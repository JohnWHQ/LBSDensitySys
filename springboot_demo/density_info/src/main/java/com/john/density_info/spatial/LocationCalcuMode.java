package com.john.density_info.spatial;

public class LocationCalcuMode {
    public static LBSPoint getLocationUseIN(LBSPoint point0, LBS_IN lbs_in) {
        LBSPoint res = new LBSPoint();

        double axp = 0;
        double ayp = 0;

        // to-do get a

        res.x = point0.x + calculus(axp);
        res.y = point0.y + calculus(ayp);

        return res;
    }

    public static double calculus(double a){
        return 1.0;
    }
}
