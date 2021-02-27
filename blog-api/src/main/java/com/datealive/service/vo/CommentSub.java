package com.datealive.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: Comment
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/9  16:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentSub implements Serializable {

    @NotBlank(message = "评论内容不能为空")
    private String content;
    private Integer blog_id;
    @NotBlank(message = "昵称不能为空")
    private String nick_name;
    private String avatar;
    private String email;
    private String reply_nick_name;
    private Integer pid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date create_time;
    private String site_url;
    private Integer page;
    private Integer is_check;
}
