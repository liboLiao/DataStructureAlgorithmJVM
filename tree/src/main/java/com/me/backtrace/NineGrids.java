package com.me.backtrace;

/**
 * Created by llb on 2018/7/17.
 * 填数的一个经典算法：
 * 1.把1填入第一行的中间一格；
 * 2.然后依次把后面的数字填入前面一个数字的右上格，若右上格是空的，则填入，若右上格有数，则填入当前位置的下面。
 */
public class NineGrids {
    public static  int n = 3;
    public static int[][] array = new int[n][n];

    public static void main(String[] agrs) {
        squaredUp(array);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]+" ");
            }
            System.out.println();
        }
    }

    //逻辑
    public static void squaredUp(int[][] array){
        int x=1;//需要填入的值
        //定义出行和列的初始位置
        int row=0;
        int col=n/2;
        array[row][col]=x;
        //开始填入后面的所有数字
        while(x<n*n){
            //先记录下原来的位置
            int tempRow=row;
            int tempCol=col;
            //向右上走
            row--;
            if(row<0){
                row=n-1;
            }
            col++;
            if(col==n){
                col=0;
            }
            x++;//需要填入的数字
            //开始填入数据
            if(array[row][col]==0){//如果右上没填过
                array[row][col]=x;
            }else{//如果右上已经填过的,移动当前位置的下面,回溯处理的地方！！！
                row=tempRow;
                col=tempCol;
                row++;
                array[row][col]=x;
            }

        }
    }
}
