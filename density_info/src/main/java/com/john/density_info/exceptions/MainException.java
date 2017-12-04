package com.john.density_info.exceptions;

/**
 * 统一异常定义模板
 */
public class MainException extends RuntimeException{

    private Integer code;

    public MainException(Integer code, String msg){
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
