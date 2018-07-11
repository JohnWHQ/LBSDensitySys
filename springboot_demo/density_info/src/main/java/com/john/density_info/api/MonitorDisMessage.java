package com.john.density_info.api;

import com.john.density_info.dao.BeanMonitorDismessageDao;
import com.john.density_info.mode.BeanMonitorDismessage;
import com.john.density_info.mode.HostHolder;
import com.john.density_info.mode.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
public class MonitorDisMessage {

    @Autowired
    BeanMonitorDismessageDao beanMonitorDismessageDao;

    @Autowired
    HostHolder hostHolder;

    // 查询所有汇总信息
    @RequestMapping(value = "/api/dismessage/selectAll", method = RequestMethod.GET)
    public ArrayList<BeanMonitorDismessage> selectAll(){
        return beanMonitorDismessageDao.selectAll();
    }

    // 插入message数据
    @RequestMapping(value = "/api/dismessage/add", method = RequestMethod.POST)
    public BeanMonitorDismessage mocadd(@RequestParam("content") String content, @RequestParam("uid") String uid){

        UserInfo userInfo = hostHolder.getInfo(uid);

        BeanMonitorDismessage dismessage = new BeanMonitorDismessage();

        dismessage.setStar(1);
        dismessage.setUid(userInfo.getId());
        dismessage.setUname(userInfo.getNickname());
        dismessage.setContent(content);


        dismessage.setSend_time(System.currentTimeMillis());

        beanMonitorDismessageDao.addDismessage(dismessage);

        return dismessage;
    }

    // 查询用户消息 by token -> uid
    @RequestMapping(value = "/api/dismessage/messageByUID", method = RequestMethod.POST)
    public ArrayList<BeanMonitorDismessage> selectByUid(@RequestParam("token") String token){

        // to-do 目前采用软引用缓存用户登录信息 以后放到redis里缓存
        UserInfo userInfo = hostHolder.getInfo(token);
        // to-do 日后抽象成check()函数
        if (userInfo == null){
            try {
                throw new Exception("未登录");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return beanMonitorDismessageDao.selectByUId(userInfo.getId());
    }
}
