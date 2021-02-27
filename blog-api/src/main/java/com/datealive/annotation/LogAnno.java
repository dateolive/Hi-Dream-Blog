package com.datealive.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Classname LogAnno
 * @Description 日志注解
 * @Date 2021/2/17 15:06
 * @author 冰羽
 */
@Target(ElementType.METHOD) // 方法注解
@Retention(RetentionPolicy.RUNTIME) // 运行时可见
public @interface LogAnno {
    String operateType();// 记录日志的操作类型
    String description();//记录日志操作描述
    int classification();//记录日志类别

}