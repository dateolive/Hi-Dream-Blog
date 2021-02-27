package com.datealive.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: BlogArticle
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  20:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogArticle {

    private Integer blog_id;
    @NotBlank(message = "文章不能为空")
    private String blog_title;
    private String blog_cover_image;
    private String blog_content;

    private String blog_summary;
    private Integer blog_status;
    private Integer blog_visits;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date create_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date update_time;
    private Integer is_top;
    private Integer blog_like;
    private Integer comment_cnt;

    /**
     * 分类
     */
    private List<CateGory> cateGory=new ArrayList<>();

    /**
     * 标签
     * */
    private List<Tag> tags=new ArrayList<>();


}
