package com.datealive.service;

import com.datealive.common.PageResult;
import com.datealive.service.vo.CateGory;

import java.util.List;

/**
 * @ClassName: CateGoryService
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/1  21:02
 */
public interface CateGoryService {
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
    public PageResult<com.datealive.pojo.CateGory> getBlogInfoByCategory(Integer category_id,Integer pageNum);

    /**
     * 分页获取分类
     * @param pageNum
     * @return
     */
    public PageResult<CateGory> getCateGoryByPage(Integer pageNum);

    /**
     * 根据分类名称获取分类
     * @param category_name
     * @return
     */
    public CateGory getCateGoryByName(String category_name);
    /**
     * 添加分类
     * @param cateGory
     * @return
     */
    public boolean saveCategory(CateGory cateGory);

    /**
     * 修改分类
     * @param cateGory
     * @return
     */
    public boolean updateCategory(CateGory cateGory);

    /**
     * 根据文章id和分类id查找联系id
     * @param blog_id
     * @param category_id
     * @return
     */
    public int getRelationId(Integer blog_id,Integer category_id);

    /**
     * 根据文章id和分类id添加分类联系表
     * @param blog_id
     * @param category_id
     * @return
     */
    public boolean saveCategoryRelation(Integer blog_id,Integer category_id);

    /**
     * 根据文章id和分类id修改分类联系表
     * @param blog_id
     * @param category_id
     * @return
     */
    public boolean updateCategoryRelation(Integer blog_id,Integer category_id,Integer relation_id);

    /**
     * 根据分类id删除分类表,和分类联系表
     * @param category_id
     * @return
     */
    public boolean deleteCategory(Integer category_id);


    /**
     * 根据文章id删除分类与文章联系表
     * @param blog_id
     * @return
     */
    public boolean deleteCateGoryRelationByblogId(Integer blog_id);
}
