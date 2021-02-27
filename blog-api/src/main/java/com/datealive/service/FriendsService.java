package com.datealive.service;

import com.datealive.common.PageResult;
import com.datealive.pojo.Friends;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: FriendsService
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/1  17:11
 */
public interface FriendsService {


    /**
     * 获取所有友链
     * @return
     */
    public List<Friends> getAllFriends();

    /**
     * 管理员分页获取友链
     * @param pageNum
     * @return
     */
    public PageResult<Friends> getAllFriendsByPage(Integer pageNum);

    /**
     * 通过名字查找友链
     * @param link_name
     * @return
     */
    public List<Friends> queryFriendsByName(String link_name);

    /**
     * 添加友链
     * @param friends
     * @return
     */
    public boolean saveFriend(Friends friends);

    /**
     * 更新友链
     * @param friends
     * @return
     */
    public boolean updateFriend(Friends friends);

    /**
     * 更新友链状态
     * @param link_id
     * @param status
     * @return
     */
    public boolean updateStatus(Integer link_id, Integer status);

    /**
     * 根据id删除友链
     * @param link_id
     * @return
     */
    public boolean deleteFriend(Integer link_id);
}
