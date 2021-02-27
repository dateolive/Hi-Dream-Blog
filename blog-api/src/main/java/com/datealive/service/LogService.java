package com.datealive.service;

import com.datealive.common.PageResult;
import com.datealive.pojo.Log;
import com.datealive.service.dto.LogQuery;
import org.springframework.scheduling.annotation.Async;

import java.sql.SQLException;
import java.util.List;




/**
 * @Classname LogService
 * @Description 日志Service
 * @Date 2021/2/17 15:06
 * @author 冰羽
 */

public interface LogService {
    /**
     * 添加日志
     * @param log
     * @return
     * @throws SQLException
     */
    public void addLog(Log log) throws SQLException;

    /**
     * 根据logQuery 查询log日志
     * @param logQuery
     * @return
     */
    public PageResult<Log> getLogListByLogQuery(LogQuery logQuery);

    /**
     * 根据id删除日志
     * @param id
     * @return
     */
    public boolean deleteLogById(Integer id);
}