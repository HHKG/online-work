package com.hhk.yebserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhk.yebserver.config.security.JwtTokenUtil;
import com.hhk.yebserver.mapper.AdminRoleMapper;
import com.hhk.yebserver.pojo.Admin;
import com.hhk.yebserver.mapper.AdminMapper;
import com.hhk.yebserver.pojo.AdminRole;
import com.hhk.yebserver.pojo.RespBean;
import com.hhk.yebserver.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hhk.yebserver.utils.AdminUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    private UserDetailsService userDetailsService;
    // PasswordEncoder是spring security框架中专门用来加密密码的插件
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    /**
     * 登录之后返回token
     *
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        // 从session中获取验证码
        String captcha = (String) request.getSession().getAttribute("captcha");
        System.out.println(request.getSession().getAttribute("captcha") + ")))))))))))))))");
        System.out.println(captcha + "===============");
        if(org.apache.commons.lang3.StringUtils.isBlank(captcha)){
            throw new RuntimeException("captcha cannot be null");
        }
         if(StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)){
             return RespBean.error("验证码输入错误，请重新输入！");
         }
        // 获取userDetail
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails==null && !passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("用户名或密码错误");
        }
        if(!userDetails.isEnabled()){
            return RespBean.error("账号被禁用，请联系管理员");
        }
        // 登录成功后，把用户信息存储在spring security 中的userDetail，以便其他地方使用
        // 更新security登录对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // 把对象放在security的全局里面
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return RespBean.success("登录成功！",tokenMap);
    }

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("enabled",true));
    }

    /**
     * 获取所有操作员
     * @param keywords
     * @return
     */
    @Override
    public List<Admin> getAllAdmins(String keywords) {
       return baseMapper.getAllAdmins(AdminUtils.getCurrentAdmin().getId(),keywords);
    }

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    @Override
    @Transactional //开启事务
    public RespBean addAdminRole(Integer adminId, Integer[] rids) {
        // 先删除全部，后调用方法重新全部添加
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId",adminId));
        Integer result= adminRoleMapper.addAdminRole(adminId,rids);
        if(rids.length == result){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @Override
    public RespBean updateAdminPassword(String oldPass, String pass, Integer adminId) {
        Admin admin = baseMapper.selectById(adminId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 对比密码，判断旧密码是否正确
        if(encoder.matches(oldPass,admin.getPassword())){
            // 对比成功，设置密码，并加密
            admin.setPassword(encoder.encode(pass));
            int result = baseMapper.updateById(admin);
            if(result == 1){
                return RespBean.success("更新成功！");
            }
        }
        return RespBean.error("更新失败！");
    }

}
