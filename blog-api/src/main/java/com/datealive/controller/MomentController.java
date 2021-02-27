package com.datealive.controller;

import com.datealive.annotation.LogAnno;
import com.datealive.common.PageResult;
import com.datealive.common.Result;
import com.datealive.pojo.Moment;
import com.datealive.service.MomentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: MomentController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/17  19:06
 */
@RestController
@Slf4j
public class MomentController {

    @Autowired
    MomentService momentService;

    @LogAnno(operateType="GET",description = "访问动态",classification = 0)
    @GetMapping("/momentList")
    public PageResult<Moment> getMomentListWithPublish(@RequestParam(defaultValue = "1") Integer currentPage){
        PageResult<Moment> momentListWithPublish = momentService.getMomentListWithPublish(currentPage);
        return momentListWithPublish;
    }

    @PostMapping("/likeMoment/{id}")
    public Result likeMomentById(@PathVariable("id")Integer id){
        boolean likeByMomentId = momentService.addLikeByMomentId(id);
        if(likeByMomentId){
            log.info("点赞操作成功");
            return Result.success("点赞成功");
        }else{
            log.error("点赞操作失败");
            return Result.error("点赞失败");
        }
    }

    @GetMapping("/queryMomentById/{id}")
    public Result getMomentById(@PathVariable("id") Integer id){
        Moment momentById = momentService.getMomentByIdToHtml(id);
        return Result.success("请求成功",momentById);
    }

    @GetMapping("/momentCount")
    public Result getMomentCountWithPublish(){
        return Result.success("请求成功", momentService.getMomentCountWithPublish());
    }

}
