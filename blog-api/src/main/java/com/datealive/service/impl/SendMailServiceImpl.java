package com.datealive.service.impl;

import com.datealive.annotation.LogAnno;
import com.datealive.pojo.Comment;
import com.datealive.service.CommentService;
import com.datealive.service.SendMailService;
import com.datealive.service.vo.CommentSub;
import com.datealive.utils.MailUtils;
import com.datealive.utils.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @ClassName: SendMailServiceImpl
 * @Description: 异步进行邮件发送业务层
 * @author: datealive
 * @date: 2021/2/16  9:15
 */
@Slf4j
@Component
@Service
public class SendMailServiceImpl implements SendMailService {

    @Autowired
    MailUtils mailUtils;

    @Autowired
    CommentService commentService;

    private static String websiteUrl;

    @Value("${server.website.url}")
    public void setWebsiteUrl(String websiteUrl) {
        SendMailServiceImpl.websiteUrl = websiteUrl;
    }


    @Async("taskExecutor")
    @Override
    public void sendSimpleMailByCommentSub(CommentSub commentSub,String path) {
        try{
            Thread.sleep(1000);
            boolean isEmail = ValidatorUtil.isEmail(commentSub.getEmail());
            Integer pid = commentSub.getPid();
            if(isEmail&&pid==0){
                mailUtils.
                        sendSimpleMail(
                                "<div style=\"border-radius: 10px 10px 10px 10px;font-size:13px;    color: #555555;width: 666px;font-family:'Century Gothic','Trebuchet MS','Hiragino Sans GB',微软雅黑,'Microsoft Yahei',Tahoma,Helvetica,Arial,'SimSun',sans-serif;margin:50px auto;border:1px solid #eee;max-width:100%;background: #ffffff repeating-linear-gradient(-45deg,#fff,#fff 1.125rem,transparent 1.125rem,transparent 2.25rem);box-shadow: 0 1px 5px rgba(0, 0, 0, 0.15);\"><div style=\"width:100%;background:#49BDAD;color:#ffffff;border-radius: 10px 10px 0 0;background-image: -moz-linear-gradient(0deg, #36D1DC, #5B86E5);background-image: -webkit-linear-gradient(0deg, #36D1DC, #5B86E5);height: 66px;\"><p style=\"font-size:15px;word-break:break-all;padding: 23px 32px;margin:0;background-color: hsla(0,0%,100%,.1);border-radius: 10px 10px 0 0;\">"+"" +
                                        "您的<a style=\"text-decoration:none;color: #ffffff;\" href=\""+websiteUrl+"\">梦独吟博客</a>上有新的留言：</p></div><div style=\"margin:40px auto;width:90%\"><p>"+commentSub.getNick_name()+" 给您的留言如下：</p><div style=\"background: #fafafa repeating-linear-gradient(-45deg,#fff,#fff 1.125rem,transparent 1.125rem,transparent 2.25rem);box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);margin:20px 0px;padding:15px;border-radius:5px;font-size:14px;color:#555555;\">"+commentSub.getContent()+"</div><p>您可以点击<a style=\"text-decoration:none; color:#12addb\" href=\""+websiteUrl+path+"\">查看回复的完整內容</a>，回到博客<a style=\"text-decoration:none; color:#12addb\" href=\""+websiteUrl+"\"> 梦独吟博客</a>。</p><style type=\"text/css\">a:link{text-decoration:none}a:visited{text-decoration:none}a:hover{text-decoration:none}a:active{text-decoration:none}</style></div></div>\n",
                                commentSub.getNick_name() + "在博客留言,请尽快审核",
                                "2448282543@qq.com");
                log.info("成功执行了邮件发送任务");
            }else if(isEmail&&pid!=0){
                String emailByCommentId = commentService.getEmailByCommentId(pid);
                Comment parentComment = commentService.getCommentByPid(pid);
              //  System.out.println("pid===========>"+pid);
                //System.out.println("pidemail======>"+emailByCommentId);
                mailUtils.
                        sendSimpleMail(
                                "<div style=\"border-radius: 10px 10px 10px 10px;font-size:13px;    color: #555555;width: 666px;font-family:'Century Gothic','Trebuchet MS','Hiragino Sans GB',微软雅黑,'Microsoft Yahei',Tahoma,Helvetica,Arial,'SimSun',sans-serif;margin:50px auto;border:1px solid #eee;max-width:100%;background: #ffffff repeating-linear-gradient(-45deg,#fff,#fff 1.125rem,transparent 1.125rem,transparent 2.25rem);box-shadow: 0 1px 5px rgba(0, 0, 0, 0.15);\"><div style=\"width:100%;background:#49BDAD;color:#ffffff;border-radius: 10px 10px 0 0;background-image: -moz-linear-gradient(0deg, rgb(67, 198, 184), rgb(255, 209, 244));background-image: -webkit-linear-gradient(0deg, #2193b0, #6dd5ed);height: 66px;\"><p style=\"font-size:15px;word-break:break-all;padding: 23px 32px;margin:0;background-color: hsla(0,0%,100%,.1);border-radius: 10px 10px 0 0;\">"+"" +
                                        "您在<a style=\"text-decoration:none;font-weight:bold;color: #ffffff;\" href=\""+websiteUrl+path+"\"> 梦独吟博客</a>上的留言有新回复啦！</p></div><div style=\"margin:40px auto;width:90%\"><p><span style=\"font-weight: bold\">"+parentComment.getNick_name()+"</span> 童鞋，您曾在 <a style=\"text-decoration:none; color:#12addb\" href=\""+websiteUrl+path+"\">这个页面</a> 上发表评论：</p><div style=\"background: #fafafa repeating-linear-gradient(-45deg,#fff,#fff 1.125rem,transparent 1.125rem,transparent 2.25rem);box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);margin:20px 0px;padding:15px;border-radius:5px;font-size:14px;color:#555555;\">"+parentComment.getContent()+"</div><p><span style=\"font-weight: bold\">"+commentSub.getNick_name()+"</span> 回复道：</p><div style=\"background: #fafafa repeating-linear-gradient(-45deg,#fff,#fff 1.125rem,transparent 1.125rem,transparent 2.25rem);box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);margin:20px 0px;padding:15px;border-radius:5px;font-size:14px;color:#555555;\">"+commentSub.getContent()+"</div><p>您可以点击<a style=\"text-decoration:none; color:#12addb\" href=\""+websiteUrl+path+"\">查看回复的完整內容</a>，欢迎再次光临<a style=\"text-decoration:none; color:#12addb\"                href=\""+websiteUrl+"\"> 梦独吟博客</a>。</p><style type=\"text/css\">a:link{text-decoration:none}a:visited{text-decoration:none}a:hover{text-decoration:none}a:active{text-decoration:none}</style></div></div>\n" ,
                                commentSub.getNick_name() + "在博客回复你了",
                                emailByCommentId);

            } else{
                log.info("邮箱格式不正确");
            }

        }catch (Exception e){
            log.error("邮件任务执行失败");
            e.printStackTrace();
        }

    }

    @Async("taskExecutor")
    @Override
    public void sendSimpleMail(String text, String title, String to) {
        try {
            // 异步执行
            Thread.sleep(1000);
            boolean isEmail = ValidatorUtil.isEmail(to);
            if(isEmail){
                mailUtils.sendSimpleMail(text, title, to);
                log.info("成功执行了邮件发送任务");
            }else{
                log.info("邮箱格式不正确");
            }
        }catch (Exception e){
            log.error("邮件任务执行失败");
            e.printStackTrace();
        }

    }

    /**
     * 此接口暂时用不到
     * @param commentSub
     * @return
     */
    @Override
    public void sendComplexMail(CommentSub commentSub) {

    }
}
