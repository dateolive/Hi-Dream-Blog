package com.datealive.controller.admin;

import com.datealive.annotation.LogAnno;
import com.datealive.common.PageResult;
import com.datealive.common.Result;
import com.datealive.pojo.Friends;
import com.datealive.service.FriendsService;
import com.datealive.service.dto.FriendsUpdStatus;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: FriendsAdminController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/1  19:40
 */
@RestController
@RequestMapping("/admin")
public class FriendsAdminController {

    @Autowired
    FriendsService friendsService;

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="POST",description = "添加友链",classification = 1)
    @RequiresAuthentication
    @PostMapping("/saveFriend")
    public Result saveFriend(@Validated @RequestBody Friends friends){
        System.out.println("=============>"+friends.getLink_name());
        boolean saveFriend = friendsService.saveFriend(friends);

        if(saveFriend){
            return Result.success("添加友链成功");
        }else{
            return Result.error("添加友链失败");
        }
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="PUT",description = "修改友链",classification = 1)
    @RequiresAuthentication
    @PutMapping("/updateFriend")
    public Result updateFriend(@Validated @RequestBody Friends friends){
        boolean isUpdate = friendsService.updateFriend(friends);
        if(isUpdate){
            return Result.success("修改友链成功");
        }else{
            return Result.error("修改友链失败");
        }
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="DELETE",description = "删除友链",classification = 1)
    @RequiresAuthentication
    @DeleteMapping("/deleteFriend")
    public Result deleteFriend(@RequestParam Integer link_id){
        boolean isDelete = friendsService.deleteFriend(link_id);
        if(isDelete){
            return Result.success("删除友链成功");
        }else{
            return Result.error("删除友链失败");
        }
    }

    @RequiresAuthentication
    @GetMapping("/friendsList")
    public PageResult<Friends> getAllFriendsByPage(@RequestParam(defaultValue = "1") Integer currentPage){
        PageResult<Friends> friends = friendsService.getAllFriendsByPage(currentPage);
        return friends;
    }

    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="PUT",description = "修改友链状态",classification = 1)
    @RequiresAuthentication
    @PutMapping("/updateFriStatus")
    public Result updateStatus(@RequestBody FriendsUpdStatus friendsUpdStatus){
        Integer link_id = friendsUpdStatus.getLink_id();
        Integer status = friendsUpdStatus.getStatus();
        boolean isUpdate = friendsService.updateStatus(link_id, status);
        if(isUpdate){
            return Result.success("修改状态成功");
        }else{
            return Result.error("修改状态失败");
        }
    }
}
