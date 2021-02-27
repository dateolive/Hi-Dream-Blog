package com.datealive.controller;

import com.datealive.annotation.LogAnno;
import com.datealive.common.PageResult;
import com.datealive.common.Result;
import com.datealive.common.ResultCode;
import com.datealive.pojo.BlogArticle;
import com.datealive.service.BlogArticleService;
import com.datealive.service.vo.BlogArticleIdAndTitle;
import com.datealive.service.vo.BlogArticleInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: BlogArticleController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  22:37
 */
@RestController
@Slf4j
public class BlogArticleController {

    @Autowired
    BlogArticleService blogArticleService;


    @LogAnno(operateType="GET",description = "访问文章",classification = 0)
    /**
     * 博客文章详情
     * @param blog_id
     * @return
     */
    @GetMapping("/blog/{id}")
    public Result getBlogArticleById(@PathVariable("id") Integer blog_id){
        BlogArticle article = blogArticleService.getBlogArticleByIdToHtml(blog_id);
        if(article!=null){
            blogArticleService.increaseBlogView(article);
            return Result.success("请求成功",article);
        }else{
            return Result.error(ResultCode.Not_Found,"没有数据");
        }

    }

    /**
     * 博客文章数量
     * @return
     */
    @GetMapping("/blogsCount")
    public Result getBlogArticlesCount(){
        int blogs_count = blogArticleService.countBlogArticle();
        return Result.success("请求成功",blogs_count);
    }

    @LogAnno(operateType="GET",description = "访问首页",classification = 0)
    @GetMapping("/blogList")
    public PageResult<BlogArticleInfo> getAllBlogArticle(@RequestParam(defaultValue = "1") Integer currentPage){
        PageResult<BlogArticleInfo> blogArticle = blogArticleService.getAllBlogArticle(currentPage);
        return blogArticle;
    }


    @GetMapping("/blogArchive")
    public PageResult<BlogArticleIdAndTitle> getBlogArticleIdAndTitle(@RequestParam(defaultValue = "1") Integer currentPage){
        PageResult<BlogArticleIdAndTitle> articleIdAndTitle = blogArticleService.getBlogArticleIdAndTitle(currentPage);
        return articleIdAndTitle;
    }

    @GetMapping("/searchBlog/{title}")
    public PageResult<BlogArticleInfo> getBlogArticleByTitle(@PathVariable("title") String blog_title,@RequestParam(defaultValue = "1") Integer currentPage){
        PageResult<BlogArticleInfo> blogArticleByTitle = blogArticleService.getBlogArticleByTitle(blog_title, currentPage);
        return blogArticleByTitle;
    }

    @PostMapping("/likeBlog/{id}")
    public Result increaseBlogLikeCnt(@PathVariable("id") Integer blog_id){
        boolean blogLike = blogArticleService.increaseBlogLike(blog_id);
        if(blogLike){
            log.info("点赞操作成功");
            return Result.success("点赞成功");
        }else{
            log.error("点赞操作失败");
            return Result.error("点赞失败");
        }
    }

}
