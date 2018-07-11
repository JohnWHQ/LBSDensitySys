package com.john.density_info.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class mapUtilsOpt {


    @RequestMapping(value = "/api/maputils/savePolygon",method = RequestMethod.POST)
    public String savePolygon(@RequestParam("token") String token, @RequestParam("sArea") double s,
                              @RequestParam("lngArr") String lngArr, @RequestParam("latArr") String latArr){

//        String[] lngSArr = lngArr.split(",");
//        String[] latSArr = latArr.split(",");

        System.out.println(s);
        System.out.println(lngArr);
        System.out.println(latArr);

        return "yes";
    }
}