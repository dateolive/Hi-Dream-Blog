package com.datealive.service;

import com.datealive.common.PageResult;
import com.datealive.pojo.BlogArticle;
import com.datealive.service.vo.BlogArticleIdAndTitle;
import com.datealive.service.vo.BlogArticleInfo;


import java.util.List;

/**
 * @ClassName: BlogArticleService
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  22:04
 */
public interface BlogArticleService {

    /**
     * 根据分页获取全部文章，，不携带内容字段
     *
     * @return
     */
    public PageResult<BlogArticleInfo> getAllBlogArticle(Integer pageNum);

    /**
     * 根据分页获取全部文章，，不携带内容字段 管理员
     * @param pageNum
     * @return
     */
    public PageResult<BlogArticleInfo> getAllBlogArticleWithStatus(Integer pageNum);

    /**
     * 根据title搜索文章
     * @param blog_title
     * @param pageNum
     * @return
     */
    public PageResult<BlogArticleInfo> getBlogArticleByTitle(String blog_title,Integer pageNum);

    /**
     * 获取发布的文章总数量
     * @return
     */
    public int countBlogArticle();

    /**
     * 获取所有文章数量
     * @return
     */
    public int countBlogArticleWithStatus();

    /**
     * 根据title获取文章数量
     * @return
     */
    public int countBlogArticleByTitle(String blog_title);

    /**
     * 根据文章id获取文章详情 这里返回给后台编写
     * @param blog_id
     * @return
     */
    public BlogArticle getBlogArticleById(Integer blog_id);

    /**
     * 根据文章id获取文章详情  这里是转为html返回给前端前台页面
     * @param blog_id
     * @return
     */
    public BlogArticle getBlogArticleByIdToHtml(Integer blog_id);

    /**
     * 获取文章id和标题
     * @param pageNum
     * @return
     */
    public PageResult<BlogArticleIdAndTitle> getBlogArticleIdAndTitle(Integer pageNum);

    /**
     * 根据id删除文章
     * @param blog_id
     * @return
     */
    public boolean deleteBlogById(Integer blog_id);

    /**
     * 添加博客文章
     * @param blogArticle
     * @return
     */
    public boolean saveBlogArticle(BlogArticle blogArticle);

    /**
     * 添加文章点赞量
     * @param blog_id
     * @return
     */
    public boolean increaseBlogLike(Integer blog_id);

    /**
     * 添加文章访问量
     * @param article
     * @return
     */
    public boolean increaseBlogView(BlogArticle article);

    /**
     * 在页面（这里不是详情页）获取redis的文章访问量并返回
     * @param blogLists
     */
    public void hGetBlogPageRedisViewCnt(List<BlogArticleInfo> blogLists);

    /**
     * 在详情页获取redis中的文章访问量
     * @param blog
     */
    public void hGetBlogDeatilRedisViewCnt(BlogArticle blog);

    /**
     * 更新文章置顶操作
     * @param blog_id
     * @param is_top
     * @return
     */
    public boolean updateBlogTopById(Integer blog_id, Integer is_top);
}
