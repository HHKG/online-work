package com.hhk.yebserver.mapper;

import com.hhk.yebserver.pojo.MenuRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {
    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    Integer insertRecord(@Param("rid") Integer rid,@Param("mids") Integer[] mids);

}
