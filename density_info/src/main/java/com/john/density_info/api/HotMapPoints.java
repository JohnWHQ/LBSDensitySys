package com.john.density_info.api;

import com.john.density_info.dao.HPMapPointsDao;
import com.john.density_info.mode.HPMapPoints;
import com.john.density_info.utils.JohnRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * 热力图Point信息服务API接口类
 *
 */
@RestController
public class HotMapPoints {

    @Autowired
    HPMapPointsDao pointsDao;

    // 获取全部的热力图points数据
    @RequestMapping(value = "/api/monitorpoints",method = RequestMethod.GET)
    public ArrayList<HPMapPoints> getMoniorPoints(){

        ArrayList<HPMapPoints> al;
        al = pointsDao.selectAll();

        return al;
    }

    // 模拟拟合插入poi数据
    @RequestMapping(value = "/api/testMocPointInsert",method = RequestMethod.GET)
    public HPMapPoints test(){

        HPMapPoints pointsInfo;
        pointsInfo = new HPMapPoints();



        pointsInfo.setLng(121.22063 + JohnRandom.getDetarRandom4HMP());
        pointsInfo.setLat(31.29188 + JohnRandom.getDetarRandom4HMP());
        pointsInfo.setCount(JohnRandom.getZero2N(2000));

        pointsInfo.setUid(666);
        pointsInfo.setFid(777);

        pointsInfo.setCreat_time(System.currentTimeMillis());
        pointsInfo.setUpdate_time(System.currentTimeMillis());

        pointsDao.addPoints(pointsInfo);


        return pointsInfo;
    }
}
