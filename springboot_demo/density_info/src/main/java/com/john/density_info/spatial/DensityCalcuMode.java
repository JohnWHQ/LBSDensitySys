package com.john.density_info.spatial;

public class DensityCalcuMode {

    public static double getDensity(LBSPolygon polygon, LBSPoint[] arr){

        double res  = -1.0;
        double s = SpatialCalcuMode.getSFromPolygonPro(polygon);
        int n = 0;
        for (LBSPoint point : arr) {
            if (SpatialTopology.pointInPolygon(point, polygon)) {
                n++;
            }
        }
        res = n / s;
        return res;
    }
}
