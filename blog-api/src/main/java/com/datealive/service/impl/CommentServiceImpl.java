package com.datealive.service.impl;

import com.datealive.common.PageResult;
import com.datealive.common.ResultCode;
import com.datealive.common.StaticFinalCode;
import com.datealive.mapper.CommentMapper;
import com.datealive.pojo.Comment;
import com.datealive.service.CommentService;
import com.datealive.service.dto.CommentQuery;
import com.datealive.service.vo.CommentSub;
import com.datealive.utils.QQInfoUtils;
import com.datealive.utils.StringUtils;
import com.datealive.utils.ValidatorUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: CommentServiceImpl
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/9  20:44
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public PageResult<Comment> getAllCommentByQuery(CommentQuery commentQuery) {
        int parentCommentCnt = commentMapper.getParentCommentCnt(commentQuery);
        int totalPage = (int) Math.ceil(parentCommentCnt* 1.0 / StaticFinalCode.pageSize);
        PageHelper.startPage(commentQuery.getCurrentPage(),StaticFinalCode.pageSize);
        List<Comment> parentComment = commentMapper.getParentComment(commentQuery);
        for (Comment comment : parentComment) {
            comment.setReplyComments(commentMapper.getChildComment(comment.getComment_id()));
        }
        if(parentComment.isEmpty()){
            return new PageResult<Comment>(ResultCode.Not_Found,totalPage,commentQuery.getCurrentPage(),null);
        }else{
            return new PageResult<Comment>(ResultCode.Success,totalPage,commentQuery.getCurrentPage(),parentComment);
        }

    }

    @Override
    public PageResult<Comment> getAllComment(Integer pageNum) {
        int parentCommentCnt = commentMapper.getAllCommentCnt();
        int totalPage = (int) Math.ceil(parentCommentCnt* 1.0 / StaticFinalCode.pageSize);
        PageHelper.startPage(pageNum,StaticFinalCode.pageSize);
        List<Comment> allComment = commentMapper.getAllComment();
        if(allComment.isEmpty()){
            return new PageResult<Comment>(ResultCode.Not_Found,totalPage,pageNum,null);
        }else{
            return new PageResult<Comment>(ResultCode.Success,totalPage,pageNum,allComment);
        }
    }

    @Override
    public List<Comment> getCommentByNickName(String nick_name) {
        List<Comment> commentByNickName = commentMapper.getCommentByNickName(nick_name);
        return commentByNickName;
    }

    @Override
    public int getAllCommentCnt() {
        return commentMapper.getAllCommentCnt();
    }

    @Override
    public boolean saveComment(CommentSub commentSub) {
        commentSub.setCreate_time(new Date());
        //如果有特殊字符的话
        if(StringUtils.hasSpecialChar(commentSub.getContent())||StringUtils.hasSpecialChar(commentSub.getNick_name())){
            commentSub.setIs_check(0);
        }else{
            commentSub.setIs_check(1);
            System.out.println(commentSub.getIs_check());
        }
        //判断提交的email是否正确
        if(ValidatorUtil.isEmail(commentSub.getEmail())){
            //根据邮箱获取qq头像，这里的工具类也可以获取昵称的
            String qqAvatarURL = QQInfoUtils.getQQAvatarURL(commentSub.getEmail());
            commentSub.setAvatar(qqAvatarURL);
            if(!commentSub.getContent().isEmpty()&&!commentSub.getNick_name().isEmpty()){
                int saveComment = commentMapper.saveComment(commentSub);
                if(saveComment>0){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }else{
            //如果邮箱格式不正确，这里直接给他返回false   如果后期，在服务器上有头像图片，这里可以随机生成一张头像url
            return false;
        }

    }

    @Override
    public boolean deleteComment(Integer comment_id) {
        List<Comment> commentByCommentId = commentMapper.getCommentByCommentId(comment_id);
        if(!commentByCommentId.isEmpty()) {
            for (Comment comment : commentByCommentId) {
                commentMapper.deleteComment(comment.getComment_id());
            }
        }
        int deleteComment = commentMapper.deleteComment(comment_id);
        if(deleteComment>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean checkComment(Integer comment_id, Integer is_check) {
        int checkComment = commentMapper.checkComment(comment_id, is_check);
        if(checkComment>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String getEmailByCommentId(Integer comment_id) {
        return commentMapper.getEmailByCommentId(comment_id);
    }

    @Override
    public Comment getCommentByPid(Integer comment_id) {
        return commentMapper.getCommentByPid(comment_id);
    }
}
