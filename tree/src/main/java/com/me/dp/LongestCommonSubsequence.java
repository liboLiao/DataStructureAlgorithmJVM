package com.me.dp;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by llb on 2018/5/2.
 * 求2个串的最长公共子序列，主要用于2个串的相似度比较，
 * 与求最长公共子串是有区别的，相似度函数为getSimilarityDegree(X,Y,最长公共子序列的长度)
 * <p>
 * 建模
 * 输入：Xi=<x1,x2,...,xi>表示X序列的前i个字符(1<=i<=m)
 * 输入：Yj=<y1,y2,...,yj>表示Y序列的前j个字符(1<=j<=n)
 * 输入：令X=Xm，Y=Yn
 * 输出：字符串X和Y的最长公共子序列
 * 动规求解第一步，根据问题定义
 * 定义LCS(Xi,Yj)为字符串X的前i个字符和Y的前j个字符的最长公共子序列
 * 则最终求解的是LCS(Xm,Yn):字符串X和Y的最长公共子序列
 * <p>
 * 经过一番研究和数学证明有以下递推式：
 * LCS(Xi,Yj)=LCS(Xi-1,Yj-1)+(xi or yj) [if xi==yj]
 * LCS(Xi,Yj)=max{LCS(Xi-1,Yj),LCS(Xi,Yj-1)} [if xi!=yj]
 * <p>
 * 算法实现是非递归地迭代每一步的状态[LCS(Xi,Yj)和LCS(Xi,Yj)的长度]，并求得最终的状态，有如下矩阵表格：
 * ` `  0   1   2   3   4   5   6
 * ` `  X   b   d   c   a   b   a
 * ------------------------------------
 * 0 Y  0   0   0   0   0   0   0
 * ------------------------------------
 * 1 a  0   0   0   0   1   1   1
 * ------------------------------------
 * 2 b  0   1   1   1   1   2   2
 * ------------------------------------
 * 3 c  0   1   1   2   2   2   2
 * ------------------------------------
 * 4 b  0   1   1   2   2   3   3
 * ------------------------------------
 * 5 d  0   1   2   2   2   3   3
 * ------------------------------------
 * 6 a  0   1   2   2   3   3   4
 * ------------------------------------
 * 7 b  0   1   2   2   3   4   4
 * ------------------------------------
 */
public class LongestCommonSubsequence {

    /**
     * 递归先是一步一步往初值或者边界值深入，再按原路一步一步返回的这么一个过程
     * 在实际中不适用，10000和10000的比较可以想象系统进程会在栈区开辟多少个帧栈吗？
     *
     * @param X
     * @param Y
     * @param i
     * @param j
     * @return 最长公共子序列的长度
     */
    public int getLCS(String X, String Y, int i, int j) {
        if (X.charAt(i) == Y.charAt(j)) {
            //上面矩阵的第一行和第一列都是0
            if (X.length() == 0 || Y.length() == 0) {
                return 1;
            }
            return getLCS(X.substring(0, X.length() - 1), Y.substring(0, Y.length() - 1), X.length() - 1, Y.length() - 1) + 1;
        } else {
            if (X.length() == 0 || Y.length() == 0) {//有点问题，需要再次考虑
                return 1;
            }
            int l1 = getLCS(X.substring(0, X.length() - 1), Y, X.length() - 2, Y.length() - 1);
            int l2 = getLCS(X, Y.substring(0, Y.length() - 1), X.length() - 1, Y.length() - 2);
            return l1 > l2 ? l1 : l2;
        }
    }

    public float getSimilarityDegree(String X, String Y, int maxSubqLength) {
        if (X != null && Y != null) {
            return (maxSubqLength * 1.00f / X.length() + maxSubqLength * 1.00f / Y.length()) / 2;
        }
        return 0;
    }

    /**
     * 非递归的算法
     *
     * @param X
     * @param Y
     * @return 最长公共子序列的长度
     */
    public int getLCS(String X, String Y) {
        char[] xChars = X.toCharArray();
        char[] yChars = Y.toCharArray();

        int[][] states = new int[yChars.length + 1][xChars.length + 1];
        //给矩阵填写状态值
        //矩阵的第一行和第一列都是0
        for (int j = 0; j <= xChars.length; j++) {
            states[0][j] = 0;
        }
        for (int i = 1; i <= yChars.length; i++) {
            states[i][0] = 0;
        }

        //给其他位置填值
        for (int i = 1; i <= yChars.length; i++) {
            char yc = Y.charAt(i - 1);
            for (int j = 1; j <= xChars.length; j++) {
                char xc = X.charAt(j - 1);
                if (xc == yc) {
                    states[i][j] = states[i - 1][j - 1] + 1;
                } else {
                    states[i][j] = states[i - 1][j] >= states[i][j - 1] ? states[i - 1][j] : states[i][j - 1];
                }
            }
        }

        //打印矩阵
        for (int i = 0; i <= yChars.length; i++) {
            String lingString = "";
            for (int j = 0; j <= xChars.length; j++) {
                lingString += states[i][j] + ",";
            }
            System.out.println(lingString);
        }
        System.out.println("最大子序列的长度：" + states[yChars.length][xChars.length]);


        //找出最长子序列并打印，从矩阵的右下角开始寻找
        int i = yChars.length - 1;
        int j = xChars.length - 1;
        Stack<java.lang.Character> characters = new Stack<java.lang.Character>();
        while (i >= 0 && j >= 0) {
            if (xChars[j] == yChars[i]) {
                characters.add(xChars[j]);
                i--;
                j--;
            } else {
//                int top = states[i-1+1][j+1];
//                int l = states[i+1][j-1+1];
                if (states[i - 1 + 1][j + 1] > states[i + 1][j - 1 + 1]) {
                    i--;
                } else if (states[i - 1 + 1][j + 1] < states[i + 1][j - 1 + 1]) {
                    j--;
                } else {
                    i--;//j--
                }
            }
        }
        while (characters.size()>0) {
            System.out.print(characters.pop() + ",");
        }

        return states[yChars.length][xChars.length];
    }

    class Character {
        char content;
        Character next;
        Character pre;

        public Character(char content) {
            this.content = content;
        }
    }

    public void printAllLCS(String X, String Y, int[][] states) {
        char[] xChars = X.toCharArray();
        char[] yChars = Y.toCharArray();

        //找出最长子序列，从矩阵的右下角开始寻找
        int i = yChars.length - 1;
        int j = xChars.length - 1;
        List<Character> lcs_s = new ArrayList<Character>();




        //打印这些最大子序列
    }
    //有问题的一个方法，有待以后完善
    public void printLCS(int[][] states,List<Character> lcs_s,char[] xChars,char[] yChars,int i,int j) {
        Character temp = null;
        while (i >= 0 && j >= 0) {
            if (xChars[j] == yChars[i]) {
                Character c = new Character(xChars[j]);
                c.next = temp;
                temp = c;
                i--;
                j--;
            } else {
//                int top = states[i-1+1][j+1];
//                int l = states[i+1][j-1+1];
                if (states[i - 1 + 1][j + 1] > states[i + 1][j - 1 + 1]) {
                    i--;
                } else if (states[i - 1 + 1][j + 1] < states[i + 1][j - 1 + 1]) {
                    j--;
                } else {
                    i--;
                    printLCS(states,lcs_s,xChars,yChars,i,j);
                    j--;
                    printLCS(states,lcs_s,xChars,yChars,++i,j);
                }
            }
        }
    }


    public static void main(String[] agrs) {
        String X = "bdcaba";
        String Y = "abcbdab";
        new LongestCommonSubsequence().getLCS(X, Y);
    }

}
