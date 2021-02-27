package com.datealive.controller;

import com.datealive.annotation.LogAnno;
import com.datealive.common.PageResult;
import com.datealive.common.Result;
import com.datealive.common.ResultCode;
import com.datealive.pojo.Friends;
import com.datealive.service.FriendsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: FriendsController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/1  17:13
 */
@RestController
@Slf4j
public class FriendsController {

    @Autowired
    FriendsService friendsService;

    @GetMapping("/allFriends")
    public Result getAllFriends(){
        List<Friends> friends = friendsService.getAllFriends();
        return Result.success("请求成功",friends);
    }

    @GetMapping("/queryFriend/{name}")
    public Result getFriendsByName(@PathVariable("name") String name){
        List<Friends> friends = friendsService.queryFriendsByName(name);
        if(friends.isEmpty()){
            return Result.error(ResultCode.Not_Found,"无数据");
        }else{
            return Result.success("请求成功",friends);
        }

    }

    @PostMapping("/saveFriend")
    public Result saveFriend(@Validated @RequestBody Friends friends){
        friends.setStatus(0);
        boolean saveFriend = friendsService.saveFriend(friends);
        if(saveFriend){
            log.info("友链申请成功");
            return Result.success("申请成功");
        }else{
            log.error("友链申请失败");
            return Result.error("申请失败");
        }
    }
}
