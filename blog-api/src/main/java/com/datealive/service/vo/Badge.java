package com.datealive.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;

/**
 * @ClassName: Badge
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/26  9:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Badge implements Serializable {
    private String title;
    private String url;
    private String subject;
    private String value;
    private String color;
}
