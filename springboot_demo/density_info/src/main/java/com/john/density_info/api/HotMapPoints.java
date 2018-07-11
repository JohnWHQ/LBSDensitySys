package com.john.density_info.api;

import com.john.density_info.dao.DensityInfoStaticDao;
import com.john.density_info.dao.HPMapPointsDao;
import com.john.density_info.mode.DensityInfoStatic;
import com.john.density_info.mode.HPMapPoints;
import com.john.density_info.mode.HostHolder;
import com.john.density_info.mode.UserInfo;
import com.john.density_info.spatial.LBSMBR;
import com.john.density_info.spatial.LBSPoint;
import com.john.density_info.spatial.SpatialMBR;
import com.john.density_info.utils.JohnRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;

/**
 * 热力图Point信息服务API接口类
 *
 */
@RestController
public class HotMapPoints {

    @Autowired
    HPMapPointsDao pointsDao;

    @Autowired
    DensityInfoStaticDao polygonDao;

    @Autowired
    HostHolder hostHolder;

    private static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);

    // 获取全部的热力图points数据
    @RequestMapping(value = "/api/monitorpoints",method = RequestMethod.GET)
    public ArrayList<HPMapPoints> getMoniorPoints(){

        ArrayList<HPMapPoints> al;
        al = pointsDao.selectAll();

        return al;
    }

    // 获取 uid 全部的热力图points数据
    @RequestMapping(value = "/api/monitorpointsByUid",method = RequestMethod.GET)
    public ArrayList<HPMapPoints> getMoniorPointsByUid(@RequestParam("token") String token){

        int uid = hostHolder.getInfo(token).getId();

        logger.info("API模块日志记录-monitorpointsByUid接口={}","用户SessionID = " + token + " , uid = " + uid);

        ArrayList<HPMapPoints> al;

        al = pointsDao.selectByUid(uid);

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

    // 模拟拟合插入poi数据
    @RequestMapping(value = "/api/MocPointInsert1",method = RequestMethod.GET)
    public ArrayList<HPMapPoints> test1(@RequestParam("token") String token){



        HPMapPoints pointsInfo = null;


        int uid = hostHolder.getInfo(token).getId();
        ArrayList<DensityInfoStatic> al = polygonDao.selectByUId(uid);
        ArrayList<HPMapPoints> res = new ArrayList<HPMapPoints>();

        for (DensityInfoStatic polygon : al) {

            pointsInfo = new HPMapPoints();

            double lng = Double.valueOf(polygon.getLng_s().split(",")[0]);
            double lat = Double.valueOf(polygon.getLat_s().split(",")[0]);

            pointsInfo.setLng(lng + JohnRandom.getDetarRandom4HMP());
            pointsInfo.setLat(lat + JohnRandom.getDetarRandom4HMP());

            pointsInfo.setCount(JohnRandom.getZero2N(2000));

            pointsInfo.setUid(uid);
            pointsInfo.setFid(polygon.getId());

            pointsInfo.setCreat_time(System.currentTimeMillis());
            pointsInfo.setUpdate_time(System.currentTimeMillis());

            pointsDao.addPoints(pointsInfo);

            res.add(pointsInfo);
        }


        return res;
    }

    // 模拟拟合插入poi数据pro
    @RequestMapping(value = "/api/MocPointInsert2",method = RequestMethod.GET)
    public ArrayList<HPMapPoints> test2(@RequestParam("token") String token){


        HPMapPoints pointsInfo = null;


        int uid = hostHolder.getInfo(token).getId();
        ArrayList<DensityInfoStatic> al = polygonDao.selectByUId(uid);

        ArrayList<HPMapPoints> res = new ArrayList<HPMapPoints>();

        for (DensityInfoStatic polygon : al) {

            String[] sarr1 = polygon.getLng_s().split(",");
            String[] sarr2 = polygon.getLat_s().split(",");

            LBSPoint[] lbsPointsArr = new LBSPoint[sarr1.length];

            for (int i = 0; i < lbsPointsArr.length; i++){
                lbsPointsArr[i] = new LBSPoint(Double.valueOf(sarr1[i]),Double.valueOf(sarr2[i]));
            }

            LBSMBR mbr = SpatialMBR.getMBR(lbsPointsArr);

            double lng = (mbr.getTopLeftX() + mbr.getTopRightX())/2.0;
            double lat = (mbr.getTopLeftY() + mbr.getBottomLeftY())/2.0;

            pointsInfo = new HPMapPoints();

            pointsInfo.setLng(lng);
            pointsInfo.setLat(lat);

            pointsInfo.setCount(JohnRandom.getZero2N(2000));

            pointsInfo.setUid(uid);
            pointsInfo.setFid(polygon.getId());

            pointsInfo.setCreat_time(System.currentTimeMillis());
            pointsInfo.setUpdate_time(System.currentTimeMillis());

            pointsDao.addPoints(pointsInfo);

            res.add(pointsInfo);


        }


        return res;
    }
}
