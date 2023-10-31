package com.hhk.yebserver.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhk.yebserver.pojo.Menu;
import com.hhk.yebserver.pojo.MenuRole;
import com.hhk.yebserver.pojo.RespBean;
import com.hhk.yebserver.pojo.Role;
import com.hhk.yebserver.service.IMenuRoleService;
import com.hhk.yebserver.service.IMenuService;
import com.hhk.yebserver.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/basic/permission")
public class PermissionController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IMenuRoleService menuRoleService;
    @ApiOperation(value = "获取所有角色")
    @GetMapping("/getAllRoles")
    public List<Role> getAllRoles(){
        return roleService.list();
    }
    @ApiOperation(value = "添加角色")
    @PostMapping("/addRole")
    public RespBean addRole(@RequestBody Role role){
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        if(roleService.save(role)){
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/deleteRole/{id}")
    public RespBean deleteRole(@PathVariable Integer id){
        if(roleService.removeById(id)){
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/getAllMenu")
    public List<Menu> getAllMenu(){
        return menuService.getAllMenus();
    }
    @ApiOperation(value = "根据角色id，查询菜单 id")
    @GetMapping("/getMid/{id}")
    public List<Integer> getMidById(@PathVariable Integer rid){
        return menuRoleService.list(new QueryWrapper<MenuRole>()
                .eq("rid",rid))
                .stream()
                .map(MenuRole::getMid)
                .collect(Collectors.toList());
    }
    @ApiOperation(value = "更新菜单角色")
    @PutMapping("/updateMenuRole")
    public RespBean updateMenuRole(Integer rid,Integer[] mids){
        return menuRoleService.updateMenuRole(rid,mids);
    }
}
