package com.datealive.service.impl;

import com.datealive.common.StaticFinalCode;
import com.datealive.mapper.SiteSettingMapper;
import com.datealive.pojo.SiteSetting;
import com.datealive.service.SiteSettingService;
import com.datealive.service.vo.Badge;
import com.datealive.service.vo.Copyright;
import com.datealive.service.vo.Favorite;
import com.datealive.service.vo.Introduction;
import com.datealive.utils.JacksonUtils;
import com.datealive.utils.RedisUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: SiteSettingServiceImpl
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/26  9:37
 */
@Service
public class SiteSettingServiceImpl implements SiteSettingService {

    @Autowired
    SiteSettingMapper siteSettingMapper;

    @Autowired
    RedisUtil redisUtil;

    /**
     * 匹配\\中的中文
     */
    private static final Pattern PATTERN = Pattern.compile("\"(.*?)\"");

    @Override
    public Map<Object, List<SiteSetting>> getSiteList() {
        List<SiteSetting> siteSettings = siteSettingMapper.getList();
        Map<Object, List<SiteSetting>> map = new HashMap<>();
        List<SiteSetting> type1 = new ArrayList<>();
        List<SiteSetting> type2 = new ArrayList<>();
        List<SiteSetting> type3 = new ArrayList<>();
        List<SiteSetting> type4 = new ArrayList<>();
        for (SiteSetting s : siteSettings) {
            if (s.getType() == 1) {
                type1.add(s);
            } else if (s.getType() == 2) {
                type2.add(s);
            } else if (s.getType() == 3) {
                type3.add(s);
            } else if(s.getType()==4){
                type4.add(s);
            }
        }
        map.put("type1", type1);
        map.put("type2", type2);
        map.put("type3", type3);
        map.put("type4",type4);

        return map;
    }

    @Override
    public Map<Object, Object> getSiteInfo() {
        String redisKey = StaticFinalCode.SITE_INFO_MAP;
        Map<Object, Object> siteInfoMapFromRedis = (Map<Object, Object>) redisUtil.hget(redisKey,"Get:siteInfo");
        if (siteInfoMapFromRedis != null) {
         //   System.out.println("=============");
            return siteInfoMapFromRedis;
        }
        List<SiteSetting> siteSettings = siteSettingMapper.getList();
        Map<Object, Object> map = new HashMap<>();
        Map<Object, Object> siteInfo = new HashMap<>();
        List<Badge> badges = new ArrayList<>();
        Introduction introduction = new Introduction();
        List<Favorite> favorites = new ArrayList<>();
        List<String> rollTexts = new ArrayList<>();
        for (SiteSetting s : siteSettings) {
            if (s.getType() == 1) {
                if ("copyright".equals(s.getName_en())) {
                    Copyright copyright = JacksonUtils.readValue(s.getValue(), Copyright.class);
                    siteInfo.put(s.getName_en(), copyright);
                } else {
                    siteInfo.put(s.getName_en(), s.getValue());
                }
            } else if (s.getType() == 2) {
                Badge badge = JacksonUtils.readValue(s.getValue(), Badge.class);
                badges.add(badge);
            } else if (s.getType() == 3) {
                if ("avatar".equals(s.getName_en())) {
                    introduction.setAvatar(s.getValue());
                } else if ("name".equals(s.getName_en())) {
                    introduction.setName(s.getValue());
                } else if ("github".equals(s.getName_en())) {
                    introduction.setGithub(s.getValue());
                } else if ("qq".equals(s.getName_en())) {
                    introduction.setQq(s.getValue());
                } else if ("bilibili".equals(s.getName_en())) {
                    introduction.setBilibili(s.getValue());
                } else if ("netease".equals(s.getName_en())) {
                    introduction.setNetease(s.getValue());
                }else if("musicId".equals(s.getName_en())){
                    introduction.setMusicId(s.getValue());
                } else if("backGroundImage".equals(s.getName_en())){
                    introduction.setBackGroundImage(s.getValue());
                }else if ("email".equals(s.getName_en())) {
                    introduction.setEmail(s.getValue());
                } else if ("favorite".equals(s.getName_en())) {
                    Favorite favorite = JacksonUtils.readValue(s.getValue(), Favorite.class);
                    favorites.add(favorite);
                } else if ("rollText".equals(s.getName_en())) {
                    Matcher m = PATTERN.matcher(s.getValue());
                    while (m.find()) {
                        rollTexts.add(m.group(1));
                    }
                }
            }
        }
        introduction.setFavorites(favorites);
        introduction.setRollText(rollTexts);
        map.put("introduction", introduction);
        map.put("siteInfo", siteInfo);
        map.put("badges", badges);
        redisUtil.hset(redisKey,"Get:siteInfo",map);
        return map;

    }

    @Override
    public boolean updateSiteSetting(List<LinkedHashMap> siteSettings, List<Integer> deleteIds) {
        for (Integer id : deleteIds) {//删除
            deleteOneSiteSettingById(id);
        }
        for (LinkedHashMap s : siteSettings) {
            SiteSetting siteSetting = JacksonUtils.convertValue(s, SiteSetting.class);
            if (siteSetting.getId() != null) {//修改
                updateOneSiteSetting(siteSetting);
            } else {//添加
                saveOneSiteSetting(siteSetting);
            }
        }
        deleteSiteInfoRedisCache();
        return true;
    }

    @Transactional(rollbackFor=Exception.class)
    public void saveOneSiteSetting(SiteSetting siteSetting) {
        if (siteSettingMapper.saveSiteSetting(siteSetting) != 1) {
            throw new PersistenceException("配置添加失败");
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public void updateOneSiteSetting(SiteSetting siteSetting) {
        if (siteSettingMapper.updateSiteSetting(siteSetting) != 1) {
            throw new PersistenceException("配置修改失败");
        }
    }

    @Transactional(rollbackFor=Exception.class)
    public void deleteOneSiteSettingById(Integer id) {
        if (siteSettingMapper.deleteSiteSettingById(id) != 1) {
            throw new PersistenceException("配置删除失败");
        }
    }

    /**
     * 删除站点信息缓存
     */
    private void deleteSiteInfoRedisCache() {
        redisUtil.del(StaticFinalCode.SITE_INFO_MAP);
    }
}
