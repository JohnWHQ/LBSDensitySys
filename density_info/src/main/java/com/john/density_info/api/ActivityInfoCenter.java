package com.john.density_info.api;

import com.john.density_info.dao.ActivtiyInfoDao;
import com.john.density_info.mode.BeanActivityInfo;
import com.john.density_info.mode.HostHolder;
import com.john.density_info.mode.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ActivityInfoCenter {

    @Autowired
    ActivtiyInfoDao activtiyInfoDao;

    @Autowired
    HostHolder hostHolder;

    // 查询所有汇总信息
    @RequestMapping(value = "/api/activity/selectAll", method = RequestMethod.GET)
    public ArrayList<BeanActivityInfo> selectAll(){
        return activtiyInfoDao.selectAll();
    }

    // 插入activity数据
    @RequestMapping(value = "/api/activity/mocadd", method = RequestMethod.POST)
    public BeanActivityInfo mocadd(@RequestParam("content") String content, @RequestParam("uid") String uid){

        UserInfo userInfo = hostHolder.getInfo(uid);

        System.out.println(userInfo);
        BeanActivityInfo activityInfo = new BeanActivityInfo();

        activityInfo.setUid(userInfo.getId());
        activityInfo.setUname(userInfo.getNickname());
        activityInfo.setContent(content);

        activityInfo.setAc_id(0);
        activityInfo.setRq_id(0);
        activityInfo.setTr_id(0);
        activityInfo.setCreate_time(System.currentTimeMillis());

        activtiyInfoDao.addActivtiy(activityInfo);

        return activityInfo;
    }
}
