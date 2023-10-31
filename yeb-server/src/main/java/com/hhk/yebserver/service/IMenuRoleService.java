package com.hhk.yebserver.service;

import com.hhk.yebserver.pojo.MenuRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hhk.yebserver.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
public interface IMenuRoleService extends IService<MenuRole> {
    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    RespBean updateMenuRole(Integer rid,Integer[] mids);

}
