package com.hhk.yebserver.controller;


import com.hhk.yebserver.pojo.RespBean;
import com.hhk.yebserver.pojo.Salary;
import com.hhk.yebserver.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
@RequestMapping("/salary")
public class SalaryController {
    @Autowired
    private ISalaryService salaryService;

    @ApiOperation(value = "获取员工工资账套")
    @GetMapping("/getAllSalary")
    public List<Salary> getAllSalary(){
        return salaryService.list();
    }
    @ApiOperation(value = "添加工资账套")
    @PostMapping("/addSalary")
    public RespBean addSalary(@RequestBody Salary salary){
        salary.setCreateDate(LocalDateTime.now());
        if(salaryService.save(salary)){
           return RespBean.success("添加工资账套成功！");
        }
        return RespBean.error("添加工资账套失败！");
    }
    @ApiOperation(value = "删除工资账套")
    @DeleteMapping("/deleteSalary/{id}")
    public RespBean deleteSalary(@PathVariable Integer id){
        if(salaryService.removeById(id)){
            return RespBean.success("删除工资账套成功！");
        }
        return RespBean.error("删除工资账套失败！");
    }
    @ApiOperation(value = "更新工资账套")
    @PutMapping("/updateSalary")
    public RespBean updateSalary(@RequestBody Salary salary){
        salary.setCreateDate(LocalDateTime.now());
        if(salaryService.updateById(salary)){
            return RespBean.success("更新工资账套成功！");
        }
        return RespBean.error("更新工资账套失败！");
    }
}
