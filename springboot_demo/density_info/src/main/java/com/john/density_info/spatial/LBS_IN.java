package com.john.density_info.spatial;

public class LBS_IN {
    // 加速度
    double ax;
    double ay;
    double az;

    // 陀螺仪
    double ix;
    double iy;
    double iz;

    public LBS_IN(){

    }

    public LBS_IN(double ax, double ay, double az, double ix, double iy, double iz) {
        this.ax = ax;
        this.ay = ay;
        this.az = az;
        this.ix = ix;
        this.iy = iy;
        this.iz = iz;
    }

    public double getAx() {
        return ax;
    }

    public void setAx(double ax) {
        this.ax = ax;
    }

    public double getAy() {
        return ay;
    }

    public void setAy(double ay) {
        this.ay = ay;
    }

    public double getAz() {
        return az;
    }

    public void setAz(double az) {
        this.az = az;
    }

    public double getIx() {
        return ix;
    }

    public void setIx(double ix) {
        this.ix = ix;
    }

    public double getIy() {
        return iy;
    }

    public void setIy(double iy) {
        this.iy = iy;
    }

    public double getIz() {
        return iz;
    }

    public void setIz(double iz) {
        this.iz = iz;
    }
}
