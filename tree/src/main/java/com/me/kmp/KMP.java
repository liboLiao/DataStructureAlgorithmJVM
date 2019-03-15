package com.me.kmp;

/**
 * Created by llb on 2018/8/6.
 */
public class KMP {
    @org.junit.Test
    public void test() {
        String str = "ababcabcbababcabacaba";
        String dest = "ababcaba";
        int[] array = kmpNext(dest);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
        System.out.println(kmp(str, dest, array));
    }

    //用来返回next数组//0 0 1 2 0 1 2 3
    //                  a b a b c a b a      i
    //                    a b a b c a b a    j
    public static int[] kmpNext(String dest) {
        int[] next = new int[dest.length()];
        next[0] = 0;
        //开始推出next
        for (int i = 1, j = 0; i < dest.length(); i++) {
            //3
            while (j > 0 && dest.charAt(j) != dest.charAt(i)) {
                j = next[j - 1];//计算出了j前面有多少字符是首尾相同的个数
            }
            //1
            if (dest.charAt(i) == dest.charAt(j)) {
                j++;
            }
            //2
            next[i] = j;
        }

        return next;
    }

    public static int kmp(String str, String dest, int[] next) {
        for (int i = 0, j = 0; i < str.length(); i++) {
            //3
            while (j > 0 && str.charAt(i) != dest.charAt(j)) {//i=7  j=7
                j = next[j - 1];//next[7-1]=2   next[2-1]=0
            }
            if (str.charAt(i) == dest.charAt(j)) {
                j++;
            }
            if (j == dest.length()) {
                return i - j + 1;
            }
        }
        return 0;
    }
}
