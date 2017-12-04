package com.john.density_info.utils;

import com.john.density_info.mode.Result;

/**
 * 结果构造工组类
 */
public class ResultUtil {

    // 成功构造方法
    public static Result success(Object obj){
        Result res = new Result();
        res.setCode(1);
        res.setMsg("success");
        res.setContent(obj);
        return res;
    }

    public static Result success(){
        return success(null);
    }

    // 失败构造方法
    public static Result fail(Integer code, String msg){
        Result res = new Result();
        res.setCode(code);
        res.setMsg(msg);
        return res;
    }

    // 生成维持会话的token 即 session_id;
    public static String getToken(){
        return "";
    }
}