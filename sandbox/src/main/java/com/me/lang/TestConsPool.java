package com.me.lang;

/**
 * Created by llb on 2018/10/11.
 */
public class TestConsPool {

    public static void main(String[] args) {
//        System.out.println("hahaha".hashCode());
//        String s = new String("hahaha");
//        System.out.println(s.hashCode());
//        String s1 = new String("hahaha");
//        System.out.println(s1.hashCode());
//        System.out.println(s1.intern().hashCode());


        StringBuilder sb = new StringBuilder();
        sb.append('h').append('a').append('h').append('a').append('h').append('a');
        String s = sb.toString();
        System.out.println(s.hashCode());

//        System.out.println("hahaha".hashCode());

        String s1 = s.intern();
        System.out.println(s1.hashCode());

        System.out.println("hahaha".hashCode());
    }
}
