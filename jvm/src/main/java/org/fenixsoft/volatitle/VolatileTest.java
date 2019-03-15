package org.fenixsoft.volatitle;

/**
 * Created by llb on 2019/1/19.
 */
public class VolatileTest {


    public static volatile int race = 0;

    public static void increase() {
        race++;
    }

    private static final int THREADS_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        increase();
                        if(i==9999){
                            System.out.println(Thread.currentThread().getId()+" : "+ i);
                        }
                    }
                }
            });
            threads[i].start();
        }

        // 等待所有累加线程都结束
        while (Thread.activeCount() > 1){
            try {
                Thread.sleep(5);
                System.out.println("sleep 5 ms,"+Thread.currentThread().getThreadGroup().activeCount());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//            Thread.yield();

        System.out.println("race : "+race);
    }

}
