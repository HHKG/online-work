package com.hhk.yebserver.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhk.yebserver.mapper.MailLogMapper;
import com.hhk.yebserver.pojo.Employee;
import com.hhk.yebserver.mapper.EmployeeMapper;
import com.hhk.yebserver.pojo.MailLog;
import com.hhk.yebserver.pojo.RespBean;
import com.hhk.yebserver.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhk.yebserver.utils.MailConstants;
import com.hhk.yebserver.utils.RespPageBean;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private MailLogMapper mailLogMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     * 获取所有员工（分页）
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    @Override
    public RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        // 开启分页
        Page<Employee> page = new Page<>(currentPage,size);
        Page<Employee> employeeByPage = employeeMapper.getEmployeeByPage(page,employee,beginDateScope);
        return new RespPageBean(employeeByPage.getSize(),employeeByPage.getRecords());
    }

    /**
     * 获取最大工号
     * @return
     */
    @Override
    public RespBean maxWorkID() {
        List<String> list = employeeMapper.getMaxWorkID();
        List<Long> longList = list.stream().map(Long::valueOf).collect(Collectors.toList());
        long max = longList.stream().mapToInt(Long::intValue).max().getAsInt();
        return RespBean.success("request success",String.format("%08d",max+1));
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @Override
    public RespBean addEmployee(Employee employee) {
        // 处理合同期限，保留两位小数
        LocalDate beginContract = employee.getBeginContract(); // 合同开始时间
        LocalDate endContract = employee.getEndContract(); // 合同结束时间
        // 计算 两个日期相差多少天
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        // 保留两位小数
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days/365)));
        if(baseMapper.insert(employee)==1){
            // 获取当前行添加员工记录
            Employee emp = baseMapper.getEmployee(employee.getId()).get(0);
            // 数据库记录发送的消息
            String msgId = UUID.randomUUID().toString();
            MailLog mailLog = new MailLog();
            mailLog.setMsgId(msgId);
            mailLog.setEid(employee.getId());
            mailLog.setStatus(0);
            mailLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailLog.setCount(0);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());
            mailLogMapper.insert(mailLog); // 把设置的数据插入数据表
            // 发送信息
            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,emp,new CorrelationData(msgId));
            return RespBean.success("添加成功！");

        }
        return RespBean.error("添加失败！");
    }

    /**
     * 查询员工
     * @param id
     * @return
     */
    @Override
    public List<Employee> getEmployee(Integer id) {
        return baseMapper.getEmployee(id);
    }

}
