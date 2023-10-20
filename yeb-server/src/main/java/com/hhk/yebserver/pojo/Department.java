package com.hhk.yebserver.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_department")
@ApiModel(value="Department对象", description="")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "部门名称")
    private String name;

    @ApiModelProperty(value = "父id")
    private Integer parentId;

    @ApiModelProperty(value = "路径")
    private String dePath;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @ApiModelProperty(value = "是否上级")
    private Boolean isParent;


}
