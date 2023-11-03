package com.hhk.yebserver.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.hhk.yebserver.pojo.*;
import com.hhk.yebserver.service.*;
import com.hhk.yebserver.utils.RespPageBean;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
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
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPoliticsStatusService politicsStatusService;
    @Autowired
    private IJoblevelService jobLevelService;
    @Autowired
    private INationService nationService;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private IDepartmentService departmentService;
    @ApiOperation(value = "获取所有员工（分页）")
    @PostMapping("/getEmployee")
    public RespPageBean getEmployee(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    Employee employee,
                                    LocalDate[] beginDateScope){
        return employeeService.getEmployeeByPage(currentPage,size,employee,beginDateScope);
    }
    @ApiModelProperty(value = "获取所有政治面貌")
    @GetMapping("/getAllPoliticsStatus")
    public List<PoliticsStatus> getAllPoliticsStatus(){
        return politicsStatusService.list();
    }
    @ApiOperation(value = "获取所有的职称")
    @GetMapping("/getAllJobLevel")
    public List<Joblevel> getAllJobLevel(){
        return jobLevelService.list();
    }
    @ApiOperation(value = "获取所有民族")
    @GetMapping("/getAllNation")
    public List<Nation> getAllNation(){
        return nationService.list();
    }
    @ApiOperation(value = "获取所有职位")
    @GetMapping("getAllPosition")
    public List<Position> getAllPosition(){
        return positionService.list();
    }
    @ApiOperation(value = "获取所有部门")
    @GetMapping("/getAllDepartment")
    public List<Department> getAllDepartment(){
        return departmentService.list();
    }
    @ApiOperation(value = "获取工号")
    @GetMapping("/maxWorkID")
    public RespBean maxWorkID(){
      return employeeService.maxWorkID();
    }
    @ApiOperation(value = "添加员工")
    @PostMapping("/addEmployee")
    public RespBean addEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }

    @ApiOperation(value = "更新员工")
    @PutMapping("/updateEmployee")
    public RespBean updateEmployee(@RequestBody Employee employee){
        if(employeeService.updateById(employee)){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
    @ApiOperation(value = "删除员工")
    @DeleteMapping("/deleteEmployee/{id}")
    public RespBean deleteEmployee(@PathVariable Integer id){
        if(employeeService.removeById(id)){
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
    @ApiOperation(value = "导出数据")
    @GetMapping(value = "export",produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response){
        List<Employee> list = employeeService.getEmployee(null); // 导出所有数据
        // HSSF 03版，兼容性好一点；还有一个07版的
        ExportParams params = new ExportParams("员工表","员工表", ExcelType.HSSF);
        // 导出工具类
        Workbook workbook = ExcelExportUtil.exportExcel(params,Employee.class,list);
        ServletOutputStream out = null;
       try {
           // 流形式导出
           response.setHeader("content-type","application/octet-stream");
           // 防止中文乱码
           response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("员工表.xls","UTF-8"));
           out = response.getOutputStream();
           workbook.write(out);
       }catch (IOException e){
           throw new RuntimeException(e);
       }finally {
           if(out != null){
               try {
                   out.close();
               }catch (IOException e){
                   throw new RuntimeException(e);
               }
           }
       }
    }

//    @ApiOperation(value = "导入员工数据")
//    @PostMapping("importEmployee")
//    public RespBean importEmployee(MultipartFile file) {
//        ImportParams params = new ImportParams();
//        // 去掉标题行
//        params.setTitleRows(1);
//        List<Nation> nationList = nationService.list();
//        List<PoliticsStatus> politicsStatusesList = politicsStatusService.list();
//        List<Department> departmentsList = departmentService.list();
//        List<Joblevel> joblevelsList = jobLevelService.list();
//        List<Position> positionsList = positionService.list();
//        try {
//            List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, params);
//            list.forEach(employee -> {
//                // indexOf 在字符串中寻找参数字符串第一次出现的位置并返回该位置。
//                // 民族 id
//                // 获取 Nation 的 name, 通过 name 获取对应的下标，通过下标获取整完的对象，通过对象获取 id,
//                employee.setNationId(nationList.get(nationList.indexOf(new Nation(employee.getNation().getName()))).getId());
//                // 政治 id
//                employee.setPoliticId(politicsStatusesList.get(politicsStatusesList.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
//                // 部门 id
//                employee.setDepartmentId(departmentsList.get(departmentsList.indexOf(new Department(employee.getDepartment().getName()))).getId());
//                // 职称 id
//                employee.setJobLevelId(joblevelsList.get(joblevelsList.indexOf(new Joblevel(employee.getJobLevel().getName()))).getId());
//                // 职位 id
//                employee.setPosId(positionsList.get(positionsList.indexOf(new Position(employee.getPosition().getName()))).getId());
//            });
//            if (employeeService.saveBatch(list)) {
//                return RespBean.success("导入成功！");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return RespBean.error("导入失败！");
//    }

}
