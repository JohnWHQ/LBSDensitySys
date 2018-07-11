package com.john.density_info.utils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 老汉的轮子
 * 构造随机数，模拟POI
 */
public class JohnRandom {
    public static double getDetarRandom4HMP(){

        Random random = new Random();

        int mutix = 1000;
        double dotTail = 0.000001;

        double res = random.nextInt(mutix) * 0.000001;

        int pos = 0;
        pos = random.nextInt(10);
        res = ((pos&1)==0) ? res : -res;


        BigDecimal bg1 = new BigDecimal(res);
        res = bg1.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();

        System.out.println(res);

        return res;
    }

    public static int getZero2N(int n){
        Random random = new Random();
        return random.nextInt(n);
    }
}
