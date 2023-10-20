package com.hhk.yebserver.service.impl;

import com.hhk.yebserver.pojo.Employee;
import com.hhk.yebserver.mapper.EmployeeMapper;
import com.hhk.yebserver.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
