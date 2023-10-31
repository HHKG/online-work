package com.hhk.yebserver.service;

import com.hhk.yebserver.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 通过用户id查询菜单列表
     * @return
     */
    List<Menu> getMenuByAdminId(Integer id);

    /**
     * 根据角色获取菜单列表
     * @return
     */
    List<Menu> getMenusWithRole();

    /**
     * 查询所有菜单
     * @return
     */
    List<Menu> getAllMenus();

}
