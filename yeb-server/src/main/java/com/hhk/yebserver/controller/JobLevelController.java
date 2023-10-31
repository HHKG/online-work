package com.hhk.yebserver.controller;


import com.hhk.yebserver.pojo.Joblevel;
import com.hhk.yebserver.pojo.RespBean;
import com.hhk.yebserver.service.IJoblevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/jobLevel")
public class JobLevelController {
    @Autowired
    private IJoblevelService jobLevelService;

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/getAllJobLevel")
    public List<Joblevel> getAllJobLevel(){
        return jobLevelService.list();
    }
    @ApiOperation(value = "添加职称")
    @PostMapping("/addJobLevel")
    public RespBean addJobLevel(@RequestBody Joblevel joblevel){
        joblevel.setCreateDate(LocalDateTime.now());
        if(jobLevelService.save(joblevel)){
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }
    @ApiOperation(value = "更新职称")
    @PostMapping("/updateJobLevel")
    public RespBean updateJobLevel(@RequestBody Joblevel joblevel){
        joblevel.setCreateDate(LocalDateTime.now());
        if(jobLevelService.updateById(joblevel)){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
    @ApiOperation(value = "删除职称")
    @DeleteMapping("/deleteJobLevel/{id}")
    public RespBean deleteJobLevel(@PathVariable Integer id){
        if(jobLevelService.removeById(id)){
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
    @ApiOperation(value = "批量删除职称")
    @DeleteMapping("/deleteJobLevelByIds")
    public RespBean deleteJobLevelByIds(Integer[] ids){
        if(jobLevelService.removeByIds(Arrays.asList(ids))){
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败");
    }
}
