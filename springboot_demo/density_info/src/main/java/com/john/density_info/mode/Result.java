package com.john.density_info.mode;

/**
 * 统一结果格式 springboot 返回json序列化
 * @param <T>
 */
public class Result<T> {
    // 结果代码
    private Integer code;
    // 结果信息
    private String msg;
    // 具体内容
    private T content;

    //
    private UserConfig config;

    // session_id
    private String session_id;

    public Result(){
    }

    public Result(Integer code, String msg, T content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }

    public UserConfig getConfig() {
        return config;
    }

    public void setConfig(UserConfig config) {
        this.config = config;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
