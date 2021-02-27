package com.datealive.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ClassName: JwtToken
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  16:50
 */
public class JwtToken implements AuthenticationToken {

    private String token;
    public JwtToken(String token){
        this.token=token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
