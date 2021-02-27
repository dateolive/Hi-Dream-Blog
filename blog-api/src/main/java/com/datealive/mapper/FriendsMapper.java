package com.datealive.mapper;

import com.datealive.pojo.Friends;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: FriendsMapper
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/1  16:59
 */
@Mapper
@Repository
public interface FriendsMapper {
    /**
     * 获取已经审核通过的友链
     * @return
     */
    public List<Friends> getAllFriends();

    /**
     * 管理员获得所有友链
     * @return
     */
    public List<Friends> getAllFriendsWithStatus();
    /**
     * 获取友链数量
     * @return
     */
    public int getFriendsCnt();

    /**
     * 通过名字查找友链
     * @param link_name
     * @return
     */
    public List<Friends> queryFriendsByName(@Param("link_name") String link_name);

    /**
     * 添加友链
     * @param friends
     * @return
     */
    public int saveFriend(Friends friends);

    /**
     * 更新友链
     * @param friends
     * @return
     */
    public int updateFriend(Friends friends);

    /**
     * 更新友链状态
     * @param link_id
     * @return
     */
    public int updateStatus(@Param("link_id") Integer link_id,@Param("status") Integer status);

    /**
     * 根据id删除友链
     * @param link_id
     * @return
     */
    public int deleteFriend(@Param("link_id")Integer link_id);

}
