package com.john.density_info.api;

import com.john.density_info.dao.BeanMonitorTaskDao;
import com.john.density_info.mode.BeanMonitorDismessage;
import com.john.density_info.mode.BeanMonitorTask;
import com.john.density_info.mode.HostHolder;
import com.john.density_info.mode.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class MonitorTask {

    @Autowired
    BeanMonitorTaskDao taskDao;

    @Autowired
    HostHolder hostHolder;

    // 查询所有汇总信息
    @RequestMapping(value = "/api/task/selectAll", method = RequestMethod.GET)
    public ArrayList<BeanMonitorTask> selectAll(){
        return taskDao.selectAll();
    }

    // task
    @RequestMapping(value = "/api/task/add", method = RequestMethod.POST)
    public BeanMonitorTask mocadd(@RequestParam("taskname") String taskname, @RequestParam("uid") String uid){

        UserInfo userInfo = hostHolder.getInfo(uid);

        BeanMonitorTask task = new BeanMonitorTask();

        task.setUid(userInfo.getId());
        task.setPid(1);
        task.setStatus(1);
        task.setTaskname(taskname);
        task.setLat(0);
        task.setLng(0);
        task.setOtherinfo("暂无");


        task.setCreate_time(System.currentTimeMillis());

        taskDao.addTask(task);

        return task;
    }
    // 查询用户消息 by token -> uid
    @RequestMapping(value = "/api/task/taskByUID", method = RequestMethod.POST)
    public ArrayList<BeanMonitorTask> selectByUid(@RequestParam("token") String token){

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
        return taskDao.selectByUId(userInfo.getId());
    }
}
