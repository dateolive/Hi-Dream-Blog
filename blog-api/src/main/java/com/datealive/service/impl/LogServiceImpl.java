package com.datealive.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.datealive.common.PageResult;
import com.datealive.common.ResultCode;
import com.datealive.common.StaticFinalCode;
import com.datealive.mapper.LogMapper;
import com.datealive.pojo.Log;
import com.datealive.service.LogService;
import com.datealive.service.dto.LogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;



/**
 * @Classname LogServiceImpl
 * @Description 日志管理实现
 * @Date 2021/2/17 15:06
 * @author 冰羽
 */
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Async
    @Override
    public void addLog(Log log) throws SQLException {
        logMapper.insertLog(log);
    }

    @Override
    public PageResult<Log> getLogListByLogQuery(LogQuery logQuery) {
        int count = logMapper.getLogCountByClassification(logQuery.getClassification());
        int totalPage = (int) Math.ceil(count* 1.0 / logQuery.getPageSize());
        PageHelper.startPage(logQuery.getCurrentPage(),logQuery.getPageSize());
        List<Log> logList = logMapper.getLogListByLogQuery(logQuery);
        if(logList.isEmpty()){
            return new PageResult<Log>(ResultCode.Not_Found,totalPage,logQuery.getCurrentPage(),null);
        }else{
            return new PageResult<Log>(ResultCode.Success,totalPage,logQuery.getCurrentPage(),logList);
        }
    }


    @Override
    public boolean deleteLogById(Integer id) {
        int deleteLog = logMapper.deleteLog(id);
        if(deleteLog>0){
            return true;
        }else{
            return false;
        }
    }

}