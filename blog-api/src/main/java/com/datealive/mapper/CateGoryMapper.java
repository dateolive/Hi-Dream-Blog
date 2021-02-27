package com.datealive.mapper;

import com.datealive.common.PageResult;
import com.datealive.service.vo.BlogArticleInfo;
import com.datealive.service.vo.CateGory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: CateGoryMapper
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/1  20:05
 */
@Mapper
@Repository
public interface CateGoryMapper {

    /**
     * 查询所有分类的id和名字 注意这里的Category是在vo包下的
     * @return
     */
    public List<CateGory> getGateGoryIdAndName();

    /**
     * 根据分类id查询该分类下所有博客文章
     * @param category_id
     * @return
     */
    public List<com.datealive.pojo.CateGory> getBlogInfoByCategory(@Param("category_id")Integer category_id);

    /**
     * 获取分类的数量
     * @return
     */
    public int getCateGoryCnt();

    /**
     * 获取某个分类的文章数量
     * @param category_id
     * @return
     */
    public int getCateGoryBlogCnt(@Param("category_id")Integer category_id);

    /**
     * 根据分类名称获取分类
     * @param category_name
     * @return
     */
    public CateGory getCateGoryByName(@Param("category_name") String category_name);
    /**
     * 添加分类
     * @param cateGory
     * @return
     */
    public int saveCategory(CateGory cateGory);

    /**
     * 修改分类
     * @param cateGory
     * @return
     */
    public int updateCategory(CateGory cateGory);

    /**
     * 根据分类id删除分类表
     * @param category_id
     * @return
     */
    public int deleteCategory(@Param("category_id")Integer category_id);

    /**
     * 根据文章id和分类id添加分类联系表
     * @param blog_id
     * @param category_id
     * @return
     */
    public int saveCategoryRelation(@Param("blog_id")Integer blog_id,@Param("category_id")Integer category_id);

    /**
     * 根据文章id和分类id查找联系id
     * @param blog_id
     * @param category_id
     * @return
     */
    public int getRelationId(@Param("blog_id")Integer blog_id,@Param("category_id")Integer category_id);

    /**
     * 根据文章id和分类id修改分类联系表
     * @param blog_id
     * @param category_id
     * @return
     */
    public int updateCategoryRelation(@Param("blog_id")Integer blog_id,@Param("category_id")Integer category_id,@Param("relation_id")Integer relation_id);

    /**
     * 根据分类id删除分类联系表
     * @param category_id
     * @return
     */
    public int deleteCategoryRelationBycateId(@Param("category_id")Integer category_id);

    /**
     * 根据文章id删除分类联系表
     * @param blog_id
     * @return
     */
    public int deleteCateGoryRelationByblogId(@Param("blog_id")Integer blog_id);
}
