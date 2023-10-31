package com.hhk.yebserver.controller;


import com.hhk.yebserver.pojo.Department;
import com.hhk.yebserver.pojo.RespBean;
import com.hhk.yebserver.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/getAllDepartments")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartment();
    }

    @ApiOperation(value = "添加部门")
    @PostMapping("/addDepartment")
    public RespBean addDepartment(@RequestBody Department department){
        return departmentService.addDepartment(department);
    }
    @ApiOperation(value = "删除部门")
    @DeleteMapping("/deleteDep/{id}")
    public RespBean deleteDep(@PathVariable Integer id){
        return departmentService.deleteDep(id);
    }
}
