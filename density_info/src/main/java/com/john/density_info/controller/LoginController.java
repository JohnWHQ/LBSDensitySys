package com.john.density_info.controller;


import com.john.density_info.dao.LoginDao;
import com.john.density_info.mode.HostHolder;
import com.john.density_info.mode.Result;
import com.john.density_info.mode.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;



@RestController
public class LoginController {

    @Autowired
    LoginDao loginDao;

    @Autowired
    HostHolder hostHolder;


    /**
     * 登录模块
     * @param name
     * @param pwd
     * @return result
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces="application/json")
    public Result login(@RequestParam("name") String name, @RequestParam("password") String pwd) throws Exception{
        Result res = new Result();
        UserInfo userInfo = loginDao.selectByName(name);

        if (userInfo != null && userInfo.getPassword().equals(pwd)) {

            // set global user info
            hostHolder.setUser_infos(userInfo);

            // produce result
            res.setCode(0);
            res.setMsg("success");
            res.setSession_id(hostHolder.getUser_token());

        }else {
            res.setCode(1);
            res.setMsg("name or pwd wrong");
        }
        return res;
    }

    // 测试登录session预留接口
    @RequestMapping(value = "/login1", method = RequestMethod.GET)
    public String login1() {
        return "session_id=" + hostHolder.getUser_token();
    }


    /**
     * 注册模块
     * @param name
     * @param pwd1
     * @param pwd2
     * @param email
     * @param info
     * @return result
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@RequestParam("name") String name, @RequestParam("password1") String pwd1,
                           @RequestParam("password2") String pwd2,
                           @RequestParam("email") String email,
                           @RequestParam("info") String info){

        Result res = new Result();
        if (!pwd1.equals(pwd2)) {
            res.setCode(1);
            res.setMsg("两次密码不一致");
            return res;
        }

        // to-do

        // check name email function

        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setPassword(pwd1);
        userInfo.setEmail(email);
        userInfo.setInfo(info);

        loginDao.addEntity(userInfo);

        res.setCode(0);
        res.setMsg("success");
        res.setContent(userInfo);

        return res;
    }


}
