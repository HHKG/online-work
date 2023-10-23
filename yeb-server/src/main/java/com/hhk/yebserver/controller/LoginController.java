package com.hhk.yebserver.controller;

import com.hhk.yebserver.pojo.Admin;
import com.hhk.yebserver.pojo.AdminLoginParam;
import com.hhk.yebserver.pojo.RespBean;
import com.hhk.yebserver.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Api(tags = "LoginController")
@RestController
public class LoginController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value="登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
        System.out.println(request.getSession().getAttribute("captcha"));
        System.out.println(request.getSession().toString());
        System.out.println(adminLoginParam.toString()+"---------------------------");
        return adminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCode(),request);
    }

    /**
     * principal是获取全局用户信息插件
     * @param principal
     * @return
     */
    @ApiOperation(value = "获取登录用户的信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if(principal==null){
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUserName(username);
        admin.setPassword(null);
        return admin;
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout(){

        return RespBean.success("注销成功！");
    }
}
