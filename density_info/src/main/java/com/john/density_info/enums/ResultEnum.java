package com.john.density_info.enums;

/**
 * 枚举结果记录单元
 */
public enum ResultEnum {

    SUCCESSED(0, "successed"),
    FAILED(1, "FAILED"),
    ERROR(-1, "ERROR");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
