package com.datealive.service.vo;

import com.datealive.pojo.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: BlogArticleInfo
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  21:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogArticleInfo implements Serializable {

    private Integer blog_id;
    private String blog_title;
    private String blog_cover_image;


    private String blog_summary;
    private Integer blog_status;
    private Integer blog_visits;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date create_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date update_time;
    private Integer is_top;

    private Integer blog_like;

    private Integer comment_cnt;

    private CateGory cateGory;


}
