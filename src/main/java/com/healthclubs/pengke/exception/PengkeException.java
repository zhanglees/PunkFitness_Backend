package com.healthclubs.pengke.exception;

import com.healthclubs.pengke.utils.I18nService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class PengkeException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 2307173081850353807L;

    // 异常状态码
    private final Integer code;

    private Object data;

    @Autowired
    I18nService i18nService;

    public void setData(Object data) {
        this.data = data;
    }



    public PengkeException(Integer code) {

        this.code = code;
    }

    public PengkeException(Integer code, Object data) {

        this.data = data;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

}
