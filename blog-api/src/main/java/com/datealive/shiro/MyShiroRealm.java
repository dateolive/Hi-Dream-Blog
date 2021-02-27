package com.datealive.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.datealive.pojo.User;
import com.datealive.service.UserService;
import com.datealive.utils.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MyShiroRealm
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  16:16
 */
@Component
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户登录信息
        MyProfile user = (MyProfile) principalCollection.getPrimaryPrincipal();
       // System.out.println("user====>"+user);
        //这里根据用户信息再查一遍权限表  由于这里没有权限表  所以直接查询用户表  获取它的role
        User realUser = userService.getUserById(user.getUser_id());
        //添加角色和权限
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        //System.out.println("=========>"+realUser.getRoles());
        authorizationInfo.addStringPermission(realUser.getRoles());
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        JwtToken jwt = (JwtToken) token;
        String userId = jwtUtils.getClaimByToken((String) jwt.getPrincipal()).getSubject();
        User user = userService.getUserById(Integer.valueOf(userId));
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        MyProfile profile=new MyProfile();
        BeanUtil.copyProperties(user, profile);

        return new SimpleAuthenticationInfo(profile, jwt.getCredentials(), getName());
    }
}
