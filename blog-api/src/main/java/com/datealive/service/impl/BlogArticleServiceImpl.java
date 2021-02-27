package com.datealive.service.impl;

import com.datealive.common.PageResult;
import com.datealive.common.ResultCode;
import com.datealive.common.StaticFinalCode;
import com.datealive.mapper.BlogArticleMapper;
import com.datealive.pojo.BlogArticle;
import com.datealive.pojo.CateGory;
import com.datealive.pojo.Tag;
import com.datealive.service.BlogArticleService;
import com.datealive.service.CateGoryService;
import com.datealive.service.TagService;
import com.datealive.service.vo.BlogArticleIdAndTitle;
import com.datealive.service.vo.BlogArticleInfo;
import com.datealive.utils.DateUtils;
import com.datealive.utils.RedisUtil;
import com.datealive.utils.markdowntoHtml.MarkdownUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

/**
 * @ClassName: BlogArticleServiceImpl
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  22:08
 */
@Service
public class BlogArticleServiceImpl implements BlogArticleService {

    @Autowired
    BlogArticleMapper blogArticleMapper;

    @Autowired
    CateGoryService cateGoryService;

    @Autowired
    TagService tagService;

    @Autowired
    RedisUtil redisUtil;



    @Override
    public PageResult<BlogArticleInfo> getAllBlogArticle(Integer pageNum) {
        int blog_count = blogArticleMapper.countBlogArticle();
        int totalPage = (int) Math.ceil(blog_count* 1.0 / StaticFinalCode.pageSize);
        PageHelper.startPage(pageNum,StaticFinalCode.pageSize);
        List<BlogArticleInfo> blogArticle = blogArticleMapper.getAllBlogArticle();
        //redis获取文章访问量
        hGetBlogPageRedisViewCnt(blogArticle);
        if(blogArticle.isEmpty()){
            return new PageResult<BlogArticleInfo>(ResultCode.Not_Found,totalPage,pageNum,null);
        }else{

            return new PageResult<BlogArticleInfo>(ResultCode.Success,totalPage,pageNum,blogArticle);
        }

    }

    @Override
    public PageResult<BlogArticleInfo> getAllBlogArticleWithStatus(Integer pageNum) {
        int blog_count = blogArticleMapper.countBlogArticleWithStatus();
        int totalPage = (int) Math.ceil(blog_count* 1.0 / StaticFinalCode.pageSize);
        PageHelper.startPage(pageNum,StaticFinalCode.pageSize);
        List<BlogArticleInfo> blogArticleWithStatus = blogArticleMapper.getAllBlogArticleWithStatus();
        //redis获取文章访问量
        hGetBlogPageRedisViewCnt(blogArticleWithStatus);
        if(blogArticleWithStatus.isEmpty()){
            return new PageResult<BlogArticleInfo>(ResultCode.Not_Found,totalPage,pageNum,null);
        }else{
            return new PageResult<BlogArticleInfo>(ResultCode.Success,totalPage,pageNum,blogArticleWithStatus);
        }
    }

    @Override
    public PageResult<BlogArticleInfo> getBlogArticleByTitle(String blog_title, Integer pageNum) {
        int blog_count = blogArticleMapper.countBlogArticleByTitle(blog_title);
        int totalPage = (int) Math.ceil(blog_count* 1.0 / StaticFinalCode.pageSize);
        PageHelper.startPage(pageNum,StaticFinalCode.pageSize);
        List<BlogArticleInfo> blogArticleByTitle = blogArticleMapper.getBlogArticleByTitle(blog_title);
        //redis获取文章访问量
        hGetBlogPageRedisViewCnt(blogArticleByTitle);
        if(blogArticleByTitle.isEmpty()){
            return new PageResult<BlogArticleInfo>(ResultCode.Not_Found,totalPage,pageNum,null);
        }else{
            return new PageResult<BlogArticleInfo>(ResultCode.Success,totalPage,pageNum,blogArticleByTitle);
        }
    }

    @Override
    public int countBlogArticle() {
        return blogArticleMapper.countBlogArticle();
    }

    @Override
    public int countBlogArticleWithStatus() {
        return blogArticleMapper.countBlogArticleWithStatus();
    }

    @Override
    public int countBlogArticleByTitle(String blog_title) {
        return blogArticleMapper.countBlogArticleByTitle(blog_title);
    }

    @Override
    public BlogArticle getBlogArticleById(Integer blog_id) {
        BlogArticle article = blogArticleMapper.getBlogArticleById(blog_id);
        hGetBlogDeatilRedisViewCnt(article);
        return article;
    }

    @Override
    public BlogArticle getBlogArticleByIdToHtml(Integer blog_id) {
        BlogArticle article = blogArticleMapper.getBlogArticleById(blog_id);
        String blog_content = article.getBlog_content();
        String markdownToHtml = MarkdownUtils.markdownToHtmlExtensions(blog_content);
        article.setBlog_content(markdownToHtml);
        hGetBlogDeatilRedisViewCnt(article);
        return article;
    }

    @Override
    public PageResult<BlogArticleIdAndTitle> getBlogArticleIdAndTitle(Integer pageNum) {
        int blog_count = blogArticleMapper.countBlogArticle();
        int totalPage = (int) Math.ceil(blog_count* 1.0 / StaticFinalCode.pageSize);
        PageHelper.startPage(pageNum,StaticFinalCode.pageSize);
        List<BlogArticleIdAndTitle> articleIdAndTitle = blogArticleMapper.getBlogArticleIdAndTitle();
        if(articleIdAndTitle.isEmpty()){
            return new PageResult<BlogArticleIdAndTitle>(ResultCode.Not_Found,totalPage,pageNum,null);
        }else{
            return new PageResult<BlogArticleIdAndTitle>(ResultCode.Success,totalPage,pageNum,articleIdAndTitle);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean deleteBlogById(Integer blog_id) {
        int isDelete = blogArticleMapper.deleteBlogById(blog_id);
        if(isDelete>0){
            blogArticleMapper.deleteBlogTagById(blog_id);
            blogArticleMapper.deleteBlogCategoryById(blog_id);
            blogArticleMapper.deleteBlogCommentById(blog_id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean saveBlogArticle(BlogArticle blogArticle) {
        //更新博客文章
        if(blogArticle.getBlog_id()!=null){
            BlogArticle oldBlogArticle = blogArticleMapper.getBlogArticleById(blogArticle.getBlog_id());
            blogArticle.setUpdate_time(new Date());
            int updateBlog = blogArticleMapper.updateBlogArticle(blogArticle);
            //修改文章成功
            if(updateBlog>0){
                //这里需要进行标签和分类的维护
                //1.先进行分类的维护
                //先根据文章id删除分类联系表
                cateGoryService.deleteCateGoryRelationByblogId(oldBlogArticle.getBlog_id());
                //再把新的分类list写入数据库
                List<CateGory> newCateGory = blogArticle.getCateGory();
                for (CateGory gory : newCateGory) {
                    boolean isSaveCate = cateGoryService.saveCategoryRelation(blogArticle.getBlog_id(), gory.getCategory_id());
                    if(!isSaveCate){
                        return false;
                    }
                }
                //2.进行标签的维护
                tagService.deleteTagRelationByblogId(oldBlogArticle.getBlog_id());
                List<Tag> newTags = blogArticle.getTags();
                for (Tag newTag : newTags) {
                    boolean isSaveTag = tagService.saveTagRelation(blogArticle.getBlog_id(), newTag.getTag_id());
                    if(!isSaveTag){
                        return false;
                    }
                }
                return true;
            }
            return false;

        }else{
            blogArticle.setBlog_like(0);
            blogArticle.setBlog_visits(0);
            blogArticle.setCreate_time(new Date());
            blogArticle.setUpdate_time(new Date());
            int saveBlog= blogArticleMapper.saveBlogArticle(blogArticle);
            //添加博客成功
            if(saveBlog>0){
                Integer save_blog_id = blogArticle.getBlog_id();
                //System.out.println("=========>saveBlogId"+save_blog_id);
                //这里需要进行博客标签关系表和分类表的维护
                //1.先进行分类的维护
                List<CateGory> saveCategory = blogArticle.getCateGory();
                for (CateGory cateGory : saveCategory) {
                    boolean saveCate = cateGoryService.saveCategoryRelation(save_blog_id, cateGory.getCategory_id());
                    if(!saveCate){
                        return false;
                    }
                }
                //2.进行标签的维护
                List<Tag> tags = blogArticle.getTags();
                for (Tag tag : tags) {
                    boolean saveTagRelation = tagService.saveTagRelation(save_blog_id, tag.getTag_id());
                    if(!saveTagRelation){
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

    }

    @Override
    public boolean increaseBlogLike(Integer blog_id) {
        int blogLike = blogArticleMapper.increaseBlogLike(blog_id);
        if(blogLike>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean increaseBlogView(BlogArticle article) {
        String key=StaticFinalCode.KEY_BLOG_ARTICLE_VIEW_ProFix+article.getBlog_id();
        Integer ViewCount = (Integer) redisUtil.hget(key, "post:viewCount");
        //判断redis中是否有当前文章的浏览量，如果没有，则在实体类获取浏览量+1
        if(ViewCount!=null){
            article.setBlog_visits(ViewCount+1);
        }else{
            article.setBlog_visits(article.getBlog_visits()+1);
        }
        boolean hSetViewCount = redisUtil.hset(key, "post:viewCount", article.getBlog_visits());
        if(hSetViewCount){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void hGetBlogPageRedisViewCnt(List<BlogArticleInfo> blogLists) {
        for (BlogArticleInfo blog : blogLists) {
            String key=StaticFinalCode.KEY_BLOG_ARTICLE_VIEW_ProFix+blog.getBlog_id();
            Integer redisViewCnt = (Integer) redisUtil.hget(key, "post:viewCount");
            if(redisViewCnt!=null){
                blog.setBlog_visits(redisViewCnt);
            }
        }
    }

    @Override
    public void hGetBlogDeatilRedisViewCnt(BlogArticle blog) {
        String key=StaticFinalCode.KEY_BLOG_ARTICLE_VIEW_ProFix+blog.getBlog_id();
        Integer redisViewCnt = (Integer) redisUtil.hget(key, "post:viewCount");
        if(redisViewCnt!=null){
            blog.setBlog_visits(redisViewCnt);
        }
    }

    @Override
    public boolean updateBlogTopById(Integer blog_id, Integer is_top) {
        int updateBlogTopById = blogArticleMapper.updateBlogTopById(blog_id, is_top);
        if(updateBlogTopById>0){
            return true;
        }else{
            return false;
        }
    }
}
