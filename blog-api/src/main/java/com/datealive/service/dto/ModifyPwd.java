package com.datealive.service.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @ClassName: ModifyPwd
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/20  13:52
 */
@Data
public class ModifyPwd implements Serializable {

    @NotBlank(message = "昵称不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    private String oldPassword;

    @NotBlank(message = "密码不能为空")
    private String newPassword;

}
