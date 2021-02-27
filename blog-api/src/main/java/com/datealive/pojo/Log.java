package com.datealive.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Classname Log
 * @Description 日志实体类
 * @Date 2021/2/17 15:06
 * @author 冰羽
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    private Integer id;

    private String operateor;

    private String operatetype;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date operatedate;

    private String operateresult;

    private String ip;

    private String ipSource;

    private String description;

    private String os;

    private String browser;

    private String error;

    private Integer classification;

    private Integer SpendTime;

    private String  url;
}