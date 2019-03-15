package com.me.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by llb on 2019/1/28.
 */
public class FairAndUnfairTest {
    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unfairLock = new ReentrantLock2(false);

    @Test
    public void fair() {
        testLock(fairLock);
    }

    @Test
    public void unfair() {
        testLock(unfairLock);
    }

    private void testLock(Lock lock) {
        // 启动5个Job（略）
        for (int i = 0; i < 5; i++) {
            new Job(lock).start();
        }
    }

    private static class Job extends Thread {
        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        public void run() {
            // 连续2次打印当前的Thread和等待队列中的Thread（略）
            lock.lock();
            try {
                System.out.println("Locked by [" + Thread.currentThread().getId() + "]," +
                        "Waiting by -"+((ReentrantLock2)lock).getQueuedThreadIds());
            } finally {
                lock.unlock();
            }

            lock.lock();
            try {
                System.out.println("Locked by [" + Thread.currentThread().getId() + "]," +
                        "Waiting by ="+((ReentrantLock2)lock).getQueuedThreadIds());
            } finally {
                lock.unlock();
            }
        }
    }

    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public List<Long> getQueuedThreadIds() {
            List<Long> list = new ArrayList<Long>();
            List<Thread> arrayList = new ArrayList<Thread>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            for (Thread t : arrayList){
                list.add(t.getId());
            }
            return list;
        }

        public static void swap(List<?> list, int i, int j) {
            final List l = list;
            l.set(i, l.set(j, l.get(i)));
        }
    }
}
