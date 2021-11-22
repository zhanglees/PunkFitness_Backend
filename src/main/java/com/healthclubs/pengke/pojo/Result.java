package com.healthclubs.pengke.pojo;

/**
 * 响应类
 * @param <T>
 */
public class Result<T> {

    // 响应code
    private int code = 0;

    // 响应消息
    private String message = "";

    // 响应数据
    private T data = null;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
