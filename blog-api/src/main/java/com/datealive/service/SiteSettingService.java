package com.datealive.service;

import com.datealive.pojo.SiteSetting;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SiteSettingService
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/26  9:22
 */
public interface SiteSettingService {
    /**
     * 获取站点配置
     * @return
     */
    Map<Object, List<SiteSetting>> getSiteList();

    /**
     * 获取站点配置
     * @return
     */
    Map<Object, Object> getSiteInfo();

    /**
     * 更新和删除和添加 站点
     * @param siteSettings
     * @param deleteIds
     * @return
     */
    boolean updateSiteSetting(List<LinkedHashMap> siteSettings, List<Integer> deleteIds);

}
