package com.hhk.yebserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hhk.yebserver.pojo.MenuRole;
import com.hhk.yebserver.mapper.MenuRoleMapper;
import com.hhk.yebserver.pojo.RespBean;
import com.hhk.yebserver.service.IMenuRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Override
    @Transactional // 开启事务
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        // 如果调用此接口，没传其他参数，证明是删除已有菜单
        baseMapper.delete(new QueryWrapper<MenuRole>().eq("rid",rid));
        if(mids == null|| mids.length == 0){
            return RespBean.success("更新成功！");
        }
        // 如果传参过来，新建批量更新方法，更新角色菜单
        Integer result = baseMapper.insertRecord(rid,mids);
        return null;
    }
}
