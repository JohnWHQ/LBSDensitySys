package com.john.density_info.controller;

import com.john.density_info.dao.MainMybatisDao;
import com.john.density_info.mode.MainEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * the MyBatis mode controller
 */
@RestController
public class MyBatisController {

    @Autowired
    MainMybatisDao mainMybatisDao;

    @RequestMapping(value = "/mybatis/insert", method = RequestMethod.GET)
    public int insert(@RequestParam("data") String data,
                         @RequestParam("info") String info){
        MainEntity entity = new MainEntity();
        entity.setData(data);
        entity.setInfo(info);
        return  mainMybatisDao.addEntity(entity);
    }

    @RequestMapping(value = "/mybatis/select/{id}", method = RequestMethod.GET)
    public MainEntity selectById(@PathVariable("id") Integer id){
        return mainMybatisDao.selectById(id);
    }



    @RequestMapping(value = "/mybatis/update", method = RequestMethod.POST)
    public int updatePwdById(@RequestParam("id") Integer id, @RequestParam("data") String data,
                             @RequestParam("info") String info){
        MainEntity mainEntity = selectById(id);
        mainEntity.setInfo(info);
        mainEntity.setData(data);
        return mainMybatisDao.updateInfo(mainEntity);
    }

    @RequestMapping(value = "/mybatis/delete/{id}", method = RequestMethod.DELETE)
    public  int deleteById(@PathVariable("id") Integer id) {
        return mainMybatisDao.deleteById(id);
    }
}
