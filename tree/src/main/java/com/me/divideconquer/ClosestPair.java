package com.me.divideconquer;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by llb on 2018/4/28.
 * 找出一块平面点集P内的最近点对，设有n个点，n>1
 * <p>
 * 分治算法的时间复杂度：W(n)=aW(n/b)+d(n)
 * <p>
 * 降低d(n)的阶，增加预处理
 */
public class ClosestPair {

    public static class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "x:"+x+",y:"+y;
        }
    }

    public static class PointX extends Point implements Comparable {
        int id;

        public PointX(double x, double y, int id) {
            super(x, y);
            this.id = id;
        }

        public int compareTo(Object o) {
            double x = ((PointX) o).x;
            if (this.x < x) return -1;
            if (this.x == x) return 0;
            return 1;
        }
    }

    public static class PointY extends Point implements Comparable {
        int pointX_id;

        public PointY(double x, double y, int pointX_id) {
            super(x, y);
            this.pointX_id = pointX_id;
        }

        public int compareTo(Object o) {
            double y = ((PointY) o).y;
            if (this.y < y) return -1;
            if (this.y == y) return 0;
            return 1;
        }
    }

    public static class Pair {
        PointX a;
        PointX b;
        double distance;//a,b两点之间的距离

        public Pair(PointX a, PointX b, double distance) {
            this.a = a;
            this.b = b;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return a.toString() + "--" + b.toString()+"--distance:"+distance;
        }
    }

    public double dist(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public Pair getClosestPair(PointX[] x) {
        //依据X坐标排序点集
        Arrays.sort(x);

        //依据Y坐标排序点集
        PointY[] y = new PointY[x.length];
        for (int i = 0; i < x.length; i++) {
            y[i] = new PointY(x[i].x, x[i].y, i);
        }
        Arrays.sort(y);

        return closestPair(x, y, 0, x.length - 1);
    }

    private Pair closestPair(PointX[] x, PointY[] y, int l, int r) {
        if (r - l == 1)//2个点
            return new Pair(x[l], x[r], dist(x[l], x[r]));
        if (r - l == 2) {//3个点
            PointX a, b;
            double d1 = dist(x[l], x[l + 1]);
            double d2 = dist(x[l + 1], x[r]);
            double d3 = dist(x[l], x[r]);
            if (d1 <= d2) {
                if (d1 <= d3) {
                    a = x[l];
                    b = x[l + 1];
                } else {
                    a = x[l];
                    b = x[r];
                }
            } else {
                if (d2 <= d3) {
                    a = x[l + 1];
                    b = x[r];
                } else {
                    a = x[l];
                    b = x[r];
                }
            }
            return new Pair(a, b, dist(a, b));
        }

        //分治
        int m = (l + r) / 2;

        //会产生n个PointY对象O(n)
        PointY[] pyl = new PointY[m - l + 1];
        PointY[] pyr = new PointY[r - m];
        int pyl_i = 0, pyr_i = 0;
        for (int i = 0; i <= r-l; i++) {
            //中垂线上的点分给左边
            if (y[i].pointX_id <= m) pyl[pyl_i++] = y[i];
            else pyr[pyr_i++] = y[i];
        }
        //中垂线上的点分给左边
        Pair bestPair = closestPair(x, pyl, l, m);
        Pair pr = closestPair(x, pyr, m + 1, r);

        //合并
        if (bestPair.distance > pr.distance) {
            bestPair = pr;
        }
        //选出宽度小于2*bestPair.distance窄带中的点集
        int index = 0;
        for (int i = 0; i < y.length; i++) {
            if (Math.abs(x[m].x - y[i].x) < bestPair.distance) {
                if (i > index) {
                    y[index] = y[i];
                }
                index++;
            }
        }

        //在y[]中从0到index-1个元素中查找最近点对，查找时只和中垂线异侧的6个点计算距离
        for (int i = 0; i < index; i++) {
            //点是否在中垂线左侧
            boolean isLeft = y[i].x - x[m].x <= 0;
            for (int j = i + 1,k = 0; k<6 && j < index;j++) {
                //异侧要计算距离的点还要在y[i].y+bestPair.distance以下
                if(y[j].y - y[i].y <= bestPair.distance){
                    //是否是中垂线右侧侧的点
                    //将要和异侧的点计算距离，至多6次
                    boolean isRight = y[j].x - x[m].x > 0;
                    //点在左侧，去和右侧的点计算距离,至多计算6次
                    if(isLeft && isRight){
                        k++;
                    }
                    //点在右侧，去和左侧的点计算距离,至多计算6次
                    if((!isLeft) && (!isRight)){
                        k++;
                    }

                    double dp = dist(y[i],y[j]);
                    if(dp < bestPair.distance)
                        bestPair = new Pair(x[y[i].pointX_id],x[y[j].pointX_id],dp);
                }

               /* //点在左侧，去和右侧的点计算距离
                if(y[i].x - x[m].x <= 0 && y[j].x - x[m].x > 0){
                }
                //点在右侧，去和左侧的点计算距离
                if(y[i].x - x[m].x > 0 && y[j].x - x[m].x <= 0){
                }
                //异侧要计算距离的点还要在y[i].y+bestPair.distance以下
                if(y[j].y - y[i].y <= bestPair.distance){
                }*/
            }
        }
        System.out.println(bestPair);
        return bestPair;
    }

    public static void main(String[] agrs) {
//        test();;
        Random random = new Random();

        PointX[] pxs = new PointX[20];
        for (int i = 0; i < 20; i++) {
            PointX pointX = new PointX(random.nextDouble() * 10, random.nextDouble() * 10, i);
            pxs[i] = pointX;
        }

        ClosestPair closestPair = new ClosestPair();
        Pair pair =  closestPair.getClosestPair(pxs);
        System.out.println(pair);
    }

    public static void test() {
        int[] y = new int[]{2, 78, 23, 20, 78, 34, 10, 34, 23, 21, 35, 13, 14};
        int index = 0;
        for (int i = 0; i < y.length; i++) {
            if (y[i] < 30) {
                if (i > index) {
                    y[index] = y[i];
                }
                index++;
            }
        }
        for (int i = 0; i < index; i++) {
            System.out.print(y[i] + ",");
        }
    }

}
