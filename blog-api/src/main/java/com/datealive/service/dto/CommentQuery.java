package com.datealive.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: CommentQuery
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/10  11:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentQuery implements Serializable {

    private int blog_id;
    private int currentPage;
    private int page;
}
