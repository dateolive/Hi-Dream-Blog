package com.datealive.service;

import com.datealive.common.PageResult;
import com.datealive.pojo.Moment;


/**
 * @ClassName: MomentService
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/17  18:27
 */
public interface MomentService {
    /**
     * 分页获取所有动态
     * @param pageNum
     * @return
     */
    PageResult<Moment> getMomentList(Integer pageNum);

    /**
     * 分页获取已经发布的了动态
     * @param pageNum
     * @return
     */
    PageResult<Moment> getMomentListWithPublish(Integer pageNum);

    /**
     * 点赞动态
     * @param momentId
     * @return
     */
    boolean addLikeByMomentId(Integer momentId);

    /**
     * 更新动态状态
     * @param momentId
     * @param published
     * @return
     */
    boolean updateMomentPublishedById(Integer momentId, Integer published);

    /**
     * 根据id获取动态 返回给前端后台编写 md格式
     * @param id
     * @return
     */
    Moment getMomentById(Integer id);

    /**
     * 根据id获取动态  返回给前端前台展示  html格式
     * @param id
     * @return
     */
    Moment getMomentByIdToHtml(Integer id);

    /**
     * 删除动态
     * @param id
     * @return
     */
    boolean deleteMomentById(Integer id);

    /**
     * 添加动态
     * @param moment
     * @return
     */
    boolean saveMoment(Moment moment);

    /**
     * 更新动态
     * @param moment
     * @return
     */
    boolean updateMoment(Moment moment);

    /**
     * 获取已经发布了的动态数量
     * @return
     */
    int getMomentCountWithPublish();

    /**
     * 获取所有动态数量
     * @return
     */
    int getMomentCount();
}
