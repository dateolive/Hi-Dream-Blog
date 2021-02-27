package com.datealive.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: CateGory
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/1  10:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CateGory implements Serializable {

    private Integer category_id;
    private String category_name;
}
