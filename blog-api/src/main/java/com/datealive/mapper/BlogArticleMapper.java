package com.datealive.mapper;

import com.datealive.pojo.BlogArticle;
import com.datealive.service.vo.BlogArticleIdAndTitle;
import com.datealive.service.vo.BlogArticleInfo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: BlogArticleMapper
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  20:38
 */
@Mapper
@Repository
public interface BlogArticleMapper {

    /**
     * 根据分页获取全部文章，，不携带内容字段
     *
     * @return
     */
    public List<BlogArticleInfo> getAllBlogArticle();

    /**
     * 根据分页获取全部文章，，不携带内容字段 管理员
     * @return
     */
    public List<BlogArticleInfo> getAllBlogArticleWithStatus();

    /**
     * 获取发布文章总数量
     * @return
     */
    public int countBlogArticle();

    /**
     * 获取所有文章数量
     * @return
     */
    public int countBlogArticleWithStatus();

    /**
     * 根据title获取该title下的文章数量
     * @return
     */
    public int countBlogArticleByTitle(@Param("blog_title") String blog_title);

    /**
     * 根据文章id获取文章详情
     * @param blog_id
     * @return
     */
    public BlogArticle getBlogArticleById(@Param("blog_id") Integer blog_id);

    /**
     * 根据标题查询文章
     * @param blog_title
     * @return
     */
    public List<BlogArticleInfo> getBlogArticleByTitle(@Param("blog_title") String blog_title);

    /**
     * 获取文章id，标题和创建时间
     * @return
     */
    public List<BlogArticleIdAndTitle> getBlogArticleIdAndTitle();

    /**
     * 根据id删除文章
     * @param blog_id
     * @return
     */
    public int deleteBlogById(@Param("blog_id")Integer blog_id);

    /**
     * 维护tag与文章的关系表
     * @param blog_id
     * @return
     */
    public int deleteBlogTagById(@Param("blog_id")Integer blog_id);

    /**
     * 维护category与文章的关系表
     * @param blog_id
     * @return
     */
    public int deleteBlogCategoryById(@Param("blog_id")Integer blog_id);

    /**
     * 维护comment与文章的关系表
     * @param blog_id
     * @return
     */
    public int deleteBlogCommentById(@Param("blog_id")Integer blog_id);

    /**
     * 添加博客文章
     * @param blogArticle
     * @return
     */
    public int saveBlogArticle(BlogArticle blogArticle);

    /**
     * 更新文章
     * @param blogArticle
     * @return
     */
    public int updateBlogArticle(BlogArticle blogArticle);

    /**
     * 添加文章访问量
     * @param blog_id
     * @param ViewCnt
     * @return
     */
    public int increaseBlogViews(@Param("blog_id")Integer blog_id,@Param("ViewCnt") Integer ViewCnt);

    /**
     * 添加文章点赞量
     * @param blog_id
     * @return
     */
    public int increaseBlogLike(@Param("blog_id")Integer blog_id);

    /**
     * 更新文章置顶操作
     * @param blog_id
     * @param is_top
     * @return
     */
    public int updateBlogTopById(@Param("blog_id")Integer blog_id, @Param("is_top")Integer is_top);
}
