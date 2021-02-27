package com.datealive.controller;

import com.datealive.common.PageResult;
import com.datealive.common.Result;
import com.datealive.pojo.Comment;
import com.datealive.service.CommentService;
import com.datealive.service.SendMailService;
import com.datealive.service.dto.CommentQuery;
import com.datealive.service.vo.CommentSub;
import com.datealive.utils.MailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName: CommentController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/10  14:31
 */
@RestController
@Slf4j
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    SendMailService sendMailService;


    @PostMapping("/comment/query")
    public PageResult<Comment> getComment(@RequestBody CommentQuery commentQuery){
        return commentService.getAllCommentByQuery(commentQuery);
    }

    @PostMapping("/comment/save")
    public Result saveComment(@Validated @RequestBody CommentSub commentSub){
        boolean saveComment = commentService.saveComment(commentSub);
        if(saveComment){
            String path="";
            log.info(commentSub.getNick_name()+"评论成功");
            if(commentSub.getPage()==0){
                path="/article/"+commentSub.getBlog_id();
            }else if(commentSub.getPage()==1){
                path = "/friends";
            }else if(commentSub.getPage()== 2){
                path = "/about";
            }
            sendMailService.sendSimpleMailByCommentSub(commentSub,path);
            return Result.success("评论成功，待博主审核");
        }else{
            log.error(commentSub.getNick_name()+"评论失败");
            return Result.error("评论失败,请检查你的格式");
        }
    }

}
