package com.john.density_info.spatial;

/**
 * 最小外包矩形生成类
 */
public class SpatialMBR {

    public static final double invalid = Double.MAX_VALUE;

    public static LBSMBR getMBR(LBSPoint[] pArr){
        if (pArr == null || pArr.length < 3) {
            return null;
        }
        LBSMBR mbr = null;

        double Xmax = invalid;
        double Xmin = invalid;
        double Ymax = invalid;
        double Ymin = invalid;

        double tmp = invalid;

        for (int i = 0; i < pArr.length; i++) {
            if (pArr[i].x < tmp) {
                tmp = pArr[i].x;
            }
        }
        Xmin = tmp;
        tmp = invalid;

        for (int i = 0; i < pArr.length; i++) {
            if (pArr[i].y < tmp) {
                tmp = pArr[i].y;
            }
        }
        Ymin = tmp;
        tmp = Double.MIN_VALUE;

        for (int i = 0; i < pArr.length; i++) {
            if (pArr[i].x > tmp) {
                tmp = pArr[i].x;
            }
        }
        Xmax = tmp;
        tmp = Double.MIN_VALUE;

        for (int i = 0; i < pArr.length; i++) {
            if (pArr[i].y > tmp) {
                tmp = pArr[i].y;
            }
        }
        Ymax = tmp;


        if (Xmax == invalid || Ymax == invalid || Xmin == invalid || Ymin == invalid) {
            return mbr;
        }
        mbr = new LBSMBR();

        mbr.setTopLeftX(Xmin);
        mbr.setTopLeftY(Ymax);

        mbr.setTopRightX(Xmax);
        mbr.setTopRightY(Ymax);

        mbr.setBottomLeftX(Xmin);
        mbr.setBottomLeftY(Ymin);

        mbr.setBottomRightX(Xmax);
        mbr.setBottomRightY(Ymin);

        return mbr;
    }
}
