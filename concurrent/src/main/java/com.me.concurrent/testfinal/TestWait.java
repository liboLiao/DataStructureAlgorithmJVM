package com.me.concurrent.testfinal;

import org.junit.Test;

/**
 * Created by llb on 2018/9/27.
 */
public class TestWait {
    int count;
    private final Object waiter = new Object();
    public static void main(String[] args) throws Exception {

    }

    @Test
    public void testWait(){
        for(int i=0;i<100;i++){
            new Thread(new Runnable() {
                public void run() {
                    waiting();
                }
            }).start();
        }
    }

   public void waiting(){
       System.out.println(++count);
       try {
//           waiter.wait();
//           wait();
           //IllegalMonitorStateException - if the current thread is not the owner of the object's monitor.
           synchronized (waiter){
               waiter.wait();
           }
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       System.out.println(count + "after");
   }



}
