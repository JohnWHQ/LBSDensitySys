package com.john.density_info.api;

import com.john.density_info.dao.DensityInfoStaticDao;
import com.john.density_info.mode.DensityInfoStatic;
import com.john.density_info.mode.HostHolder;
import com.john.density_info.mode.UserInfo;
import com.john.density_info.utils.GradeCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Random;

@RestController
public class DensityInfoPolygon {

    @Autowired
    DensityInfoStaticDao densityInfoStaticDao;

    @Autowired
    HostHolder hostHolder;

    // 查询所有polygon汇总信息
    @RequestMapping(value = "/api/polygon/selectAll", method = RequestMethod.GET)
    public ArrayList<DensityInfoStatic> selectAll(){
        return densityInfoStaticDao.selectAll();
    }

    // 根据UID查询polygon信息
    @RequestMapping(value = "/api/polygon/selectByUid", method = RequestMethod.POST)
    public ArrayList<DensityInfoStatic> selectByUid(@RequestParam("token") String token){
        UserInfo userInfo = hostHolder.getInfo(token);

        if (userInfo == null){
            try {
                throw new Exception("未登录");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return densityInfoStaticDao.selectByUId(userInfo.getId());
    }

    // 模拟插入polygon数据
    @RequestMapping(value = "/api/polygon/mocadd", method = RequestMethod.GET)
    public DensityInfoStatic mocadd(){
        DensityInfoStatic polygon = new DensityInfoStatic();
        Random random = new Random();
        polygon.setUid(666);
        polygon.setN(random.nextInt(1000));
        polygon.setS(1.00*random.nextInt(1000));
        polygon.setE(polygon.getN()/polygon.getS());
        polygon.setGrade(GradeCheck.getGradeFromE(polygon.getE()));
        polygon.setPname("所选定区域" + random.nextInt(100));
        polygon.setSafer_info("安全信息" + random.nextInt(100));
        polygon.setCreat_time(System.currentTimeMillis());
        polygon.setUpdate_time(System.currentTimeMillis());

        densityInfoStaticDao.addPolygon(polygon);
        return polygon;
    }

    // 插入polygon数据
    @RequestMapping(value = "/api/polygon/padd", method = RequestMethod.POST)
    public DensityInfoStatic padd(@RequestParam("token") String token, @RequestParam("sArea") double s,
                                  @RequestParam("lngArr") String lngArr, @RequestParam("latArr") String latArr,
                                  @RequestParam("safer_info") String info, @RequestParam("pname") String pname,
                                  @RequestParam("initN") int n){

        UserInfo userInfo = hostHolder.getInfo(token);

        DensityInfoStatic polygon = new DensityInfoStatic();

        polygon.setUid(userInfo.getId());

        polygon.setN(n);

        polygon.setS(s);
        System.out.println(s);

        polygon.setE(polygon.getS()/polygon.getN());

        polygon.setGrade(GradeCheck.getGradeFromE(polygon.getE()));

        polygon.setPname(pname);
        polygon.setSafer_info(info);

        polygon.setLng_s(lngArr);
        polygon.setLat_s(latArr);

        polygon.setCreat_time(System.currentTimeMillis());
        polygon.setUpdate_time(System.currentTimeMillis());

        densityInfoStaticDao.addPolygon(polygon);
        return polygon;
    }

    //  删除polygon数据
    @RequestMapping(value = "/api/polygon/deleteById", method = RequestMethod.POST)
    public int deleteById(@RequestParam("id") Integer id){
       return densityInfoStaticDao.deleteById(id);
    }

    // 更新polygon数据
    @RequestMapping(value = "/api/polygon/update", method = RequestMethod.POST)
    public DensityInfoStatic update(@RequestParam("id") Integer id, @RequestParam("pname") String pname,
                                  @RequestParam("uid") Integer uid, @RequestParam("n") Integer n,
                                  @RequestParam("s") Double s, @RequestParam("e") Double e,
                                  @RequestParam("grade") String grade, @RequestParam("safer_info") String safer_info,
                                  @RequestParam("lng_s") String lng_s, @RequestParam("lat_s") String lat_s,
                                  @RequestParam("creat_time") Long creat_time,
                                  @RequestParam("update_time") Long update_time){


        DensityInfoStatic polygon = new DensityInfoStatic();

        polygon.setId(id);
        polygon.setPname(pname);
        polygon.setUid(uid);
        polygon.setN(n);
        polygon.setS(s);
        polygon.setE(polygon.getS()/polygon.getN());
        polygon.setGrade(GradeCheck.getGradeFromE(polygon.getE()));
        polygon.setSafer_info(safer_info);

        polygon.setLng_s(lng_s);
        polygon.setLat_s(lat_s);

        polygon.setCreat_time(creat_time);
        polygon.setUpdate_time(System.currentTimeMillis());

        int res = densityInfoStaticDao.updateInfo(polygon);
        if (res != 1) {
            try {
                throw new Exception("更新多边形失败"+ polygon);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return polygon;
    }
}
