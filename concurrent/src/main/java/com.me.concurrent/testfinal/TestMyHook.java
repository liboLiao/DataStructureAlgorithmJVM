package com.me.concurrent.testfinal;

/**
 * Created by llb on 2018/9/25.
 */
public class TestMyHook {

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws Exception {
        new Finalizer();
        //启用退出JVM时执行Finalizer
        Runtime.runFinalizersOnExit(true);
        MyHook hook1 = new MyHook("Hook1");
        MyHook hook2 = new MyHook("Hook2");
        MyHook hook3 = new MyHook("Hook3");

        //注册关闭钩子
        Runtime.getRuntime().addShutdownHook(hook1);
        Runtime.getRuntime().addShutdownHook(hook2);
        Runtime.getRuntime().addShutdownHook(hook3);

        //移除关闭钩子
        Runtime.getRuntime().removeShutdownHook(hook3);

        //Main线程将在执行这句之后退出
        System.out.println("Main Thread Ends.");
    }

    static class MyHook extends Thread {
        private String name;
        public MyHook (String name) {
            this.name = name;
            setName(name);
        }
        public void run() {
            System.out.println(name + " Ends.");
        }
        //重写Finalizer，将在关闭钩子后调用
        protected void finalize() throws Throwable {
            System.out.println(name + " Finalize.");
        }
    }

    static class Finalizer{
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("Finalizer Finalize.");
        }
    }
}
