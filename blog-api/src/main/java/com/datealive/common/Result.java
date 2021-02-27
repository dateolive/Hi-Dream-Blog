package com.datealive.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName: Result
 * @Description: TODO
 * @author: datealive
 * @date: 2021/1/31  13:15
 */
@NoArgsConstructor
@Getter
@Setter
public class Result implements Serializable {

    private Integer code;
    private String msg;
    private Object data;

    private Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data=null;
    }

    private Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(String msg,Object data){
        return new Result(ResultCode.Success,msg,data);
    }

    public static Result success(String msg){
        return new Result(ResultCode.Success,msg);
    }

    public static Result success(Integer code,String msg){
        return new Result(code,msg);
    }

    public static Result error(String msg) {
        return new Result(ResultCode.Server_Error, msg);
    }
    public static Result error(Integer code,String msg) {
        return new Result(code, msg);
    }

    public static Result error() {
        return new Result(ResultCode.Server_Error, "异常错误");
    }

    public static Result create(Integer code, String msg, Object data) {
        return new Result(code, msg, data);
    }

    public static Result create(Integer code, String msg) {
        return new Result(code, msg);
    }

}
