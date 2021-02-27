package com.datealive.service.impl;

import com.datealive.common.PageResult;
import com.datealive.common.Result;
import com.datealive.common.ResultCode;
import com.datealive.common.StaticFinalCode;
import com.datealive.mapper.FriendsMapper;
import com.datealive.pojo.Friends;
import com.datealive.service.FriendsService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: FriendsServiceImpl
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/1  17:12
 */
@Service
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    FriendsMapper friendsMapper;


    @Override
    public List<Friends> getAllFriends() {
        return friendsMapper.getAllFriends();
    }

    @Override
    public PageResult<Friends> getAllFriendsByPage(Integer pageNum) {
        int friendsCnt = friendsMapper.getFriendsCnt();
        int totalPage = (int) Math.ceil(friendsCnt* 1.0 / StaticFinalCode.pageSize);
        PageHelper.startPage(pageNum,StaticFinalCode.pageSize);
        List<Friends> friends = friendsMapper.getAllFriendsWithStatus();
        if(friends.isEmpty()){
            return new PageResult<Friends>(ResultCode.Not_Found,totalPage,pageNum,null);
        }else{
            return new PageResult<Friends>(ResultCode.Success,totalPage,pageNum,friends);
        }
    }

    @Override
    public List<Friends> queryFriendsByName(String link_name) {
        return friendsMapper.queryFriendsByName(link_name);
    }


    @Override
    public boolean saveFriend(Friends friends) {
        int saveFriend = friendsMapper.saveFriend(friends);
        if(saveFriend>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateFriend(Friends friends) {
        Integer link_id = friends.getLink_id();
        if(link_id!=null&&link_id!=0){
            friendsMapper.updateFriend(friends);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean updateStatus(Integer link_id, Integer status) {
        int updateStatus = friendsMapper.updateStatus(link_id, status);
        if(updateStatus>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteFriend(Integer link_id) {
        int isDelete = friendsMapper.deleteFriend(link_id);
        if(isDelete>0){
            return true;
        }else{
            return false;
        }
    }
}
