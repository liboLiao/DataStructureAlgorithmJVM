package com.me.concurrent.testfinal;

import org.junit.Test;

/**
 * Created by llb on 2018/9/28.
 */
public class TestVolatile {

    volatile int[] arr ;

    volatile int test;

    public static void main(String[] args) throws Exception {
        new TestVolatile().testVolatile();
    }

    int read() {
        return test;
    }

    void write(int t) {
        test = t;
    }

    public void testVolatile() {
        new Thread(new Runnable() {
            public void run() {
                int temp = -1;
                while (true) {
                    if (test != temp) {
                        System.out.println(test);
                        temp = test;
                    }
                }
            }
        }).start();
        Runnable runnable3 = new Runnable() {
            public void run() {
                while (true){
                    write(3);
                }
            }
        };
        Runnable runnable5 = new Runnable() {
            public void run() {
                while (true){
                    write(5);
                }
            }
        };
        new Thread(runnable3).start();
        new Thread(runnable5).start();
    }

}
