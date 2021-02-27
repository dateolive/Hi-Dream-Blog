package com.datealive.mapper;

import com.datealive.pojo.Moment;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: MomentMapper
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/17  18:20
 */
@Mapper
@Repository
public interface MomentMapper {
    /**
     * 获取所有动态
     * @return
     */
    List<Moment> getMomentList();

    /**
     * 获取已经发布的动态
     * @return
     */
    List<Moment> getMomentListWithPublish();

    /**
     * 点赞动态
     * @param momentId
     * @return
     */
    int addLikeByMomentId(Integer momentId);

    /**
     * 更新动态状态
     * @param momentId
     * @param is_published
     * @return
     */
    int updateMomentPublishedById(@Param("momentId") Integer momentId, @Param("is_published") Integer is_published);

    /**
     * 根据id获取动态
     * @param id
     * @return
     */
    Moment getMomentById(Integer id);

    /**
     * 根据id删除动态
     * @param id
     * @return
     */
    int deleteMomentById(Integer id);

    /**
     * 添加动态
     * @param moment
     * @return
     */
    int saveMoment(Moment moment);

    /**
     * 更新动态
     * @param moment
     * @return
     */
    int updateMoment(Moment moment);

    /**
     * 获取已经公开的动态数量
     * @return
     */
    int getMomentCountWithPublish();

    /**
     * 获取所有动态
     * @return
     */
    int getMomentCount();
}
