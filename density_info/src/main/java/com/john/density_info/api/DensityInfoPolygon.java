package com.john.density_info.api;

import com.john.density_info.dao.DensityInfoStaticDao;
import com.john.density_info.mode.DensityInfoStatic;
import com.john.density_info.utils.GradeCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Random;

@RestController
public class DensityInfoPolygon {

    @Autowired
    DensityInfoStaticDao densityInfoStaticDao;

    // 查询所有polygon汇总信息
    @RequestMapping(value = "/api/polygon/selectAll", method = RequestMethod.GET)
    public ArrayList<DensityInfoStatic> selectAll(){
        return densityInfoStaticDao.selectAll();
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

}
