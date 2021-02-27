package com.datealive.service.impl;

import com.datealive.common.PageResult;
import com.datealive.common.ResultCode;
import com.datealive.common.StaticFinalCode;
import com.datealive.mapper.CateGoryMapper;
import com.datealive.service.CateGoryService;
import com.datealive.service.vo.CateGory;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: CateGoryServiceImpl
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/1  21:08
 */
@Service
public class CateGoryServiceImpl implements CateGoryService {

    @Autowired
    CateGoryMapper cateGoryMapper;


    @Override
    public List<CateGory> getGateGoryIdAndName() {
        return cateGoryMapper.getGateGoryIdAndName();
    }


    @Override
    public PageResult<com.datealive.pojo.CateGory> getBlogInfoByCategory(Integer category_id, Integer pageNum) {
        int categorysCnt = cateGoryMapper.getCateGoryBlogCnt(category_id);
        int totalPage = (int) Math.ceil(categorysCnt* 1.0 / StaticFinalCode.pageSize);
        PageHelper.startPage(pageNum,StaticFinalCode.pageSize);
        List<com.datealive.pojo.CateGory> blogInfoByCategory = cateGoryMapper.getBlogInfoByCategory(category_id);
        if(blogInfoByCategory.isEmpty()){
            return new PageResult<com.datealive.pojo.CateGory>(ResultCode.Not_Found,totalPage,pageNum,null);
        }else{
            return new PageResult<com.datealive.pojo.CateGory>(ResultCode.Success,totalPage,pageNum,blogInfoByCategory);
        }
    }

    @Override
    public PageResult<CateGory> getCateGoryByPage(Integer pageNum) {
        int categorysCnt = cateGoryMapper.getCateGoryCnt();
        int totalPage = (int) Math.ceil(categorysCnt* 1.0 / StaticFinalCode.pageSize);
        PageHelper.startPage(pageNum,StaticFinalCode.pageSize);
        List<CateGory> gateGoryIdAndName = cateGoryMapper.getGateGoryIdAndName();
        if(gateGoryIdAndName.isEmpty()){
            return new PageResult<CateGory>(ResultCode.Not_Found,totalPage,pageNum,null);
        }else{
            return new PageResult<CateGory>(ResultCode.Success,totalPage,pageNum,gateGoryIdAndName);
        }
    }

    @Override
    public CateGory getCateGoryByName(String category_name) {
        return cateGoryMapper.getCateGoryByName(category_name);
    }


    @Override
    public boolean saveCategory(CateGory cateGory) {
        int saveData = cateGoryMapper.saveCategory(cateGory);
        if(saveData>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateCategory(CateGory cateGory) {
        Integer category_id = cateGory.getCategory_id();
        if(category_id!=null&&category_id!=0){
            int updateData = cateGoryMapper.updateCategory(cateGory);
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
    public int getRelationId(Integer blog_id, Integer category_id) {
        return cateGoryMapper.getRelationId(blog_id,category_id);
    }

    @Override
    public boolean saveCategoryRelation(Integer blog_id, Integer category_id) {
        int relation = cateGoryMapper.saveCategoryRelation(blog_id, category_id);
        if(relation>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateCategoryRelation(Integer blog_id, Integer category_id,Integer relation_id) {
        int updateRelation = cateGoryMapper.updateCategoryRelation(blog_id,category_id,relation_id);
        if(updateRelation>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean deleteCategory(Integer category_id) {
        int deleteCategory = cateGoryMapper.deleteCategory(category_id);
        int deleteRela = cateGoryMapper.deleteCategoryRelationBycateId(category_id);
        if(deleteCategory>0&&deleteRela>0){
            return true;
        }else if(deleteCategory>0) {
            return true;
        } else {
            return false;
        }
    }



    @Override
    public boolean deleteCateGoryRelationByblogId(Integer blog_id) {
        int deleteByblogId = cateGoryMapper.deleteCateGoryRelationByblogId(blog_id);
        if(deleteByblogId>0){
            return true;
        }else{
            return false;
        }
    }
}
