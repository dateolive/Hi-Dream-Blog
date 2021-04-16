package com.datealive.service.impl;

import com.datealive.common.Result;
import com.datealive.common.ResultCode;
import com.datealive.mapper.UserMapper;
import com.datealive.pojo.User;
import com.datealive.service.UserService;
import com.datealive.service.dto.RegisterDto;
import com.datealive.utils.QQInfoUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  12:51
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserByName(String username) {
        User user=userMapper.getUserByName(username);
        if(user==null){
            return null;
        }
        return user;
    }

    @Override
    public User getUserById(Integer userId) {
        User user=userMapper.getUserById(userId);
        if(user==null){
            return null;
        }
        return user;
    }


    @Override
    public Result changePassword(String userName, String newPassword) {
        String hashMd5AfterPassword=new Md5Hash(newPassword,userName,1024).toHex();
        userMapper.changePassword(userName,hashMd5AfterPassword);
        return Result.success(ResultCode.Success,"修改成功!");
    }

    @Override
    public boolean RegisterUser(RegisterDto registerDto) {
        //注册用户业务层,需要对用户名，邮箱进行验证，对密码进行哈希盐值加密后存储
        User user=new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        user.setEmail(registerDto.getEmail());
        user.setNick_name(registerDto.getNick_name());
        user.setRoles(registerDto.getRoles());
        //用户头像通过邮箱获取qq邮箱
        user.setAvatar(QQInfoUtils.getQQAvatarURL(registerDto.getEmail()));
        //简介默认为空
        user.setIntroduce("test");
        int registerUser = userMapper.registerUser(user);
        if(registerUser>0){
            return true;
        }else {
            return false;
        }
    }
}
