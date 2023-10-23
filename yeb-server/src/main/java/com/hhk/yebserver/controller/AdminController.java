package com.hhk.yebserver.controller;


import com.hhk.yebserver.pojo.Admin;
import com.hhk.yebserver.pojo.RespBean;
import com.hhk.yebserver.pojo.Role;
import com.hhk.yebserver.service.IAdminService;
import com.hhk.yebserver.service.IRoleService;
import io.swagger.annotations.Api;
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
@Api(tags = "adminController")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private IRoleService roleService;

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/getAllAdmins")
    public List<Admin> getAllAdmins(String keywords){
        return adminService.getAllAdmins(keywords);
    }

    @ApiOperation(value = "更新操作员")
    @PutMapping("/updateAdmin")
    public RespBean updateAdmin(@RequestBody Admin admin){
        if(adminService.updateById(admin)){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
    @ApiOperation(value = "删除操作员")
    @DeleteMapping("/deleteAdmin/{id}")
    public RespBean deleteAdmin(@PathVariable Integer id){
        if (adminService.removeById(id)){
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
    @ApiOperation(value = "获取操作员所有角色")
    @GetMapping("/getAllRoles")
    public List<Role> getAllRoles(){
        return roleService.list();
    }
    @ApiOperation(value = "更新操作员角色")
    @PutMapping("/updateAdminRole")
    public RespBean updateAdminRole(Integer adminId,Integer[] rids){
        return adminService.addAdminRole(adminId,rids);
    }

}
