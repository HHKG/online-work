package com.hhk.yebserver.service.impl;

import com.hhk.yebserver.pojo.Department;
import com.hhk.yebserver.mapper.DepartmentMapper;
import com.hhk.yebserver.pojo.RespBean;
import com.hhk.yebserver.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    /**
     * 获取所有部门
     * @return
     */
    @Override
    public List<Department> getAllDepartment() {
        return departmentMapper.getAllDepartment();
    }

    @Override
    public RespBean addDepartment(Department department) {
        department.setEnabled(true);
       int result = departmentMapper.addDepartment(department);
       if(result == 1){
           return RespBean.success("添加成功！");
       }
       return RespBean.success("添加失败！");

    }

    @Override
    public RespBean deleteDep(Integer id) {
        Department department = new Department();
        department.setId(id);
       int result = baseMapper.deleteById(department);
        if (result == -2){
            return RespBean.error("删除失败，该部门下还有子部门");
        }
        if (result == -1){
            return RespBean.error("删除失败，该部门下还有员工");
        }
        if(result == 1){
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
