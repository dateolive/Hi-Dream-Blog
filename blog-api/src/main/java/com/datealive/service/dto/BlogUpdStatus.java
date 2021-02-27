package com.datealive.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: BlogUpdStatus
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/25  12:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogUpdStatus implements Serializable {

    private Integer blog_id;
    private Integer is_top;
}
