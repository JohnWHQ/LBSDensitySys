package com.john.density_info.spatial;

public class LBSLine {

    LBSPoint[] pArr;
    LBSPoint startP;
    LBSPoint endP;
    LBSMBR mbr;

    double LBS_length;
    double dp_accur;

    public LBSLine(){
    }

    public LBSPoint[] getpArr() {
        return pArr;
    }

    public void setpArr(LBSPoint[] pArr) {
        this.pArr = pArr;
    }

    public LBSPoint getStartP() {
        return startP;
    }

    public void setStartP(LBSPoint startP) {
        this.startP = startP;
    }

    public LBSPoint getEndP() {
        return endP;
    }

    public void setEndP(LBSPoint endP) {
        this.endP = endP;
    }

    public LBSMBR getMbr() {
        return mbr;
    }

    public void setMbr(LBSMBR mbr) {
        this.mbr = mbr;
    }

    public double getLBS_length() {
        return LBS_length;
    }

    public void setLBS_length(double LBS_length) {
        this.LBS_length = LBS_length;
    }

    public double getDp_accur() {
        return dp_accur;
    }

    public void setDp_accur(double dp_accur) {
        this.dp_accur = dp_accur;
    }
}
