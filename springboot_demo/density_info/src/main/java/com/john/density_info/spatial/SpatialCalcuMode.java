package com.john.density_info.spatial;

/**
 * 空间量算模型类
 */
public class SpatialCalcuMode {

    /**
     * 空间量算两点间欧氏距离
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double getODdistanceFrom2P(double x1, double y1, double x2, double y2){
        double res;
        res = Math.sqrt(((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2)));
        return  res;
    }

    /**
     * 空间量算折线欧氏长度 根据point类的数组来逐一计算
     * @param arr
     * @return
     */
    public static double getODistanceFromArrP(LBSPoint[] arr){
        double res = 0;
        for (int i = 0; i < arr.length - 1; i++){
            res += getODdistanceFrom2P(arr[i].x, arr[i].y, arr[i + 1].x, arr[i + 1].y);
        }
        return res;
    }
    /**
     * 计算矩形面积 参数为矩形左上角(x1,y1) 右下角(x2, y2)
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double getSFromRec(double x1, double y1, double x2, double y2){
        double res;
        res = Math.abs((x2 - x1)) * Math.abs((y2 - y1));
        return  res;
    }

    /**
     * 计算两点与x轴围成的梯形面积
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double getSFromTrapezoid(double x1, double y1, double x2, double y2){
        double res;
        res = (Math.abs(x2 - x1) * (Math.abs(y1) + Math.abs(y2)))/2;
        return res;
    }


    /**
     * 几何交叉公式求解多边形面积（数学归纳法证明）
     * 参数为多边形的外边界,点的集合数组即折线
     * @param parr
     */
    public static double getSFromPolygon(LBSPoint[] parr){
        double res = 0;
        for (int i = 0; i < parr.length -1; i++) {
            res += (((parr[i].x * parr[i + 1].y) - (parr[i + 1].x * parr[i].y)) +
                    ((parr[parr.length - 1].x * parr[0].y) - (parr[0].x * parr[parr.length - 1].y))
                    )/2;
        }
        return res;
    }
    /**
     * 几何交叉公式求解多边形面积（数学归纳法证明）
     * 参数为多边形的外边界,点的集合数组即折线
     * @param polygon
     */
    public static double getSFromPolygonPro(LBSPolygon polygon){
        double res = 0;
        LBSLine[] boundArr = polygon.boundArr;
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
        res = getSFromPolygon(parr);
        return res;
    }
}
