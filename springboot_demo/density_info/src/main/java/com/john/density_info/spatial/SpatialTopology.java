package com.john.density_info.spatial;

/**
 * 空间拓扑关系
 */
public class SpatialTopology {
    /**
     * 判断对象空间实体类型
     * @param obj
     * @return
     */
    public static String checkSpatialBean(Object obj){
        if (obj instanceof  LBSPoint) {
            LBSPoint point = (LBSPoint)obj;
            return point.toString();
        }
        if (obj instanceof  LBSLine) {
            LBSLine line = (LBSLine)obj;
            return line.toString();
        }
        if (obj instanceof  LBSPolygon) {
            LBSPolygon polygon = (LBSPolygon) obj;
            return polygon.toString();
        }
        return "no spatial bean";
    }

    /**
     * 判断点与点之间的拓扑重合
     * @param p1
     * @param p2
     * @return
     */
    public static boolean pointEqPoint(LBSPoint p1, LBSPoint p2){
        if (p1 == null || p2 == null) {
            return false;
        }
        return ((p1.x == p2.x) && (p1.y == p2.y));
    }

    /**
     * 判断点与线的拓扑关系 点是否在线上
     * @param p
     * @param l
     * @return
     */
    public static boolean pointInLine(LBSPoint p, LBSLine l){
        if (p == null || l == null || l.pArr == null || l.pArr.length < 1) {
            return false;
        }

        double k = 0;
        double b = 0;

        for (int i = 0; i < l.pArr.length - 1; i++) {
            k = (l.pArr[i + 1].y - l.pArr[i].y)/(l.pArr[i + 1].x - l.pArr[i].y);
            b = l.pArr[i].y - k * l.pArr[i].x;
            if (p.y == (k * p.x + b)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断点与线的拓扑关系 点是否包含在面内
     * @param point
     * @param polygon
     * @return
     */
    public static boolean pointInPolygon(LBSPoint point, LBSPolygon polygon){
        if (point == null || polygon == null || polygon.boundArr == null || polygon.boundArr.length < 1) {
            return false;
        }
        if (point.x >= polygon.mbr.getTopLeftX() && point.x <= polygon.mbr.getTopRightX() &&
                point.y >= polygon.mbr.getBottomLeftY() && point.y <= polygon.mbr.getTopLeftY()) {
            return true;
        }
        return false;
    }

    /**
     * 判断线与线的拓扑关系 0 相离 1 相交
     * @param l1
     * @param l2
     * @return
     */
    public static int lineVsLine(LBSLine l1, LBSLine l2){
        // to-do
        return 0;
    }

    /**
     * 判断线与面的拓扑关系 0 相离 1 相交 2 相切
     * @param line
     * @param polygon
     * @return
     */
    public static int lineVsPolygon(LBSLine line, LBSPolygon polygon){
        // to-do
        return 0;
    }

    /**
     * 判断面与面的拓扑关系 0 相离 1 相交 2 相切
     * @param polygon1
     * @param polygon2
     * @return
     */
    public static int polygonVsPolygon(LBSPolygon polygon1, LBSPolygon polygon2){
        // to-do
        return 0;
    }
}
