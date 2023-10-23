package com.hhk.yebserver.mapper;

import com.hhk.yebserver.pojo.AdminRole;
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
public interface AdminRoleMapper extends BaseMapper<AdminRole> {
    /**
     * 更新操作员角色
     * @param addAdminId
     * @param rids
     * @return
     */
    Integer addAdminRole(@Param("adminId") Integer addAdminId,@Param("rids") Integer[] rids);
}
