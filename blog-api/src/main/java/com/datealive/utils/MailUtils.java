package com.datealive.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * 邮件工具类
 * @author dateaive
 */
@Component
public class MailUtils {

    @Autowired
    private JavaMailSender javaMailSender;


    /**
     * 发送简单邮件
     *
     * @param text  邮件正文
     * @param title 标题
     * @param to    接收方
     */
    public boolean sendSimpleMail(String text, String title, String... to) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("2448282543@qq.com");
            messageHelper.setTo(to);
            messageHelper.setSubject(title);
            messageHelper.setText(text, true);
            javaMailSender.send(mimeMessage);
            //发送成功返回true
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //发送失败返回false
        return false;
    }


    /**
     * 发送复杂邮件
     *
     * @param text     邮件正文
     * @param title    标题
     * @param fileName 附件名
     * @param file     附件
     * @param to       接收方
     */
    public boolean sendComplexMail(String text, String title, String fileName, File file, String... to) {
        try {
            //一个复杂的邮件任务
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            //组装邮件
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject(title);
            helper.setText(text, true);
            helper.addAttachment(fileName, file);//添加附件
            helper.setTo(to);//设置发送给谁
            helper.setFrom("2448282543@qq.com");//设置从哪里来的
            javaMailSender.send(mimeMessage);//发送邮件
            //发送成功返回true
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //发送失败返回false
        return false;
    }
}
