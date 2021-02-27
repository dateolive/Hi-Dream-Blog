package com.datealive.mapper;


import com.datealive.pojo.Comment;
import com.datealive.service.dto.CommentQuery;
import com.datealive.service.vo.CommentSub;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: CommentMapper
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/9  19:49
 */
@Mapper
@Repository
public interface CommentMapper {
    /**
     * 查询父级评论
     * @param commentQuery
     * @return
     */
    public List<Comment> getParentComment(CommentQuery commentQuery);

    /**
     * 查询子级评论
     * @param comment_id
     * @return
     */
    public List<Comment> getChildComment(@Param("comment_id") Integer comment_id);

    /**
     * 查询父级评论的数量
     * @param commentQuery
     * @return
     */
    public int getParentCommentCnt(CommentQuery commentQuery);

    /**
     * 后台获取所有评论
     * @return
     */
    public List<Comment> getAllComment();

    /**
     * 根据评论者名称搜索评论
     * @param nick_name
     * @return
     */
    public List<Comment> getCommentByNickName(@Param("nick_name")String nick_name);

    /**
     * 根据commentid查询子级评论，这里的没有is_check的判断
     * @param comment_id
     * @return
     */
    public List<Comment> getCommentByCommentId(@Param("comment_id") Integer comment_id);

    /**
     * 所有评论的数量
     * @return
     */
    public int getAllCommentCnt();

    /**
     * 提交评论
     * @param commentSub
     * @return
     */
    public int saveComment(CommentSub commentSub);

    /**
     * 删除评论，如果是父级，还要删除子级评论
     * @param comment_id
     * @return
     */
    public int deleteComment(@Param("comment_id")Integer comment_id);

    /**
     * 修改评论的状态
     * @param comment_id
     * @param is_check
     * @return
     */
    public int checkComment(@Param("comment_id")Integer comment_id,@Param("is_check")Integer is_check);

    /**
     * 根据commentId找到对应的评论邮箱
     * @param comment_id
     * @return
     */
    public String getEmailByCommentId(@Param("comment_id")Integer comment_id);

    /**
     * 根据pid查找对应的父级评论
     * @param comment_id
     * @return
     */
    public Comment getCommentByPid(@Param("comment_id")Integer comment_id);
}
