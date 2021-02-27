package com.datealive.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @ClassName: User
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  12:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer user_id;

    @NotBlank(message = "昵称不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")

    private String password;
    private String nick_name;
    private String avatar;
    private String introduce;
    private String roles;
    private String email;
}
