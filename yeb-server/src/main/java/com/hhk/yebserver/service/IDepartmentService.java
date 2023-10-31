package com.hhk.yebserver.service;

import com.hhk.yebserver.pojo.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hhk.yebserver.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
public interface IDepartmentService extends IService<Department> {
    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDepartment();

    /**
     * 添加部门
     * @param department
     * @return
     */
    RespBean addDepartment(Department department);

    RespBean deleteDep(Integer id);

}
