package com.hhk.yebserver.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_admin_role")
@ApiModel(value="AdminRole对象", description="")
public class AdminRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer adminId;

    @ApiModelProperty(value = "角色权限id")
    private Integer rid;


}
