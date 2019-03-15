package com.me.sort;

/**
 * Created by llb on 2018/7/19.
 * 使用多个带步长的插入排序来排序，例如用依次用步长为5，3，1的插入排序算法来对arr数组进行排序
 * 使用场景：麻将在玩的过程中的重复排序（因为数据源本身即是有序）；已知的最好步长序列
 * 由Marcin Ciura设计（1，4，10，23，57，132，301，701，1750，…） 这项研究也表明
 * “比较在希尔排序中是最主要的操作，而不是交换。”用这样步长序列的希尔排序比
 * 插入排序和堆排序都要快，甚至在小数组中比快速排序还快，但是在涉及大量数据时希尔排序还是比快速排序慢。
 *
 * O(nlogn)
 */
public class ShellSort {

    private int[] arr = {12, 14, 1, 5, 20, 30, 8, 3, 2, 6};

    /**
     * 每步将一个待排序的记录，按其关键码值的大小插入前面已经排序的文件中适当位置上，直到全部插入完为止。
     */
    public void insertSort() {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                } else
                    break;
            }
        }
    }

    public void insertSort2(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            int target = arr[i];//表示想插入的数据
            while (j > 0 && target < arr[j - 1]) {//如果插入的数据小于数组的前一个时
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = target;
        }
    }

    private void shellSort(int step) {
        for (int k = 0; k < step; k++) {
            for (int i = k + step; i < arr.length; i += step) {
                int j = i;
                int target = arr[i];//表示想插入的数据
                while (j > step - 1 && target < arr[j - step]) {//如果插入的数据小于数组的前一个时
                    arr[j] = arr[j - step];
                    j -= step;
                }
                arr[j] = target;
            }
        }
    }

    private void test() {
        for (int i : arr) {
            System.out.print(i + ",");
        }
        System.out.println("");
        System.out.println("------------");
    }

    public static void main(String[] args) {
        ShellSort shellSort = new ShellSort();
        shellSort.test();
        shellSort.shellSort(3);
        shellSort.test();
        shellSort.shellSort(1);
        shellSort.test();
    }


}
