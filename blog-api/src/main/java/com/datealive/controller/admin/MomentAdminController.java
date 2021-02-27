package com.datealive.controller.admin;

import com.datealive.annotation.LogAnno;
import com.datealive.common.PageResult;
import com.datealive.common.Result;
import com.datealive.pojo.Moment;
import com.datealive.service.MomentService;
import com.datealive.service.dto.MomentUpdStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @ClassName: MomentAdminController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/17  19:18
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class MomentAdminController {

    @Autowired
    MomentService momentService;

    @RequiresAuthentication
    @GetMapping("/AllmomentList")
    public PageResult<Moment> getAllMomentList(@RequestParam(defaultValue = "1") Integer currentPage){
        PageResult<Moment> momentList = momentService.getMomentList(currentPage);
        return momentList;
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="POST",description = "添加动态",classification = 1)
    @RequiresAuthentication
    @PostMapping("/saveMoment")
    public Result saveMoment(@Validated @RequestBody Moment moment){
        boolean saveMoment = momentService.saveMoment(moment);
        if(saveMoment){
            return Result.success("添加动态成功",moment.getId());
        }else{
            return Result.error("添加动态失败");
        }
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="PUT",description = "修改动态",classification = 1)
    @RequiresAuthentication
    @PutMapping("/updateMoment")
    public Result updateMoment(@Validated @RequestBody Moment moment){
        boolean updateMoment = momentService.updateMoment(moment);
        if(updateMoment){
            return Result.success("修改动态成功",moment.getId());
        }else{
            return Result.error("修改动态失败");
        }
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="DELETE",description = "删除动态",classification = 1)
    @RequiresAuthentication
    @DeleteMapping("/moment/delete")
    public Result deleteMomentById(@RequestParam Integer id){
        boolean deleteMomentById = momentService.deleteMomentById(id);
        if(deleteMomentById){
            log.info("删除了动态");
            return Result.success("删除成功");
        }else {
            log.info("动态删除失败");
            return Result.error("删除失败");
        }
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="PUT",description = "修改动态状态",classification = 1)
    @RequiresAuthentication
    @PutMapping("/moment/isPublish")
    public Result publishMoment(@RequestBody MomentUpdStatus momentUpdStatus){
        Integer id = momentUpdStatus.getId();
        Integer isPublish = momentUpdStatus.getIsPublish();
        boolean updateMomentPublishedById = momentService.updateMomentPublishedById(id, isPublish);
        if(updateMomentPublishedById){
            return Result.success("修改动态状态成功");
        }else{
            return Result.error("修改动态状态失败");
        }
    }

    @RequiresAuthentication
    @GetMapping("/queryMomentById/{id}")
    public Result getMomentById(@PathVariable("id") Integer id){
        Moment momentById = momentService.getMomentById(id);
        return Result.success("请求成功",momentById);
    }


}
