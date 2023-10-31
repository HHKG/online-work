package com.hhk.yebserver.mapper;

import com.hhk.yebserver.pojo.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hhk.yebserver.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
public interface DepartmentMapper extends BaseMapper<Department> {
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
    int addDepartment(Department department);

    /**
     * 删除部门
     * @param id
     * @return
     */
    int deleteDep(Department department);

}
