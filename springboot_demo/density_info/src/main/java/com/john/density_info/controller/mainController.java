package com.john.density_info.controller;

import org.springframework.web.bind.annotation.*;

/**
 * the mode controller
 */
@RestController
public class mainController {

    // 测试服务启动模板
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String index(){
        return "hello world!!!";
    }

    // 请求 ? + & 方式模板
    @RequestMapping(value = "/value1" , method = RequestMethod.GET)
    public String controllerValueM1(@RequestParam("value1") Integer v1, @RequestParam("value2") String v2){
        return "front-edge sent val1 = " + v1 + " val2 = " + v2;
    }
    // 请求 /{}/{} 方式模板
    @RequestMapping(value = "/value2/{v1}/{v2}", method = RequestMethod.GET)
    public String controllerValueM2(@PathVariable("v1") Integer v1, @PathVariable("v2") String v2){
        return "front-edge sent val1 = " + v1 + " val2 = " + v2;
    }

    // 测试异常处理模板
    @RequestMapping(value = "/tmp", method = RequestMethod.GET)
    public String tmp(){

        // int x = 1/0;
        return "hello world!";
    }

    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String checkOut(){
        return "check out status";
    }



}
