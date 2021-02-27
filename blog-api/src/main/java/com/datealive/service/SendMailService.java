package com.datealive.service;

import com.datealive.service.vo.CommentSub;

/**
 * @ClassName: SendMail
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/16  9:11
 */
public interface SendMailService {
    /**
     * 根据提交的评论vo类发送普通邮件
     * @param commentSub
     * @param path
     */
    void sendSimpleMailByCommentSub(CommentSub commentSub,String path);

    /**
     * 封装一个普通的邮件发送函数，便于controller层调用
     * @param text
     * @param title
     * @param to
     * @return
     */
    void sendSimpleMail(String text, String title, String to);

    /**
     * 发送复杂邮件，这个业务层暂时用不到，如果有需要，参数请修改，需要传入file
     * @param commentSub
     * @return
     */
    void sendComplexMail(CommentSub commentSub);
}
