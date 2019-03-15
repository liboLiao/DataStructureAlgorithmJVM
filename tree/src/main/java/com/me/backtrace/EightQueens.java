package com.me.backtrace;

/**
 * Created by llb on 2018/7/17.
 * 使用回溯的递归算法，搜索出八皇后问题的所有解向量
 */
public class EightQueens {
    public static int n = 8;
    public static int index;
    public static int[] array = new int[n];

    public static void main(String[] agrs) {
        eightQueens(0);
    }

    public static void eightQueens(int row) {
        if (row == n) {
            printResult();
            return;
        }

        //在当前行，一列一列地判断是否可以放置皇后而不会与已经放入的皇后打架
        //若当前行没有一列可以放置皇后，则这里的递归调用会回溯至上一行的那一列再往后一列继续判断是否可以放置，
        //  是则又回到当前行重复的逻辑
        //  否则继续回溯至上上一行的那一列再往后一列继续判断是否可以放置...
        for (int col = 0; col < n; col++) {
            array[row] = col;
            if(jude(row)){//判断(row,col)这个位置是否可以放置皇后而不会与已经放入的皇后打架
                eightQueens(row+1);
            }
        }
    }

    /**
     * @param row 要判断放入哪一列的当前列号
     * @return
     */
    public static boolean jude(int row) {
        for(int i=0;i<row;i++){
            //1.不会与前面已经放入的皇后在同一列
            //2.不会与前面已经放入的皇后在同一对角线上
            if(array[i]==array[row] || Math.abs(i-row)==Math.abs(array[i]-array[row])){
                return false;
            }
        }
        return true;
    }

    public static void printResult() {
        System.out.print("第"+ (++index) +"种放置方案: ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
