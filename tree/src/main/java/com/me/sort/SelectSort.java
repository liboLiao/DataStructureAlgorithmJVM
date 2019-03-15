package com.me.sort;

/**
 * Created by llb on 2018/7/19.
 * O(n^2)
 */
public class SelectSort {

    private int[] arr = {12, 14, 1, 5, 20, 30, 8, 3, 2, 6};

    /**
     * 初始时在序列中找到最小（大）元素，放到序列的起始位置作为已排序序列；
     * 然后，再从剩余未排序元素中继续寻找最小（大）元素，放到已排序序列的末尾。
     * 以此类推，直到所有元素均排序完毕。
     */
    public void selectSort() {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            //互换i，j位置的值
            if(minIndex!=i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
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
        SelectSort selectSort = new SelectSort();
        selectSort.test();
        selectSort.selectSort();
        selectSort.test();
    }
}
