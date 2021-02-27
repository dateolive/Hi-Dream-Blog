package com.datealive.controller.admin;

import com.datealive.annotation.LogAnno;
import com.datealive.common.PageResult;
import com.datealive.common.Result;
import com.datealive.service.TagService;
import com.datealive.service.vo.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: TagAdminController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/3  18:58
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class TagAdminController {

    @Autowired
    TagService tagService;

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="POST",description = "添加标签",classification = 1)
    @RequiresAuthentication
    @PostMapping("/saveTag")
    public Result saveTag(@Validated @RequestBody Tag tag){
        boolean saveTag = tagService.saveTag(tag);
        if(saveTag){
            return Result.success("添加标签成功",tag.getTag_id());
        }else{
            return Result.error("添加标签失败");
        }
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="PUT",description = "修改标签",classification = 1)
    @RequiresAuthentication
    @PutMapping("/updateTag")
    public Result updateTag(@Validated @RequestBody Tag tag){
        boolean updateTag = tagService.updateTag(tag);
        if(updateTag){
            return Result.success("修改标签成功");
        }else{
            return Result.error("修改标签失败");
        }
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="DELETE",description = "删除标签",classification = 1)
    @RequiresAuthentication
    @DeleteMapping("/deleteTag")
    public Result deleteTag(@RequestParam Integer tag_id){
        boolean deleteTag = tagService.deleteTag(tag_id);
        if(deleteTag){
            log.info("删除标签成功");
            return Result.success("删除标签成功");
        }else{
            log.error("删除标签失败");
            return Result.error("删除标签失败");
        }
    }

    @RequiresAuthentication
    @GetMapping("/tagList")
    public PageResult<Tag> getTagByPage(@RequestParam(defaultValue = "1") Integer currentPage){
        PageResult<Tag> tagByPage = tagService.getTagByPage(currentPage);
        return tagByPage;
    }
}
