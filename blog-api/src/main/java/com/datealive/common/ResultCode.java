package com.datealive.common;

/**
 * @ClassName: ResultCode
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/1  9:20
 */
public class ResultCode {
    /**
     * 服务器成功返回用户请求的数据
     */
    public static final int Success=200;
    /**
     * 用户新建或修改数据成功。
     */
    public static final int CREATED_SuccessCode=201;
    /**
     * 用户删除数据成功。
     */
    public static final int Delete_SuccessCode=204;
    public static final int INVALID_REQUEST=400;
    /**
     * 表示用户没有权限
     */
    public static final int Un_authorized=401;
    /**
     * 表示用户得到授权（与401错误相对），但是访问是被禁止的
     */
    public static final int Forbidden=403;
    /**
     * 用户发出的请求针对的是不存在的记录，服务器没有进行操作
     */
    public static final int Not_Found=404;
    /**
     * 服务器发生错误，用户将无法判断发出的请求是否成功
     */
    public static final int Server_Error=500;
}
