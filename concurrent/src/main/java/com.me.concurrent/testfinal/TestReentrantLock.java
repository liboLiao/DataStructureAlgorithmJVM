package com.me.concurrent.testfinal;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by llb on 2018/9/27.
 */
public class TestReentrantLock {

    public static void main(String[] args) throws Exception {
        new TestReentrantLock().testReentrantLock();
    }

    @Test
    public void testReentrantLock(){
        TestRuunnable testRuunnable = new TestRuunnable();
        new Thread(testRuunnable).start();
        new Thread(testRuunnable).start();
    }

    class TestRuunnable implements Runnable{
        ReentrantLock reentrantLock = new ReentrantLock();
        public void run() {
            reentrantLock.lock();
            try {
                System.out.println("TestRuunnable...");
                try {
                    Thread.currentThread().sleep(1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                System.out.println("TestRuunnable end.");
                reentrantLock.unlock();
            }
        }
    }
}
