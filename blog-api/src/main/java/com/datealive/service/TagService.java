package com.datealive.service;

import com.datealive.common.PageResult;
import com.datealive.service.vo.Tag;


import java.util.List;

/**
 * @ClassName: TagService
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/3  11:57
 */
public interface TagService {

    /**
     * 查询所有标签的id和名字 注意这里的Tag是在vo包下的
     * @return
     */
    public List<Tag> getTagIdAndName();

    /**
     * 分页获取标签
     * @param pageNum
     * @return
     */
    public PageResult<Tag> getTagByPage(Integer pageNum);

    /**
     * 根据标签id查询该分类下所有博客文章
     * @param tag_id
     * @return
     */
    public PageResult<com.datealive.pojo.Tag> getBlogInfoByTag(Integer tag_id,Integer pageNum);

    /**
     * 根据标签名查询标签
     * @param tag_name
     * @return
     */
    public Tag getTagByName(String tag_name);

    /**
     * 添加标签
     * @param tag
     * @return
     */
    public boolean saveTag(Tag tag);

    /**
     * 修改标签
     * @param tag
     * @return
     */
    public boolean updateTag(Tag tag);

    /**
     * 根据标签id删除标签表
     * @param tag_id
     * @return
     */
    public boolean deleteTag(Integer tag_id);

    /**
     * 根据标签id和分类id添加标签联系表
     * @param blog_id
     * @param tag_id
     * @return
     */
    public boolean saveTagRelation(Integer blog_id,Integer tag_id);

    /**
     * 根据文章id和标签id查找联系id
     * @param blog_id
     * @param tag_id
     * @return
     */
    public int getRelationId(Integer blog_id,Integer tag_id);

    /**
     * 根据联系id，标签id和文章id修改tag联系表
     * @param blog_id
     * @param tag_id
     * @param relation_id
     * @return
     */
    public boolean updateTagRelation(Integer blog_id,Integer tag_id,Integer relation_id);


    /**
     * 根据文章id删除标签联系表
     * @param blog_id
     * @return
     */
    public boolean deleteTagRelationByblogId(Integer blog_id);
}
