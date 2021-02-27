package com.datealive.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: PageResult
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  22:25
 */
@Data
public class PageResult<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 当前页数据
     */
    private List<T> data;

    public PageResult(Integer code,Integer totalPage, Integer currentPage, List<T> data) {
        this.code=code;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.data = data;
    }
}
