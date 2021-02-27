package com.datealive.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: Favorite
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/26  9:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favorite implements Serializable {

    private String title;
    private String content;
}
