package com.datealive.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: Tag
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/1  10:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag implements Serializable {
    private Integer tag_id;
    private String tag_name;
}
