package com.me.lang;

import sun.misc.Unsafe;

import java.io.UnsupportedEncodingException;

/**
 * Created by llb on 2018/10/10.
 */
public class TestPrcess {
//    private static final Unsafe unsafe = Unsafe.getUnsafe();
    public static void main(String[] args) {
//        int i = 1/0;
        Class<? extends Class> UnsafeClass = Unsafe.class.getClass();

        TestPrcess testPrcess = new TestPrcess() ;
        try {
            testPrcess.testSome();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void testSome() throws UnsupportedEncodingException {
        byte[] bytes = {(byte) 0xC0, (byte) 0xB1};
        String str = new String(bytes,"UTF-8");
        System.out.println(str.getBytes("UTF-8").length);
    }
}
