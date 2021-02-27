package com.datealive.task;

import com.datealive.annotation.LogAnno;
import com.datealive.common.StaticFinalCode;
import com.datealive.mapper.BlogArticleMapper;
import com.datealive.service.vo.BlogArticleInfo;
import com.datealive.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName: RedisViewCountTask
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/12  16:41
 */
@Component
@EnableScheduling
@Slf4j
public class RedisViewCountTask {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    BlogArticleMapper blogArticleMapper;

    @LogAnno(operateType="saveViewCntToDataBase",description = "从redis中读取浏览量到数据库",classification = 3)
    @Scheduled(cron = "0 0 1 * * ?")
   // @Scheduled(cron = "0 */1 * * * ?") 1分钟
    public void saveViewCntToDataBase(){
        log.info("进行了定时任务");
        List<BlogArticleInfo> blogList = blogArticleMapper.getAllBlogArticleWithStatus();
        for (BlogArticleInfo blog : blogList) {
            String key= StaticFinalCode.KEY_BLOG_ARTICLE_VIEW_ProFix+blog.getBlog_id();
            Integer hGetViewCnt = (Integer) redisUtil.hget(key, "post:viewCount");
            if(hGetViewCnt!=null) {
                blogArticleMapper.increaseBlogViews(blog.getBlog_id(),hGetViewCnt);
                redisUtil.del(key);
            }
        }
    }
}
