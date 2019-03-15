package com.me.sort;

/**
 * Created by llb on 2018/7/19.
 * O(n^2)
 */
public class InsertSort {

    private int[] arr = {12, 14, 1, 5, 20, 30, 8, 3, 2, 6};

    /**
     * 每步将一个待排序的记录，按其关键码值的大小插入前面已经排序的文件中适当位置上，直到全部插入完为止。
     */
    public void insertSort() {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }else
                    break;
            }
        }
    }

    public void insertSort2(int[] array){
        for(int i=1;i<array.length;i++){
            int j=i;
            int target=array[i];//表示想插入的数据
            while(j>0  && target<array[j-1]){//如果插入的数据小于数组的前一个时
                array[j]=array[j-1];
                j--;
            }
            array[j]=target;
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
        InsertSort insertSort = new InsertSort();
        insertSort.test();
        insertSort.insertSort();
        insertSort.test();
    }
}
