package com.ne;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 *
 */

public class MethodHandleTest {

    public MethodHandle getHandler() {
        MethodHandle mh = null;
        MethodType mt = MethodType.methodType(String.class, int.class, int.class);
        MethodHandles.Lookup lk = MethodHandles.lookup();

        try {
            mh = lk.findVirtual(String.class, "substring", mt);
//            mh = lk.findVirtual(String.class, "indexOfSupplementary", MethodType.methodType(int.class, int.class, int.class));
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return mh;
    }

    public static void main(String[] args) throws Throwable {
        MethodHandle mh = new MethodHandleTest().getHandler();
        String str = "hello world";

        Object result1 = mh.invoke(str, 1, 3);
        Object result2 = mh.invoke(str, new Integer(1), 3);
        Object result3 = (String) mh.invokeExact(str, 1, 3);
//      Object result2 = mh.invokeExact(str, new Integer(1), 3); 
        /**
         * 上面这句方法执行时报错,因为方法类型为String.class, int.class, int.class
         * 而返回的类型为Object，与声明中为String不符合
         * 其中第二个参数类型为Integer，与声明中为int不符合，则类型适配不符合，系统报错。
         */

        System.out.println("result 1:" + result1);
        System.out.println("result 1:" + result2);
        System.out.println("result 1:" + result3);
    }
}