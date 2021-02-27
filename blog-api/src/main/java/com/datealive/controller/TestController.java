package com.datealive.controller;

import com.datealive.annotation.LogAnno;
import com.datealive.common.PageResult;
import com.datealive.common.Result;
import com.datealive.common.ResultCode;
import com.datealive.pojo.Comment;
import com.datealive.pojo.User;
import com.datealive.service.CommentService;
import com.datealive.service.UserService;
import com.datealive.service.dto.CommentQuery;
import com.datealive.utils.QQInfoUtils;
import com.datealive.utils.UserAgentUtils;
import com.datealive.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.crazycake.shiro.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  12:59
 */
@RestController
public class TestController {

    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserAgentUtils agentUtils;


//    @RequiresAuthentication
//    @RequestMapping("/test")
//    public Result test(){
//
//        String testname="admin";
//        User user = userService.getUserByName(testname);
//        return Result.success("成功",user);
//    }
//
//    @PostMapping("/user")
//    public Result user(@Validated @RequestBody User user){
//        return Result.success("获取成功", user);
//    }
//
//    @GetMapping("/qqtest/{qq}")
//    public Result getQQavatar(@PathVariable("qq")String qq) throws UnsupportedEncodingException {
//        //对应的QQ获取头像类有自动切割qq邮箱的功能，所以这里不需要自己去切割了
//        String substring = qq.substring(0, qq.indexOf('@'));
//        System.out.println(substring);
//        String qqAvatarURL = QQInfoUtils.getQQAvatarURL(qq);
//        String qqNickname = QQInfoUtils.getQQNickname(qq);
//        Map<String,String> map = new HashMap<>();
//        map.put("qqAvatarURL",qqAvatarURL);
//        map.put("qqNickname",qqNickname);
//        return Result.success("请求成功",map);
//
//    }
//    @GetMapping("/ipadress/{ip}")
//    public Result ipadress(@PathVariable("ip") String ip){
//        String cityInfo = UserUtils.getCityInfo(ip);
//        return Result.success("请求成功",cityInfo);
//    }
//
//    @LogAnno(operateType="error",description = "异常",classification = 0)
//    @GetMapping("/testError")
//    public Result testError() throws Exception{
//        throw new Exception("我抛出来的");
//    }


}
