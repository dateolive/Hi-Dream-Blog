package com.datealive.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: LogQuery
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/22  23:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogQuery implements Serializable {

    private int currentPage;
    private int classification;
    private int pageSize;
}
