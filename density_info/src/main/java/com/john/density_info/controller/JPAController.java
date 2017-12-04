package com.john.density_info.controller;

import com.john.density_info.dao.MainJPACurdDao;
import com.john.density_info.mode.MainEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * the JPA mode controller
 */
@RestController
public class JPAController {

    @Autowired
    MainJPACurdDao mainJPACurdDAO;

    // insert
    @RequestMapping(value = "/JPA/insert", method = RequestMethod.POST)
    public MainEntity insert(@RequestParam("data") String data,
                             @RequestParam("info") String info){
        MainEntity mainEntity = new MainEntity();
        mainEntity.setData(data);
        mainEntity.setInfo(info);
        return mainJPACurdDAO.save(mainEntity);
    }

    // select by id
    @RequestMapping(value = "/JPA/select/{id}", method = RequestMethod.GET)
    public MainEntity findById(@PathVariable("id") Integer id){
        return mainJPACurdDAO.findOne(id);
    }

    // select all
    @RequestMapping(value = "/JPA/selectAll", method = RequestMethod.GET)
    public List<MainEntity> findAll(){
        return mainJPACurdDAO.findAll();
    }

    // select data
    @RequestMapping(value = "/JPA/select", method = RequestMethod.GET)
    public List<MainEntity> findByData(@RequestParam("data") String data){
        return mainJPACurdDAO.findByData(data);
    }

    // update
    @RequestMapping(value = "/JPA/update", method = RequestMethod.PUT)
    public MainEntity update(@RequestParam("id") Integer id, @RequestParam("data") String data,
                             @RequestParam("info") String info){

        MainEntity mainEntity = new MainEntity();
        mainEntity.setId(id);
        mainEntity.setData(data);
        mainEntity.setInfo(info);
        return mainJPACurdDAO.save(mainEntity);
    }

    // delete
    @RequestMapping(value = "/JPA/delete", method = RequestMethod.DELETE)
    public void update(@RequestParam("id") Integer id){
        mainJPACurdDAO.delete(id);
    }


}
