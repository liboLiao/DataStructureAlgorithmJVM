package com.me.sort;

/**
 * Created by llb on 2018/7/19.
 * O(n^2)
 */
public class BubbleSort {

    private int[] arr = {12, 14, 1, 5, 20, 30, 8, 3, 2, 6};

    /**
     * 按照序位号依次前后比较，把较大(或小)者往后位放（或往前位放）直到把最大(或最小者)者放到最后一个位置上（第一个位置上）
     * 接着按照序位号依次再按照相同逻辑比较出第二大者（第二小者），放到倒数第二个位置上（第二个位置上），依次。。。
     */
    public void bubbleSort() {
        int temp = 0;
        for(int i =arr.length-1;i>0;i--){
            boolean flag=true;
            for(int j=0;j<i;j++){
                if(arr[j]>arr[j+1]){
                    temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                    flag = false;
                }
            }
            if(flag){
                break;
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

    public static void main(String[] args){
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.test();
        bubbleSort.bubbleSort();
        bubbleSort.test();
    }


}
