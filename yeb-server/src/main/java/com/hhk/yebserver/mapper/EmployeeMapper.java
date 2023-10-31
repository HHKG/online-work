package com.hhk.yebserver.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hhk.yebserver.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
public interface EmployeeMapper extends BaseMapper<Employee> {
    /**
     * 获取所有员工（分页）
     * @param page
     * @param employee
     * @param beginDateScope
     * @return
     */
    Page<Employee> getEmployeeByPage(Page<Employee> page, @Param("employee") Employee employee, @Param("beginDateScope")LocalDate[] beginDateScope);

    /**
     * 查询员工
     * @param id
     * @return
     */
    List<Employee> getEmployee(Integer id);

    /**
     * 获取工号
     * @return
     */
    List<String> getMaxWorkID();

}
