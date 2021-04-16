package com.datealive.service;

import com.datealive.common.Result;
import com.datealive.pojo.User;
import com.datealive.service.dto.RegisterDto;

/**
 * @ClassName: UserService
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  12:50
 */
public interface UserService {

    /**
     * 业务层用户接口
     * @param username
     * @return
     */
    User getUserByName(String username);

    /**
     * 根据id获取用户
     * @param userId
     * @return
     */
    User getUserById(Integer userId);

    /**
     * 修改密码
     * @param userName
     * @param newPassword
     * @return
     */
    Result changePassword(String userName, String newPassword);

    /**
     * 注册用户
     * @param registerDto
     * @return
     */
    boolean RegisterUser(RegisterDto registerDto);
}
