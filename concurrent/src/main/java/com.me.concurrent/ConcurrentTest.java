package com.me.concurrent;

/**
 * Created by llb on 2018/9/15.
 */
public class ConcurrentTest {

    static class Thread1 extends Thread {
        Object o = new Object();

        @Override
        public void run() {
//            try {
//                sleep(300);//晚点获取this锁
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            synchronized (this) {
//            synchronized (o) {
                System.out.println("A begin " + System.currentTimeMillis());
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A end " + System.currentTimeMillis());
            }
        }
    }

    static class Thread2 extends Thread {
        Object o = new Object();

        @Override
        public void run() {
            synchronized (this) {
                System.out.println("A begin " + System.currentTimeMillis());
                try {
                    wait(5000);//wait是释放锁的
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A end " + System.currentTimeMillis());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("main begin " + System.currentTimeMillis());
//        test1();
        test2();
        System.out.println("main end " + System.currentTimeMillis());
    }

    private static void test1() {
        Thread1 thread = new Thread1();
        thread.start();
        try {
            thread.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("test1 Interrupted end " + System.currentTimeMillis());
        }
    }

    private static void test2() {
        Thread2 thread = new Thread2();
        thread.start();
        try {
            thread.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("test1 Interrupted end " + System.currentTimeMillis());
        }
    }

}
