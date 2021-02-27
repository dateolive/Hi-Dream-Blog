package com.datealive.service.dto;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: CommentUpdStatus
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/25  12:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdStatus implements Serializable {

    private Integer comment_id;
    private Integer is_check;
}
