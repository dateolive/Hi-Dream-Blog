package com.datealive.mapper;

import com.datealive.service.vo.CateGory;
import com.datealive.service.vo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: TagMapper
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/3  10:30
 */
@Mapper
@Repository
public interface TagMapper {

    /**
     * 查询所有标签的id和名字 注意这里的Tag是在vo包下的
     * @return
     */
    public List<Tag> getTagIdAndName();

    /**
     * 根据标签id查询该分类下所有博客文章
     * @param tag_id
     * @return
     */
    public List<com.datealive.pojo.Tag> getBlogInfoByTag(@Param("tag_id")Integer tag_id);

    /**
     * 获取某个标签的文章数量
     * @param tag_id
     * @return
     */
    public int getTagCntBlog(@Param("tag_id")Integer tag_id);

    /**
     * 根据标签名称获取标签
     * @param tag_name
     * @return
     */
    public Tag getTagByName(@Param("tag_name") String tag_name);

    /**
     * 获取标签的数量
     * @return
     */
    public int getTagCnt();

    /**
     * 添加标签
     * @param tag
     * @return
     */
    public int saveTag(Tag tag);

    /**
     * 修改标签
     * @param tag
     * @return
     */
    public int updateTag(Tag tag);

    /**
     * 根据标签id删除标签表
     * @param tag_id
     * @return
     */
    public int deleteTag(@Param("tag_id")Integer tag_id);

    /**
     * 根据标签id和分类id添加标签联系表
     * @param blog_id
     * @param tag_id
     * @return
     */
    public int saveTagRelation(@Param("blog_id")Integer blog_id,@Param("tag_id")Integer tag_id);

    /**
     * 根据文章id和标签id查找联系id
     * @param blog_id
     * @param tag_id
     * @return
     */
    public int getRelationId(@Param("blog_id")Integer blog_id,@Param("tag_id")Integer tag_id);

    /**
     * 根据联系id，标签id和文章id修改tag联系表
     * @param blog_id
     * @param tag_id
     * @param relation_id
     * @return
     */
    public int updateTagRelation(@Param("blog_id")Integer blog_id,@Param("tag_id")Integer tag_id,@Param("relation_id")Integer relation_id);

    /**
     * 根据标签id删除标签联系表
     * @param tag_id
     * @return
     */
    public int deleteTagRelationBytagId(@Param("tag_id")Integer tag_id);

    /**
     * 根据文章id删除标签联系表
     * @param blog_id
     * @return
     */
    public int deleteTagRelationByblogId(@Param("blog_id")Integer blog_id);

}
