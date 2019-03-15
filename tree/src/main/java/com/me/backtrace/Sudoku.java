package com.me.backtrace;

import java.util.Scanner;

/**
 * Created by llb on 2018/7/18.
 * 本算法志在讲解回溯，所以关于数独的本算法可以求出多个可行解
 */
public class Sudoku {
    private static int n = 9;
    public static int[][] result = /*new int[9][9]; //可以产生所有的数独案例*/
            {
            //最难的数独
           /* {8, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 3, 6, 0, 0, 0, 0, 0},
            {0, 7, 0, 0, 9, 0, 2, 0, 0},
            {0, 5, 0, 0, 0, 7, 0, 0, 0},
            {0, 0, 0, 0, 4, 5, 7, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 3, 0},
            {0, 0, 1, 0, 0, 0, 0, 6, 8},
            {0, 0, 8, 5, 0, 0, 0, 1, 0},
            {0, 9, 0, 0, 0, 0, 4, 0, 0}*/

            {0,0,8,3,0,9,1,0,0},
            {9,0,0,0,6,0,0,0,4},
            {0,0,7,5,0,4,8,0,0},
            {0,3,6,0,0,0,5,4,0},
            {0,0,1,0,0,0,6,0,0},
            {0,4,2,0,0,0,9,7,0},
            {0,0,5,9,0,7,3,0,0},
            {6,0,0,0,1,0,0,0,8},
            {0,0,4,6,0,8,2,0,0}
                    //多解的数独
                    /*{1, 2, 3, 4, 5, 6, 7, 8, 9},
                    {4, 0, 0, 0, 0, 0, 0, 0, 0},
                    {7, 0, 0, 0, 0, 0, 0, 0, 0},
                    {2, 0, 0, 0, 0, 0, 0, 0, 0},
                    {3, 6, 0, 8, 9, 7, 0, 1, 4},
                    {8, 9, 0, 2, 1, 4, 0, 3, 5},
                    {6, 4, 0, 9, 3, 0, 0, 0, 0},
                    {9, 7, 0, 5, 4, 1, 0, 6, 8},
                    {5, 3, 0, 6, 7, 8, 0, 4, 2}*/

            /* 解如下：   {1,2,3,4,5,6,7,8,9},
                {4,5,6,7,8,9,1,2,3},
                {7,8,9,1,2,3,4,5,6},
                {2,1,4,3,6,5,8,9,7},
                {3,6,5,8,9,7,2,1,4},
                {8,9,7,2,1,4,6,3,5},
                {6,4,8,9,3,2,5,7,1},
                {9,7,2,5,4,1,3,6,8},
                {5,3,1,6,7,8,9,4,2},
                -----------------
                {1,2,3,4,5,6,7,8,9},
                {4,5,9,7,8,3,1,2,6},
                {7,8,6,1,2,9,4,5,3},
                {2,1,4,3,6,5,8,9,7},
                {3,6,5,8,9,7,2,1,4},
                {8,9,7,2,1,4,6,3,5},
                {6,4,8,9,3,2,5,7,1},
                {9,7,2,5,4,1,3,6,8},
                {5,3,1,6,7,8,9,4,2},
                ------------------
                {1,2,3,4,5,6,7,8,9},
                {4,8,6,7,2,9,1,5,3},
                {7,5,9,1,8,3,4,2,6},
                {2,1,4,3,6,5,8,9,7},
                {3,6,5,8,9,7,2,1,4},
                {8,9,7,2,1,4,6,3,5},
                {6,4,8,9,3,2,5,7,1},
                {9,7,2,5,4,1,3,6,8},
                {5,3,1,6,7,8,9,4,2},
                -----------------
                {1,2,3,4,5,6,7,8,9},
                {4,8,9,7,2,3,1,5,6},
                {7,5,6,1,8,9,4,2,3},
                {2,1,4,3,6,5,8,9,7},
                {3,6,5,8,9,7,2,1,4},
                {8,9,7,2,1,4,6,3,5},
                {6,4,8,9,3,2,5,7,1},
                {9,7,2,5,4,1,3,6,8},
                {5,3,1,6,7,8,9,4,2},
                -----------------*/
            };

   /*       8 1 2 7 5 3 6 4 9
            9 4 3 6 8 2 1 7 5
            6 7 5 4 9 1 2 8 3
            1 5 4 2 3 7 8 9 6
            3 6 9 8 4 5 7 2 1
            2 8 7 1 6 9 5 3 4
            5 2 1 9 7 4 3 6 8
            4 3 8 5 2 6 9 1 7
            7 9 6 3 1 8 4 5 2*/

            /*4 2 8 3 7 9 1 6 5
           9 5 3 8 6 1 7 2 4
           1 6 7 5 2 4 8 3 9
           8 3 6 7 9 2 5 4 1
           7 9 1 4 3 5 6 8 2
           5 4 2 1 8 6 9 7 3
           2 8 5 9 4 7 3 1 6
           6 7 9 2 1 3 4 5 8
           3 1 4 6 5 8 2 9 7
           -----------------*/

    public static void main(String[] agrs) {
//        sudoku(0, 0);
        //测试网上的copy答案，只能有一个可行解
        new AnotherAnswer().main();
    }

    public static void sudoku(int row, int col) {
        //获得一个可行解向量，打印后可以退出
        if (row == 8 && col == 9) {
            printResult();
//            return;
        }

        if (col == 9) {
            col = 0;
            row++;
        }

        if (row == 9 || col == 9) {
            return;
        }

        if (result[row][col] == 0) {
            for (int k = 1; k <= 9; k++) {
                if (judge(row, col, k)) {
                    result[row][col] = k;
                    sudoku(row, col + 1);
                    //若下一个位置(row,col+1)(或者下一个值为0的位置)1-9的数字都不能填入，则当前位置(row,col)要置回0以便下一个k值是否可以填充的判断
                    //设想一种情况：在第一行(或者某一行)的最后一个为0值的小格子填充了一个恰当的值，按照逻辑此时转入第二行(下一行)的第一个为0值的小
                    //格子，若此小格子排斥1-9，则该递归回溯至第一行（上一行）的那个小格子继续递增的k值判断，若回溯到的第一行（上一行）最后一个为0值
                    // 的小格子和第二行（下一行）第一个为0值的小格子不在同一个宫内，且不在同一列，(或者说再加上一个条件不在同一行)，则无论第一行
                    // （上一行）的那个小格子填充什么样恰当的k值，都不会影响到第二行（下一行）第一个为0值的小格子的排斥1-9的事实，除非是从第二行
                    // （下一行）第一个为0值的小格子回溯至和其在同一列或同一行或同一宫的那个小格子继续递增的k值判断并换值后，再按照该逻辑重新填充
                    // 至第二行（下一行）第一个为0值的小格子，此时这个小格子可能不在排斥1-9的所有数字，而是按1-9升序选填了一个数字。其中回溯过程中的
                    //一些原来初始为0的小格子，现在是已经填充了某一个数字，而这些初始为0的小格子在回溯过程中需要重新按此逻辑(只有为0的小格子才k值判断)
                    // 填值，所以要把填完值的小格子重新置为0，以便重新按此逻辑填值。
                    //这里面有一个回溯的优化，就是小格子排斥1-9之后的回溯，可以回溯至和其在同一列或同一行或同一宫的那个小格子，再继续递增的k值判断，
                    //但这里统一在设值之后，回设为0似乎回溯的不太友好。
                   /* if(row==8 && col ==8){
                        System.out.println("set 0 befor result["+row+"]["+col+"]="+result[row][col]);
                    }*/
                    result[row][col] = 0;
                    /*if(row==8 && col ==8){
                        System.out.println("set 0 after result["+row+"]["+col+"]="+result[row][col]);
                    }*/
                }
            }
        } else {
            sudoku(row, col + 1);
        }
    }

    private static void printResult() {
        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder("{");
            for (int j = 0; j < n; j++) {
                sb.append(result[i][j] + ",");
//                System.out.print(result[i][j] + " ");
            }
            sb.deleteCharAt(sb.lastIndexOf(",")).append("},");
            System.out.println(sb);
        }
        System.out.println("-----------------");
    }

    public static boolean judge(int row, int col, int number) {
        //若这个小格子所在行和所在列都已经填充过number，则该number不可再用
        for (int i = 0; i < n; i++) {
            if (result[i][col] == number || result[row][i] == number) {
                return false;
            }
        }
        //若这个小格子所在宫已经填充过number，则number不可再用
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (result[row / 3 * 3 + i][col / 3 * 3 + j] == number) {
                    return false;
                }
            }
        }
        return true;
    }


    //不能回溯的网上的copy答案
    static class AnotherAnswer {
        static int[][] a = new int[9][9];
        static boolean[][] cols = new boolean[9][9];
        static boolean[][] rows = new boolean[9][9];
        static boolean[][] blocks = new boolean[9][9];//九大宫的九个数字

        public static void main() {
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a.length; j++) {
                    a[i][j] = result[i][j];
                    if (a[i][j] != 0) {
                        int k = i / 3 * 3 + j / 3;//划分九宫格,这里以行优先，自己也可以列优先
                        int val = a[i][j] - 1;
                        rows[i][val] = true;
                        cols[j][val] = true;
                        blocks[k][val] = true;
                    }
                }
            }//数据装载完毕
            DFS(a, cols, rows, blocks);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(a[i][j] + " ");
                }
                System.out.println(a[i][8]);
            }
        }


        public static boolean DFS(int[][] a, boolean[][] cols, boolean[][] rows, boolean[][] blocks) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (a[i][j] == 0) {
                        int k = i / 3 * 3 + j / 3;
                        for (int l = 0; l < 9; l++) {
                            if (!cols[j][l] && !rows[i][l] && !blocks[k][l]) {//l对于的数字l+1没有在行列块中出现
                                rows[i][l] = cols[j][l] = blocks[k][l] = true;
                                a[i][j] = 1 + l;//下标加1
                                if (DFS(a, cols, rows, blocks)) return true;//递进则返回true
                                rows[i][l] = cols[j][l] = blocks[k][l] = false;//递进失败则回溯
                                a[i][j] = 0;
                            }
                        }
                        return false;//a[i][j]==0时，l发现都不能填进去
                    }//the end of a[i][j]==0
                }
            }
            return true;//没有a[i][j]==0,则返回true
        }
    }
}
