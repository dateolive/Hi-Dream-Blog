package com.datealive.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: BlogArticleIdAndTitle
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/1  14:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogArticleIdAndTitle implements Serializable {

    private Integer blog_id;
    private String blog_title;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date create_time;

}
