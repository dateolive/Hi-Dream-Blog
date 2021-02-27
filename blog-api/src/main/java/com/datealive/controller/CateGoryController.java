package com.datealive.controller;

import com.datealive.common.PageResult;
import com.datealive.common.Result;
import com.datealive.common.ResultCode;
import com.datealive.pojo.CateGory;
import com.datealive.service.CateGoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: CateGoryController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/1  22:08
 */
@RestController
public class CateGoryController {

    @Autowired
    CateGoryService cateGoryService;

    @GetMapping("/getAllCategory")
    public Result getAllCateGory(){
        List<com.datealive.service.vo.CateGory> gateGoryIdAndName = cateGoryService.getGateGoryIdAndName();
        if(gateGoryIdAndName.isEmpty()){
            return Result.error(ResultCode.Not_Found,"无数据");
        }else{
            return Result.success("请求成功",gateGoryIdAndName);
        }
    }

    @GetMapping("/getCateGoryByName/{name}")
    public Result getCateGoryByName(@PathVariable("name") String category_name){
        com.datealive.service.vo.CateGory cateGoryByName = cateGoryService.getCateGoryByName(category_name);
        if(cateGoryByName==null){
            return Result.error(ResultCode.Not_Found,"无数据");
        }else{
            return Result.success("请求成功",cateGoryByName);
        }
    }

    @GetMapping("/cateGoryBlogs/{id}")
    public PageResult<CateGory> getCateGoryBlogs(@PathVariable("id") Integer category_id,@RequestParam(defaultValue = "1") Integer currentPage){
        PageResult<CateGory> blogInfoByCategory = cateGoryService.getBlogInfoByCategory(category_id, currentPage);
        return blogInfoByCategory;
    }





}
