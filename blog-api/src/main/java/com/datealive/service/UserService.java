package com.datealive.service;

import com.datealive.common.Result;
import com.datealive.pojo.User;

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

    User getUserById(Integer userId);

    Result changePassword(String userName, String newPassword);
}
