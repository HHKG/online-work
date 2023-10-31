package com.hhk.yebserver.mapper;

import com.hhk.yebserver.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getMenusByAdminId(Integer id);
    List<Menu> getMenusWithRole();
    List<Menu> getAllMenus();

}
