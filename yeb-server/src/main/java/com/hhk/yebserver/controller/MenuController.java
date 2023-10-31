package com.hhk.yebserver.controller;


import com.hhk.yebserver.pojo.Menu;
import com.hhk.yebserver.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;
    @ApiOperation(value = "通过用户id查询菜单列表")
    @GetMapping("/getMenus")
    public List<Menu> getMenusByAdminId(Integer id){
        // 只要登录，用户信息存在security全局对象中，从全局对象中获取用户id
        return menuService.getMenuByAdminId(id);
    }

}
