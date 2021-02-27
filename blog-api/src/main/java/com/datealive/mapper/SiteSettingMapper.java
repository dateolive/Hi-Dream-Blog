package com.datealive.mapper;

import com.datealive.pojo.SiteSetting;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: SiteSettingMapper
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/26  9:04
 */
@Mapper
@Repository
public interface SiteSettingMapper {
    /**
     * 获取站点配置list
     * @return
     */
    List<SiteSetting> getList();

    /**
     * 获取友链页面信息自定义
     * @return
     */
    List<SiteSetting> getFriendInfo();

    /**
     * 更新站点自定义
     * @param siteSetting
     * @return
     */
    int updateSiteSetting(SiteSetting siteSetting);

    /**
     * 根据id删除站点自定义
     * @param id
     * @return
     */
    int deleteSiteSettingById(Integer id);

    /**
     * 插入站点自定义
     * @param siteSetting
     * @return
     */
    int saveSiteSetting(SiteSetting siteSetting);
}
