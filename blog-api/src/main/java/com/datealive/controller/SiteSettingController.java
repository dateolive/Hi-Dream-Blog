package com.datealive.controller;

import com.datealive.common.Result;
import com.datealive.common.ResultCode;
import com.datealive.service.SiteSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName: SiteSettingController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/26  12:57
 */
@RestController
@Slf4j
public class SiteSettingController {

    @Autowired
    SiteSettingService siteSettingService;

    @GetMapping("/siteInfo")
    public Result getSiteInfo(){
        Map<Object, Object> siteInfo = siteSettingService.getSiteInfo();
        if(siteInfo.isEmpty()){
            return Result.error(ResultCode.Not_Found,"无数据");
        }else{
            return Result.success("请求成功",siteInfo);
        }
    }
}
