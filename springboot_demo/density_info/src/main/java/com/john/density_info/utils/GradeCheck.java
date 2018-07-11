package com.john.density_info.utils;

/**
 * 密度等级判断工具类
 */
public class GradeCheck {
    private static double a = 5.6;
    private static double b = 3.7;
    private static double c = 2.2;
    private static double d = 1.4;
    private static double e = 0.75;
    public static char getGradeFromE(double r){

        if (r >= a) {
            return 'A';
        }else if ((b <= r) && (r < a)){
            return 'B';
        }else if ((c <= r) && (r < b)){
            return 'C';
        }else if ((d <= r) && (r < c)){
            return 'D';
        }else if ((e <= r) && (r < d)){
            return 'E';
        }
        return 'F';
    }
}
