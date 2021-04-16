package com.datealive.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * @ClassName: RegisterDto
 * @Description: TODO
 * @author: datealive
 * @date: 2021/3/5  14:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto implements Serializable {

    private String username;
    private String password;
    private String nick_name;
    private String email;
    private String roles;
}
