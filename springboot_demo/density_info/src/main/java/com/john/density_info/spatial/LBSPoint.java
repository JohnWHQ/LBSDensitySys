package com.john.density_info.spatial;

public class LBSPoint {
    double x;
    double y;
    double lat;
    double lng;

    int LineId;

    public LBSPoint(){

    }
    public LBSPoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getLineId() {
        return LineId;
    }

    public void setLineId(int lineId) {
        LineId = lineId;
    }
}
