package com.hhk.yebserver.service;

import com.hhk.yebserver.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hhk.yebserver.pojo.RespBean;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 登录之后返回token
     *
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);

    /**
     * 获取所有操作员
     * @param keywords
     * @return
     */
    List<Admin> getAllAdmins(String keywords);

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    RespBean addAdminRole(Integer adminId,Integer[] rids);

    /**
     * 更新用户密码
     * @param oldPass
     * @param pass
     * @param adminId
     * @return
     */
    RespBean updateAdminPassword(String oldPass,String pass,Integer adminId);
}
