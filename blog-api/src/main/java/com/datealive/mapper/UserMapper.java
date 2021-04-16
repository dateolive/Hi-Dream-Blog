package com.datealive.mapper;

import com.datealive.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: UserMapper
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  12:41
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 通过名字查找用户
     * @param username
     * @return
     */
    User getUserByName(@Param("username") String username);

    /**
     * 根据id查找用户
     * @param userId
     * @return
     */
    User getUserById(@Param("userId")Integer userId);

    /**
     * 修改密码
     * @param userName
     * @param password
     * @return
     */
    int changePassword(@Param("userName") String userName,@Param("password") String password);

    /**
     * 注册用户
     * @param user
     * @return
     */
    int registerUser(User user);
}
