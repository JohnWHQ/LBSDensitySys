package com.john.springbootmybatis.controller;

import com.john.springbootmybatis.dao.UserDao;
import com.john.springbootmybatis.domain.User;
import com.john.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String test(){
        return "hello world";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public String insert(@RequestParam("id") Integer id, @RequestParam("name") String name,
                         @RequestParam("password") String password, @RequestParam("otherInfo") String otherInfo){

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setOtherInfo(otherInfo);

        return  userService.addUser(user) + "";
    }

    @RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
    public User selectById(@PathVariable("id") Integer id){
        return userService.findById(id);
    }



    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int updatePwdById(@RequestParam("id") Integer id, @RequestParam("name") String name,
                             @RequestParam("password") String password){
        User user = selectById(id);
        user.setPassword(password);
        return userService.updatePwdById(user);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public  int deleteById(@PathVariable("id") Integer id){
        return userService.deleteById(id);
    }

    @RequestMapping(value = "/selectByLimit", method = RequestMethod.GET)
    public List<User> seletctByLimit(@RequestParam("id") Integer id, @RequestParam("limitN") Integer limitN){
        return userService.selectByLimit(id,limitN);
    }
}
