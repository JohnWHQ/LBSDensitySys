package com.john.density_info.mode;

import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.WeakHashMap;

/**
 * 全局对象共同持有模板
 */
@Component
public class HostHolder {

    // 模块全局实体
    private static ThreadLocal<MainEntity> entitys = new ThreadLocal<MainEntity>();

    // 用户登录态全局实体
    private static ThreadLocal<String> user_tokens = new ThreadLocal<>();

    // 用户信息全局实体
    private static ThreadLocal<UserInfo> user_infos = new ThreadLocal<>();

    private static WeakHashMap<String, UserInfo> map = new WeakHashMap<>();



    public MainEntity getMainEntity(){
        return entitys.get();
    }
    public void setMainEntity(MainEntity entity){
        entitys.set(entity);
    }
    public void removeEntity(){
        entitys.remove();
    }


    public static String getUser_token() {
        return user_tokens.get();
    }
    public static void setUser_token(String token) {
        user_tokens.set(token);
    }
    public static void removeUser_token(){
        user_tokens.remove();
    }


    public static UserInfo getUserInfo(){
        return user_infos.get();
    }
    public static void setUser_infos(UserInfo user_info){
        user_infos.set(user_info);
    }
    public static void remove(){
        user_infos.remove();
    }


    public static UserInfo getInfo(String sessionid){
        return map.get(sessionid);
    }
    public static void set_info(String sessionid,UserInfo user_info){
        map.put(sessionid,user_info);
    }


}
