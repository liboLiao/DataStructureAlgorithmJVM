package com.me.sort;

/**
 * Created by llb on 2018/4/12.
 * 递归方程的算法分析见《算法导论》和我的笔记
 * O(nlogn)
 * 求渐进上界的方法:O(g(n))
 * 1.代换法，先猜测一个上界，再带入递推方程用数学归纳法证明之
 * 2.还原迭代
 * 3.递归树
 */
public class Merge {

    private int[] arr = {12, 14, 1, 5, 20, 30, 8, 3, 2, 6};


    //递归
    public void merge(int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            merge(start, mid);
            merge(mid + 1, end);
            merge(start, mid, end);
        }
    }

    private void merge(int start, int mid, int end) {
        int i = start;
        int j = mid + 1;
        int k = 0;
        int temp = 0;
        while (i <= mid && j <= end) {
            if (arr[i] > arr[j]) {
                temp = arr[j];
                //移动[i--mid]项往后一格
                k = j;
                while (k>i){
                    arr[k] = arr[--k];
                }
                arr[i] = temp;
                j++;
                mid++;//移动带来的比较中线后移，以便mid之前的项都能和j之后的项比较大小
            }
            i++;
        }
    }

    private void test() {
        for (int i : arr) {
            System.out.print(i + ",");
        }
        System.out.println("");
        System.out.println("------------");
    }

    public static void main(String[] agrs) {
        Merge merge = new Merge();
        merge.test();
        merge.merge(0, 9);
        merge.test();
    }
}
