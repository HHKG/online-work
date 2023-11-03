package com.hhk.yebserver.controller;


import com.hhk.yebserver.pojo.Position;
import com.hhk.yebserver.pojo.RespBean;
import com.hhk.yebserver.service.IPositionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 黄华康
 * @since 2023-07-28
 */
@RestController
@RequestMapping("/position")
public class PositionController {
    @Autowired
    private IPositionService positionService;
    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/getAllPosition")
    public List<Position> getAllPosition(){
        return positionService.list();
    }
    @ApiOperation(value = "添加职位")
    @PostMapping("/addPosition")
    public RespBean addPosition(@RequestBody Position position){
        position.setCreateDate(LocalDateTime.now());
        if(positionService.save(position)){
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }
    @ApiOperation(value = "更新职位信息")
    @PutMapping("/updatePosition")
    public RespBean updatePosition(@RequestBody Position position){
        position.setCreateDate(LocalDateTime.now());
        if(positionService.updateById(position)){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
    @ApiOperation(value = "删除单条职位信息")
    @DeleteMapping("/deletePosition/{id}")
    public RespBean deletePosition(@PathVariable Integer id){
        if(positionService.removeById(id)){
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/deletePositions")
    public RespBean deletePositions(Integer[] ids){
        if(positionService.removeBatchByIds(Arrays.asList(ids))){
            return RespBean.success("批量删除成功！");
        }
        return RespBean.error("批量删除失败！");
    }

}
