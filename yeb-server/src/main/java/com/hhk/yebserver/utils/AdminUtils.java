package com.hhk.yebserver.utils;

import com.hhk.yebserver.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

public class AdminUtils {
    /**
     * 获取当前登录用户
     * @return
     */
    public static Admin getCurrentAdmin(){
       return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
