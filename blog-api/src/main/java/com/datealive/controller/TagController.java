package com.datealive.controller;

import com.datealive.common.PageResult;
import com.datealive.common.Result;
import com.datealive.common.ResultCode;
import com.datealive.pojo.Tag;
import com.datealive.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: TagController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/3  12:22
 */
@RestController
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/getAllTag")
    public Result getAllTag(){
        List<com.datealive.service.vo.Tag> tagIdAndName = tagService.getTagIdAndName();
        if(tagIdAndName.isEmpty()){
            return Result.error(ResultCode.Not_Found,"无数据");
        }else{
            return Result.success("请求成功",tagIdAndName);
        }
    }

    @GetMapping("/getTagByName/{name}")
    public Result getTagByName(@PathVariable("name") String tag_name){
        com.datealive.service.vo.Tag tagByName = tagService.getTagByName(tag_name);
        if(tagByName==null){
            return Result.error(ResultCode.Not_Found,"无数据");
        }else{
            return Result.success("请求成功",tagByName);
        }
    }

    @GetMapping("/tagBlogs/{id}")
    public PageResult<Tag> getTagBlogs(@PathVariable("id") Integer tag_id, @RequestParam(defaultValue = "1") Integer currentPage){
        PageResult<com.datealive.pojo.Tag> blogInfoByTag = tagService.getBlogInfoByTag(tag_id, currentPage);
        return blogInfoByTag;
    }
}
