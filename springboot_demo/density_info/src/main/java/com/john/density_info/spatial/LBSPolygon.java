package com.john.density_info.spatial;

public class LBSPolygon {

    LBSLine[] boundArr;
    LBSMBR mbr;

    double areaS;

    public LBSPolygon(){

    }

    public boolean init(){
        if (boundArr == null || boundArr.length < 1) {
            return false;
        }


        // 递归遍历所以边界收集LBSpoint实体组合在一起
        int len = 0;

        for (int i = 0; i < boundArr.length; i++) {
            len += boundArr[i].pArr.length;
        }

        LBSPoint[] parr = new LBSPoint[len];

        int index = 0;

        for (int i = 0; i < boundArr.length; i++) {
            for (int j = 0; j < boundArr[i].pArr.length; j++) {
                parr[index++] = boundArr[i].pArr[j];
            }
        }

        // 计算多边形面积

        areaS = SpatialCalcuMode.getSFromPolygon(parr);

        // 生出最小外包矩形

        mbr = SpatialMBR.getMBR(parr);

        return true;
    }
}
