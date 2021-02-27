package com.datealive.service.impl;

import com.datealive.common.Result;
import com.datealive.common.ResultCode;
import com.datealive.mapper.UserMapper;
import com.datealive.pojo.User;
import com.datealive.service.UserService;
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
}
