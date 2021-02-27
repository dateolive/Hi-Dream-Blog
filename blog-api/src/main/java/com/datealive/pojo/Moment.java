package com.datealive.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * @ClassName: Moment
 * @Description: 博客动态，说说
 * @author: datealive
 * @date: 2021/2/17  18:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Moment {

    private Integer id;
    @NotBlank(message = "内容不能为空")
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;
    private Integer likes;
    private Integer published;
}
