package com.me.concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by llb on 2019/1/25.
 */
public class TestMemorySemantics {
    private volatile int state;
    private int count = 0;

    public static void main(String[] args) {
        final TestMemorySemantics testMemorySemantics = new TestMemorySemantics();
        for (int i = 0; i < 10; i++)
            new Thread(new Runnable() {
                public void run() {
                    int j = 1000;
                    do {
//                        testMemorySemantics.testSynchronized();
                        testMemorySemantics.testSynchronized2();
                    } while (--j > 0);
                }
            }).start();
    }


    private final int getState() {
        return state;
    }

    private final void setState(int newState) {
        state = newState;
    }

    public void testSynchronized() {
        getState();
        System.out.println("ThreadId" + Thread.currentThread().getId() + "-count:" + (++count));
        setState(1);
    }

    private static final Unsafe unsafe;
    private static final long stateOffset;
    private ThreadLocal threadLocal = new ThreadLocal();

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
            stateOffset = unsafe.objectFieldOffset(TestMemorySemantics.class.getDeclaredField("state"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    private final boolean compareAndSetState(int expect, int update) {
        // See below for intrinsics setup to support this
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    class Node {
        Thread thread;
    }

    private final void getStateLock() {
        //获取state，并检查
        while (!compareAndSetState(0, 1)) {//锁被占用
           /* try {
                Node node = new Node();
                threadLocal.set(node);
                node.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            Node node = new Node();
            node.thread = Thread.currentThread();
            //node入队 和出队要同步起来

            //阻塞未获取到锁的线程
            LockSupport.park(node);
        }

        //如果对列中有该线程对应的node，则该node出队，和入队要同步起来

        //[[[实现起来，没那么简单，还是乖乖地看Doug Lea的j.u.c吧]]]
    }

    private final void setStateLock(int newState) {
        state = newState;
//        ((Node) threadLocal.get()).notify();

        //唤醒队列里其他阻塞的线程
//        for(){//解决threadLocal集合的插入和获取的并发性
//            LockSupport.unpark(Thread.currentThread());
//        }
    }

    public void testSynchronized2() {
        getStateLock();
        System.out.println("ThreadId-" + Thread.currentThread().getId() + "-count:" + (++count));
        setStateLock(0);
    }
}
