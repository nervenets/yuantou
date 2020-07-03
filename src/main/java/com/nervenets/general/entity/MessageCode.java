package com.nervenets.general.entity;

/**
 * Created by shengshibotong.com.
 * User: Joe by 12-12-24 上午9:10
 */
public final class MessageCode {
    /*--------------------------成功代码--------------------------------*/
    public static final int code_200 = 200;     //请求成功

    /*--------------------------失败代码--------------------------------*/
    public static final int code_400 = 400;     //响应失败，请稍后重试！
    public static final int code_401 = 401;    //登录失效，重新登录获取TOKEN
    public static final int code_404 = 404;     //查询信息不存在

    public static final int code_500 = 500;     // 服务器错误

}
