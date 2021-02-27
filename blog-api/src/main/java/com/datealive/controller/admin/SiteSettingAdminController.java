package com.datealive.controller.admin;

import com.datealive.annotation.LogAnno;
import com.datealive.common.Result;
import com.datealive.common.ResultCode;
import com.datealive.pojo.SiteSetting;
import com.datealive.service.SiteSettingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SiteSettingAdminController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/26  10:16
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class SiteSettingAdminController {

    @Autowired
    SiteSettingService siteSettingService;

    /**
     * 获取所有站点配置信息
     *
     * @return
     */
    @RequiresAuthentication
    @GetMapping("/siteSettings")
    public Result siteSettings() {
        Map<Object, List<SiteSetting>> typeMap = siteSettingService.getSiteList();
        if(typeMap.isEmpty()){
            return Result.error(ResultCode.Not_Found,"数据为空");
        }
        return Result.success("请求成功", typeMap);
    }

    /**
     * 修改、删除(部分配置可为空，但不可删除)、添加(只能添加部分)站点配置
     *
     * @param map 包含所有站点信息更新后的数据 map => {settings=[更新后的所有配置List], deleteIds=[要删除的配置id List]}
     * @return
     */
    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="POST",description = "修改站点信息",classification = 1)
    @RequiresAuthentication
    @PostMapping("/saveSiteSettings")
    public Result updateAll(@RequestBody Map<Object, Object> map) {
        List<LinkedHashMap> siteSettings = (List<LinkedHashMap>) map.get("settings");
        List<Integer> deleteIds = (List<Integer>) map.get("deleteIds");
        siteSettingService.updateSiteSetting(siteSettings, deleteIds);
        return Result.success("更新成功");
    }
}
