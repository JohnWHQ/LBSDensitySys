package com.john.density_info.spatial;

public class LBSMBR {

    double topLeftX;
    double topLeftY;

    double topRightX;
    double topRightY;

    double bottomLeftX;
    double bottomLeftY;

    double bottomRightX;
    double bottomRightY;

    public LBSMBR(){
    }

    public LBSMBR(double topLeftX, double topLeftY, double topRightX, double topRightY,
                  double bottomLeftX, double bottomLeftY,
                  double bottomRightX, double bottomRightY) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.topRightX = topRightX;
        this.topRightY = topRightY;
        this.bottomLeftX = bottomLeftX;
        this.bottomLeftY = bottomLeftY;
        this.bottomRightX = bottomRightX;
        this.bottomRightY = bottomRightY;
    }

    public double getTopLeftX() {
        return topLeftX;
    }

    public void setTopLeftX(double topLeftX) {
        this.topLeftX = topLeftX;
    }

    public double getTopLeftY() {
        return topLeftY;
    }

    public void setTopLeftY(double topLeftY) {
        this.topLeftY = topLeftY;
    }

    public double getTopRightX() {
        return topRightX;
    }

    public void setTopRightX(double topRightX) {
        this.topRightX = topRightX;
    }

    public double getTopRightY() {
        return topRightY;
    }

    public void setTopRightY(double topRightY) {
        this.topRightY = topRightY;
    }

    public double getBottomLeftX() {
        return bottomLeftX;
    }

    public void setBottomLeftX(double bottomLeftX) {
        this.bottomLeftX = bottomLeftX;
    }

    public double getBottomLeftY() {
        return bottomLeftY;
    }

    public void setBottomLeftY(double bottomLeftY) {
        this.bottomLeftY = bottomLeftY;
    }

    public double getBottomRightX() {
        return bottomRightX;
    }

    public void setBottomRightX(double bottomRightX) {
        this.bottomRightX = bottomRightX;
    }

    public double getBottomRightY() {
        return bottomRightY;
    }

    public void setBottomRightY(double bottomRightY) {
        this.bottomRightY = bottomRightY;
    }
}
