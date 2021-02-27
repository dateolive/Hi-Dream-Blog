package com.datealive.controller.admin;

import com.datealive.annotation.LogAnno;
import com.datealive.common.PageResult;
import com.datealive.common.Result;
import com.datealive.common.ResultCode;
import com.datealive.mapper.CommentMapper;
import com.datealive.pojo.Comment;
import com.datealive.service.CommentService;
import com.datealive.service.SendMailService;
import com.datealive.service.dto.CommentUpdStatus;
import com.datealive.utils.MailUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: CommentAdminCotroller
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/10  14:49
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class CommentAdminCotroller {

    @Autowired
    CommentService commentService;

    @Autowired
    SendMailService sendMailService;

    @RequiresAuthentication
    @GetMapping("/comment")
    public PageResult<Comment> getAllComment(@RequestParam(defaultValue = "1") Integer currentPage){
        PageResult<Comment> allComment = commentService.getAllComment(currentPage);
        return allComment;
    }

    @RequiresAuthentication
    @GetMapping("/comment/{name}")
    public Result getCommentByNickName(@PathVariable("name") String nick_name){
        List<Comment> commentByNickName = commentService.getCommentByNickName(nick_name);
        if(commentByNickName.isEmpty()){
            return Result.error(ResultCode.Not_Found,"无数据");
        }else{
            return Result.success("请求成功",commentByNickName);
        }
    }
    @RequiresAuthentication
    @GetMapping("/comment/allCnt")
    public Result getAllCommentCnt(){
        int allCommentCnt = commentService.getAllCommentCnt();
        return Result.success("请求成功",allCommentCnt);
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="DELETE",description = "删除评论",classification = 1)
    @RequiresAuthentication
    @DeleteMapping("/comment/delete")
    public Result deleteComment(@RequestParam Integer comment_id){
        boolean deleteComment = commentService.deleteComment(comment_id);
        if(deleteComment){
            log.info("删除了评论");
            return Result.success("删除成功");
        }else {
            log.info("评论删除失败");
            return Result.error("删除失败");
        }
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="PUT",description = "发送邮件任务",classification = 3)
    @RequiresAuthentication
    @PutMapping("/comment/isCheck")
    public Result checkComment(@RequestBody CommentUpdStatus commentUpdStatus){
        Integer comment_id = commentUpdStatus.getComment_id();
        Integer is_check = commentUpdStatus.getIs_check();
        boolean checkComment = commentService.checkComment(comment_id, is_check);
        if(checkComment){
            log.info("审核通过友链");
            String email=commentService.getEmailByCommentId(comment_id);
            if(!email.isEmpty()&&is_check==1){
                sendMailService.sendSimpleMail("您的留言已经通过审核，请返回站点查看~","梦独吟博客留言审核通过",email);
            }
            return Result.success("审核通过");
        }else{
            return Result.error("请求失败");
        }
    }

}
