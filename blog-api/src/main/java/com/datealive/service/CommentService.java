package com.datealive.service;


import com.datealive.common.PageResult;
import com.datealive.pojo.Comment;
import com.datealive.service.dto.CommentQuery;
import com.datealive.service.vo.CommentSub;

import java.util.List;

/**
 * @ClassName: CommentService
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/9  20:42
 */
public interface CommentService {
    /**
     * 获取页面评论
     * @param commentQuery
     * @return
     */
    public PageResult<Comment> getAllCommentByQuery(CommentQuery commentQuery);

    /**
     * 后台获取评论
     * @param pageNum
     * @return
     */
    public PageResult<Comment> getAllComment(Integer pageNum);

    /**
     * 根据名称获取评论
     * @param nick_name
     * @return
     */
    public List<Comment> getCommentByNickName(String nick_name);

    /**
     * 获取所有评论数量
     * @return
     */
    public int getAllCommentCnt();

    /**
     * 提交评论
     * @param commentSub
     * @return
     */
    public boolean saveComment(CommentSub commentSub);

    /**
     * 如果是父级评论，还需要删除子级评论
     * @param comment_id
     * @return
     */
    public boolean deleteComment(Integer comment_id);

    /**
     * 修改评论状态
     * @param comment_id
     * @param is_check
     * @return
     */
    public boolean checkComment(Integer comment_id,Integer is_check);

    /**
     * 根据评论id查找comment对应的email
     * @param comment_id
     * @return
     */
    public String getEmailByCommentId(Integer comment_id);

    /**
     * 根据pid查找父级评论
     * @param comment_id
     * @return
     */
    public Comment getCommentByPid(Integer comment_id);
}
