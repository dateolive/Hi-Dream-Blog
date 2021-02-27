package com.datealive.controller.admin;

import com.datealive.common.PageResult;
import com.datealive.common.Result;
import com.datealive.pojo.Log;
import com.datealive.service.LogService;
import com.datealive.service.dto.LogQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: LogAdminController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/23  9:46
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class LogAdminController {

    @Autowired
    LogService logService;

    @RequiresAuthentication
    @PostMapping("/logList/query")
    public PageResult<Log> getLogListByLogQuery(@RequestBody LogQuery logQuery){
        return logService.getLogListByLogQuery(logQuery);
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @RequiresAuthentication
    @DeleteMapping("/deleteLog")
    public Result deleteLogById(@RequestParam Integer id){
        boolean deleteLogById = logService.deleteLogById(id);
        if(deleteLogById){
            return Result.success("删除成功");
        }else{
            return Result.error("删除失败");
        }
    }



}
