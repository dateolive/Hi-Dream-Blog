package com.datealive.service.impl;

import com.datealive.common.PageResult;
import com.datealive.common.ResultCode;
import com.datealive.common.StaticFinalCode;
import com.datealive.mapper.TagMapper;
import com.datealive.service.TagService;
import com.datealive.service.vo.Tag;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: TagServiceImpl
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/3  12:10
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Override
    public List<Tag> getTagIdAndName() {
        return tagMapper.getTagIdAndName();
    }

    @Override
    public PageResult<Tag> getTagByPage(Integer pageNum) {
        int tagCnt = tagMapper.getTagCnt();
        int totalPage = (int) Math.ceil(tagCnt* 1.0 / StaticFinalCode.pageSize);
        PageHelper.startPage(pageNum,StaticFinalCode.pageSize);
        List<Tag> tagIdAndName = tagMapper.getTagIdAndName();
        if(tagIdAndName.isEmpty()){
            return new PageResult<Tag>(ResultCode.Not_Found,totalPage,pageNum,null);
        }else{
            return new PageResult<Tag>(ResultCode.Success,totalPage,pageNum,tagIdAndName);
        }
    }

    @Override
    public PageResult<com.datealive.pojo.Tag> getBlogInfoByTag(Integer tag_id,Integer pageNum) {
        int tagCnt = tagMapper.getTagCntBlog(tag_id);
        int totalPage = (int) Math.ceil(tagCnt* 1.0 / StaticFinalCode.pageSize);
        PageHelper.startPage(pageNum,StaticFinalCode.pageSize);
        List<com.datealive.pojo.Tag> blogInfoByTag = tagMapper.getBlogInfoByTag(tag_id);
        if(blogInfoByTag.isEmpty()){
            return new PageResult<com.datealive.pojo.Tag>(ResultCode.Not_Found,totalPage,pageNum,null);
        }else{
            return new PageResult<com.datealive.pojo.Tag>(ResultCode.Success,totalPage,pageNum,blogInfoByTag);
        }
    }

    @Override
    public Tag getTagByName(String tag_name) {
        return tagMapper.getTagByName(tag_name);
    }

    @Override
    public boolean saveTag(Tag tag) {
        int saveData = tagMapper.saveTag(tag);
        if(saveData>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateTag(Tag tag) {
        Integer tag_id = tag.getTag_id();
        if(tag_id!=null&&tag_id!=0){
            int updateData = tagMapper.updateTag(tag);
            if(updateData>0){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public int getRelationId(Integer blog_id, Integer tag_id) {
        return tagMapper.getRelationId(blog_id,tag_id);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean deleteTag(Integer tag_id) {
        int deleteTag= tagMapper.deleteTag(tag_id);
        int deleteRela = tagMapper.deleteTagRelationBytagId(tag_id);
        if(deleteTag>0&&deleteRela>0){
            return true;
        }else if(deleteTag>0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean saveTagRelation(Integer blog_id, Integer tag_id) {
        int relation = tagMapper.saveTagRelation(blog_id, tag_id);
        if(relation>0){
            return true;
        }else{
            return false;
        }
    }



    @Override
    public boolean updateTagRelation(Integer blog_id, Integer tag_id, Integer relation_id) {
        int updateRelation = tagMapper.updateTagRelation(blog_id,tag_id,relation_id);
        if(updateRelation>0){
            return true;
        }else {
            return false;
        }
    }



    @Override
    public boolean deleteTagRelationByblogId(Integer blog_id) {
        int deleteByblogId = tagMapper.deleteTagRelationByblogId(blog_id);
        if(deleteByblogId>0){
            return true;
        }else{
            return false;
        }
    }
}
