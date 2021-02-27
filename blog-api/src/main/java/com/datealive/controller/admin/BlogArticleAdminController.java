package com.datealive.controller.admin;

import com.datealive.annotation.LogAnno;
import com.datealive.common.PageResult;
import com.datealive.common.Result;
import com.datealive.common.ResultCode;
import com.datealive.pojo.BlogArticle;
import com.datealive.service.BlogArticleService;
import com.datealive.service.dto.BlogUpdStatus;
import com.datealive.service.vo.BlogArticleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: BlogArticleAdminController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/1  16:12
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class BlogArticleAdminController {

    @Autowired
    BlogArticleService blogArticleService;

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="DELETE",description = "删除文章",classification = 1)
    @RequiresAuthentication
    @DeleteMapping("/deleteBlog")
    public Result deleteBlogById(@RequestParam Integer blog_id){
        boolean isDelete = blogArticleService.deleteBlogById(blog_id);
        if(isDelete){
            log.info("删除了id为"+blog_id+"的文章");
            return Result.success("删除成功");
        }else{
            log.error("对id为"+blog_id+"的文章删除失败");
            return Result.error("删除失败");
        }
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="POST",description = "添加文章",classification = 1)
    @RequiresAuthentication
    @PostMapping("/saveBlog")
    public Result saveBlogArticle(@Validated @RequestBody BlogArticle blogArticle){
        boolean saveBlog = blogArticleService.saveBlogArticle(blogArticle);
        if(saveBlog){
            log.info("添加了标题为"+blogArticle.getBlog_title()+"的文章");
            return Result.success("添加成功");
        }else{
            log.error("对标题为"+blogArticle.getBlog_title()+"的文章添加失败");
            return Result.error("添加失败");
        }
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="PUT",description = "修改文章",classification = 1)
    @RequiresAuthentication
    @PutMapping("/updateBlog")
    public Result updateBlogArticle(@Validated @RequestBody BlogArticle blogArticle){
        boolean saveBlog = blogArticleService.saveBlogArticle(blogArticle);
        if(saveBlog){
            log.info("修改了标题为"+blogArticle.getBlog_title()+"的文章");
            return Result.success("修改成功");
        }else{
            log.error("对标题为"+blogArticle.getBlog_title()+"的文章修改失败");
            return Result.error("修改失败");
        }
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="PUT",description = "修改文章状态",classification = 1)
    @RequiresAuthentication
    @PutMapping("/blog/isTop")
    public Result publishMoment(@RequestBody BlogUpdStatus blogUpdStatus){

        Integer blog_id = blogUpdStatus.getBlog_id();
        Integer is_top = blogUpdStatus.getIs_top();
        boolean updateBlogTopById = blogArticleService.updateBlogTopById(blog_id, is_top);
        if(updateBlogTopById){
            return Result.success("修改文章状态成功");
        }else{
            return Result.error("修改文章状态失败");
        }
    }

    @RequiresAuthentication
    @GetMapping("/blogList")
    public PageResult<BlogArticleInfo> getAllBlogArticle(@RequestParam(defaultValue = "1") Integer currentPage){
        PageResult<BlogArticleInfo> blogArticle = blogArticleService.getAllBlogArticleWithStatus(currentPage);
        return blogArticle;
    }
    @RequiresAuthentication
    @GetMapping("/blog/{id}")
    public Result getBlogArticleById(@PathVariable("id") Integer blog_id){
        BlogArticle article = blogArticleService.getBlogArticleById(blog_id);
        if(article!=null){
            blogArticleService.increaseBlogView(article);
            return Result.success("请求成功",article);
        }else{
            return Result.error(ResultCode.Not_Found,"没有数据");
        }

    }


}
