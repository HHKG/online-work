package com.hhk.yebserver.service;

import com.hhk.yebserver.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hhk.yebserver.pojo.RespBean;
import com.hhk.yebserver.utils.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
public interface IEmployeeService extends IService<Employee> {
    /**
     * 获取所有员工（分页）
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    /**
     * 获取工号
     * @return
     */
    RespBean maxWorkID();

    /**
     * 添加员工
     * @param employee
     * @return
     */
    RespBean addEmployee(Employee employee);

    /**
     * 查询员工
     * @param id
     * @return
     */
    List<Employee> getEmployee(Integer id);
 }
