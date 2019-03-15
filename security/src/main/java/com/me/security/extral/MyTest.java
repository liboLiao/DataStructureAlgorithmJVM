package com.me.security.extral;

class Singleton {
//    private static Singleton singleton = new Singleton();   //第一种：放在这儿运行结果是counter1=1,counter2=0
    public static int counter1;
    public static int counter2 = 0;
    private static Singleton singleton = new Singleton();   //第二种：放在这儿运行结果是counter1=1,counter2=1

    private Singleton() {
        counter1++;
        counter2++;
    }

    public static Singleton getInstance() {
        return singleton;
    }
}

public class MyTest {
    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        System.out.println("counter1=" + singleton.counter1);
        System.out.println("counter2=" + singleton.counter2);
    }
}
