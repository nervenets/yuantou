package com.nervenets.general.entity;


import com.alibaba.fastjson.JSONObject;

/**
 * Created by shengshibotong.com.
 * User: Joe by 12-12-24 上午9:07
 */
public class ResponseResult extends JSONObject {

    public ResponseResult(int code) {
        this(code, null, null);
    }

    public ResponseResult(int code, String message) {
        this(code, null, message);
    }

    public ResponseResult(int code, Object data) {
        this(code, data, null);
    }

    public ResponseResult(int code, Object data, String message) {
        put("code", code);
        put("data", data);
        put("message", message);
    }

    public static ResponseResult failed() {
        return new ResponseResult(MessageCode.code_400);
    }

    public static ResponseResult failed(String message) {
        return new ResponseResult(MessageCode.code_400, message);
    }

    public static ResponseResult failed(int code, String message) {
        return new ResponseResult(code, message);
    }

    public static ResponseResult success() {
        return new ResponseResult(MessageCode.code_200);
    }

    public static ResponseResult success(String message) {
        return new ResponseResult(MessageCode.code_200, message);
    }

    public int getCode() {
        return getInteger("code");
    }

    public void setCode(int code) {
        put("code", code);
    }

    public Object getData() {
        return get("data");
    }

    public void setData(Object data) {
        put("data", data);
    }

    public String getMessage() {
        return getString("message");
    }

    public void setMessage(String message) {
        put("message", message);
    }

}
