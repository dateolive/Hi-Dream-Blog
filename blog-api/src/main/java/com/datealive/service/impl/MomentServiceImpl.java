package com.datealive.service.impl;

import com.datealive.common.PageResult;
import com.datealive.common.ResultCode;
import com.datealive.common.StaticFinalCode;
import com.datealive.mapper.MomentMapper;
import com.datealive.pojo.Moment;
import com.datealive.service.MomentService;
import com.datealive.utils.markdowntoHtml.MarkdownUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: MomentServiceImpl
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/17  18:33
 */
@Service
public class MomentServiceImpl implements MomentService {

    @Autowired
    MomentMapper momentMapper;

    @Override
    public PageResult<Moment> getMomentList(Integer pageNum) {
        int count = momentMapper.getMomentCount();
        int totalPage = (int) Math.ceil(count * 1.0 / StaticFinalCode.pageSize);
        PageHelper.startPage(pageNum, StaticFinalCode.pageSize);
        List<Moment> momentList = momentMapper.getMomentList();
        if (momentList.isEmpty()) {
            return new PageResult<Moment>(ResultCode.Not_Found, totalPage, pageNum, null);
        } else {
            return new PageResult<Moment>(ResultCode.Success, totalPage, pageNum, momentList);
        }

    }

    @Override
    public PageResult<Moment> getMomentListWithPublish(Integer pageNum) {
        int countWithPublish = momentMapper.getMomentCountWithPublish();
        int totalPage = (int) Math.ceil(countWithPublish * 1.0 / StaticFinalCode.pageSize);
        PageHelper.startPage(pageNum, StaticFinalCode.pageSize);
        List<Moment> momentListWithPublish = momentMapper.getMomentListWithPublish();
        for (Moment listWithPublish : momentListWithPublish) {
            listWithPublish.setContent(MarkdownUtils.markdownToHtmlExtensions(listWithPublish.getContent()));
        }
        if (momentListWithPublish.isEmpty()) {
            return new PageResult<Moment>(ResultCode.Not_Found, totalPage, pageNum, null);
        } else {
            return new PageResult<Moment>(ResultCode.Success, totalPage, pageNum, momentListWithPublish);
        }
    }

    @Override
    public boolean addLikeByMomentId(Integer momentId) {
        int likeByMomentId = momentMapper.addLikeByMomentId(momentId);
        if(likeByMomentId>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateMomentPublishedById(Integer momentId, Integer published) {
        int publishedById = momentMapper.updateMomentPublishedById(momentId, published);
        if(publishedById>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Moment getMomentById(Integer id) {
        Moment momentById = momentMapper.getMomentById(id);
        return momentById;
    }

    @Override
    public Moment getMomentByIdToHtml(Integer id) {
        Moment moment = momentMapper.getMomentById(id);
        String content = moment.getContent();
        String toHtmlExtensions = MarkdownUtils.markdownToHtmlExtensions(content);
        moment.setContent(toHtmlExtensions);
        return moment;
    }

    @Override
    public boolean deleteMomentById(Integer id) {
        int deleteMomentById = momentMapper.deleteMomentById(id);
        if(deleteMomentById>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean saveMoment(Moment moment) {
        moment.setCreateTime(new Date());
        moment.setLikes(0);
        int saveMoment = momentMapper.saveMoment(moment);
        if(saveMoment>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateMoment(Moment moment) {
        Integer momentId = moment.getId();
        if(momentId!=null){
            int updateMoment = momentMapper.updateMoment(moment);
            if(updateMoment>0){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public int getMomentCountWithPublish() {
        return momentMapper.getMomentCountWithPublish();
    }

    @Override
    public int getMomentCount() {
        return momentMapper.getMomentCount();
    }
}
