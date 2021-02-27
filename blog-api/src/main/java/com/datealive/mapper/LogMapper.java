package com.datealive.mapper;

import java.util.List;

import com.datealive.pojo.Log;
import com.datealive.service.dto.LogQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



/**
 * @Classname LogMapper
 * @Description 日志Mapper
 * @Date 2021/2/17 15:06
 * @author 冰羽
 */

@Mapper
@Repository
public interface LogMapper {

    /***
     *    //向数据库添加日志
     * @param log
     * @return
     */
    int insertLog(Log log);

    /**
     * 根据LogQuery  的查询条件查询log日志
     * @param logQuery
     * @return
     */
    public List<Log> getLogListByLogQuery(LogQuery logQuery);

    /**
     * 查询不同分类log的数量
     * @param classification
     * @return
     */
    public int getLogCountByClassification(@Param("classification") Integer classification);

    /**
     * 根据id删除日志
     * @param id
     * @return
     */
    public int deleteLog(@Param("id")Integer id);

}
