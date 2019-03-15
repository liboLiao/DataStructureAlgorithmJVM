package com.me.divideconquer;

/**
 * Created by llb on 2018/4/26.
 * 使用分治法的思想，来快速排序
 * 具体的算法描述可以见我的博客
 */
public class QuickSort {
    /**
     * 以第一个元素为基准，进行一次划分
     *
     * @param arr
     * @param start
     * @param end
     * @return 划分的位置
     */
    public int quickCoreSort1(int[] arr, int start, int end) {
        int i = start + 1, j = end, temp = 0, pivot = arr[start];
        while (i < j) {
            //从左往右找到大于等于第一个元素的第一个元素位置
            while (arr[i] < pivot) {
                i++;
            }
            //从右往左找到小于等于第一个元素的第一个元素位置
            while (arr[j] > pivot) {
                j--;
            }
            if (i < j) {
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }

        if (i >= j && pivot > arr[j]) {
            temp = arr[j];
            arr[j] = arr[start];
            arr[start] = temp;
        }

        return j;
    }

    /**
     * 以中间元素为基准，进行一次划分
     *
     * @param arr
     * @param start
     * @param end
     * @return
     */
    public int quickCoreSort2(int[] arr, int start, int end) {
        return -1;
    }

    /**
     * 挖坑填数，以第一个元素为基准，实现一次划分
     * @param arr
     * @param start
     * @param end
     * @return
     */
    public int quickCoreSort3(int[] arr, int start, int end) {
        int i = start, j = end;
        int x = arr[start]; //s[l]即s[i]就是第一个坑
        while (i < j) {
            // 从右向左找小于x的数来填s[i]
            while (i < j && arr[j] >= x)
                j--;
            if (i < j) {
                arr[i] = arr[j]; //将s[j]填到s[i]中，s[j]就形成了一个新的坑
                i++;
            }

            // 从左向右找大于或等于x的数来填s[j]
            while (i < j && arr[i] < x)
                i++;
            if (i < j) {
                arr[j] = arr[i]; //将s[i]填到s[j]中，s[i]就形成了一个新的坑
                j--;
            }
        }
        //退出时，i等于j。将x填到这个坑中。
        arr[i] = x;

        return j;
    }

    /**
     * 递归分治策略
     *
     * @param arr
     * @param start
     * @param end
     */
    public void quickSort(int arr[], int start, int end) {
        if (start < end) {
//            int j = quickCoreSort1(arr, start, end);
            int j = quickCoreSort3(arr, start, end);
            for (int i = start; i <= end; i++) {
                System.out.print(arr[i] + ",");
            }
            System.out.println("arr[" + j + "]:" + arr[j]);
            quickSort(arr, start, j - 1);
            quickSort(arr, j + 1, end);
        }
    }

    public static void main(String[] agrs) {
        int[] arr = {27, 99, 0, 8, 13, 64, 86, 16, 7, 10, 88, 25, 90};
//        int[] arr = {27, 99, 0, 8, 13, 64,27, 86, 16,27, 27,7, 10, 88, 25, 90};
        for (int i : arr) {
            System.out.print(i + ",");
        }
        System.out.println("");
        QuickSort quickSort = new QuickSort();
        quickSort.quickSort(arr, 0, arr.length - 1);
        for (int i : arr) {
            System.out.print(i + ",");
        }
    }

}
