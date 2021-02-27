package com.datealive.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.datealive.annotation.LogAnno;
import com.datealive.common.Result;
import com.datealive.common.ResultCode;
import com.datealive.pojo.User;
import com.datealive.service.UserService;
import com.datealive.service.dto.LoginDto;
import com.datealive.service.dto.ModifyPwd;
import com.datealive.utils.JwtUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @ClassName: LoginController
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  19:10
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

   // @LogAnno(operateType="POST",description = "登录后台",classification = 2)
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {

     //   System.out.println("==============="+loginDto.getUsername());

        User user=userService.getUserByName(loginDto.getUsername());
        Assert.notNull(user, "用户不存在");
        String hashMd5AfterPassword=new Md5Hash(loginDto.getPassword(),loginDto.getUsername(),1024).toHex();
     //   System.out.println("加密后的=======>"+hashMd5AfterPassword);
        if(!user.getPassword().equals(hashMd5AfterPassword)){
            log.error("账号"+loginDto.getUsername()+"账号"+loginDto.getPassword()+"登录失败");
            return Result.error("密码或用户名不正确");
        }

        String jwt = jwtUtils.generateToken(user.getUser_id());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");
        log.info("用户"+loginDto.getUsername()+"登录成功");
        return Result.success("登录成功",MapUtil.builder()
                .put("id",user.getUser_id())
                .put("username",user.getUsername())
                .put("nick_name",user.getNick_name())
                .put("avatar",user.getAvatar())
                .put("introduce",user.getIntroduce())
                .put("roles",user.getRoles())
                .put("email",user.getEmail())
                .map()
        );
    }

    @LogAnno(operateType="GET",description = "退出登录",classification = 2)
    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        log.info("用户登出");
        return Result.success("登出成功");
    }

    /**
     * 修改密码
     * @param modifyPwd
     * @return
     */
    @RequiresPermissions(value ={"admin"},logical= Logical.OR)
    @LogAnno(operateType="POST",description = "后台用户修改密码",classification = 2)
    @PostMapping("/changePassword")
    @ApiOperation(value = "修改密码")
    public Result changePassword(@Validated @RequestBody ModifyPwd modifyPwd){
        String userName = modifyPwd.getUserName();
        String oldPassword = modifyPwd.getOldPassword();
        String newPassword = modifyPwd.getNewPassword();
        // System.out.println("===========>"+userName);
        log.info(new Date()+":进入修改密码接口");
        User user = userService.getUserByName(userName);
        if (user == null){
            return Result.error(ResultCode.Not_Found,"用户不存在！");
        }
        String hashMd5AfterPassword=new Md5Hash(oldPassword,userName,1024).toHex();
        //System.out.println("加密后的=======>"+hashMd5AfterPassword);
        if(!user.getPassword().equals(hashMd5AfterPassword)){
            return Result.error(ResultCode.Un_authorized,"旧密码错误！");
        }
        if(oldPassword.equals(newPassword)){
            return Result.error(ResultCode.Un_authorized,"新密码不能和旧密码一致！");
        }
        log.info("用户修改密码成功");
        return userService.changePassword(userName,newPassword);
    }

}
