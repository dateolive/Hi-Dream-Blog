package com.datealive.shiro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: MyProfile
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  17:56
 */
@Data
public class MyProfile implements Serializable {

    private Integer user_id;
    private String user_name;
    private String nick_name;
    private String avatar;
    private String introduce;

    public Integer getId(){
        return user_id;
    }

}
