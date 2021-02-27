package com.datealive.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Tag
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  20:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    private Integer tag_id;
    @NotBlank(message = "标签名称不能为空")
    private String tag_name;
    /**
     * 该标签下的所有文章
     */
    private List<BlogArticle> blogs=new ArrayList<>();
}
