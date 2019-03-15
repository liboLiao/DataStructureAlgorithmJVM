package com.me.sort;

/**
 * Created by llb on 2018/8/6.
 * 由extra包下的PriorityQueue优先级队列改装而来
 */
public class HeapSort {

    private int[] arr = {12, 14, 1, 5, 20, 30, 8, 3, 2, 6};


    /**
     * //向下逐级调整成符合堆的定义
     * @param k:从第k个元素开始自顶向下调整成大顶堆或者小顶堆
     * @param d:数组后面d个已经排好序的元素不算在堆内
     */
    private void siftDown(int k,int d) {
        int tempK = arr[k];
        int half = (arr.length-d) >>> 1;        // loop while a non-leaf
        while (k < half) {
            int child = (k << 1) + 1; // assume left child is least
            int c = arr[child];
            int right = child + 1;
            if (right < (arr.length-d) && c < arr[right])
                c = arr[child = right];
            if (tempK >= c)
                break;
            arr[k] = c;
            k = child;
        }
        arr[k] = tempK;
    }

    //创建堆
    private void heapify() {
        //超过(size >>> 1) - 1下标位置上的元素都没有孩子节点，所以从该位置开始自底向上，
        // 比较父子大小，进而使数组成为大顶堆或者小顶堆
        for (int i = (arr.length >>> 1) - 1; i >= 0; i--)
            siftDown(i,0);
    }

    //堆排序
    public void heapSort() {
        heapify();
        for (int i = arr.length-1; i > 0; i--) {
            int t = arr[i];
            arr[i] = arr[0];
            arr[0] = t;

            siftDown(0,arr.length-i);
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
        HeapSort heapSort = new HeapSort();
        heapSort.test();
        heapSort.heapSort();
        heapSort.test();
    }
}
