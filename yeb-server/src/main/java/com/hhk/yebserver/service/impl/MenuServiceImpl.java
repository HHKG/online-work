package com.hhk.yebserver.service.impl;

import com.hhk.yebserver.pojo.Menu;
import com.hhk.yebserver.mapper.MenuMapper;
import com.hhk.yebserver.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> getMenuByAdminId(Integer id) {
        return null;
    }

    @Override
    public List<Menu> getMenusWithRole() {
        return null;
    }

    @Override
    public List<Menu> getAllMenus() {
        return null;
    }
}
