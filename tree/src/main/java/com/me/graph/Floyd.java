package com.me.graph;

/**
 * Created by llb on 2018/8/6.
     1.先生成图的邻接距阵与路径距阵
     2.用d[i,j]表示i点到j点的最短距离，但i到j可能会经过K个顶点才能找到最短路径
     则d[i][j]=d[i][k]+d[k][j],其中的k可能为多个点
     3.遍历全部顶点，如果出现d[i][j]>d[i][k]+d[k][j],我们就用短的路径替换长的路径，如图中v1->v2直接走为4，
     用v1->v0->v2==3
     4.为了找到全部结点对之间的最短路径,可以在路径计算过程中，取数组p[i][j]=p[i][k]用于记录每次的替换过程;
 * 用邻接矩阵存储
 * 适用于有向和无向图，任意2个顶点之间的最短距离的路径选择
 */
public class Floyd {
    //floyd算法
    @org.junit.Test
    public void testFloyd() {
        floyd();
        printShortPath();
    }

    public static final int INF = Integer.MAX_VALUE;
    //邻接距阵，初始存储的是顶点之间的距离，不可达的顶点之间存储的是INF，最终存储的是顶点之间的最短距离
    public static int[][] d = new int[][]{
            {0, 2, 1, 5},
            {2, 0, 4, INF},
            {1, 4, 0, 3},
            {5, INF, 3, 0}
    };
    //存储的是顶点之间的最短距离的路径信息
    public static int[][] p=new int[][]{
            {0,1,2,3},
            {0,1,2,3},
            {0,1,2,3},
            {0,1,2,3}
    };

    public static void floyd() {
        for (int k = 0; k < d.length; k++) {
            for (int i = 0; i < d.length; i++) {
                for (int j = 0; j < d.length; j++) {
                    if (d[i][j] > d[i][k] + d[k][j]) {
                        d[i][j] = d[i][k] + d[k][j];
                        //记录下路径
                        p[i][j]=p[i][k];
                    }
                }
            }
        }
//        for(int i=0;i<d.length;i++){
//            for (int j = 0; j < d.length; j++) {
//                if(d[i][j]>d[i][1]+d[1][j]){
//                    d[i][j]=d[i][1]+d[1][j];
//                }
//            }
//        }
//        for(int i=0;i<d.length;i++){
//            for (int j = 0; j < d.length; j++) {
//                if(d[i][j]>d[i][2]+d[2][j]){
//                    d[i][j]=d[i][2]+d[2][j];
//                }
//            }
//        }
//        for(int i=0;i<d.length;i++){
//            for (int j = 0; j < d.length; j++) {
//                if(d[i][j]>d[i][3]+d[3][j]){
//                    d[i][j]=d[i][3]+d[3][j];
//                }
//            }
//        }
        printArray(d);
        printArray(p);
    }
    //反推路径
    public static void printShortPath(){
        //找所有节点的路径
        for (int i = 0; i < d.length; i++) {
            //通过列号找到原来的一组路径
            for (int j = 0; j < d.length; j++) {
                System.out.print("V"+i+"->V"+j+"weight:"+d[i][j]+" path:"+i);
                int k=p[i][j];//先看路径中第0列，哪些是让改动了
                while(k!=j){
                    System.out.print("->"+k);
                    k=p[k][j];
                }
                System.out.println();
            }
        }
    }


    public static void printArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------");
    }
}
