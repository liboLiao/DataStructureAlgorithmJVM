package com.me.concurrent.testfinal;

import org.junit.Test;

/**
 * Created by llb on 2018/9/16.
 * Testfinal是不可变对象的要求有三点：
 * 1.它的状态（对象关联的成员属性）不能在创建后被修改,没有提供修改状态的代码途径。
 * 2.所有域都是final的
 * 3.它被正确地创建，即：构造期间没有发生过this引用的逸出
 */
public class Testfinal {
    //final修饰变量預示著變量的引用不可改变,只能赋初值或者构造函数赋值。
    private final AnotherObj anotherObj = new AnotherObj();

//    public Testfinal(AnotherObj anotherObj) {
//        this.anotherObj = anotherObj;
//    }

    public AnotherObj getAnotherObj(){
        return anotherObj;
    }

    @Test
    public void test(){
        Testfinal testfinal = new Testfinal();
        AnotherObj anotherObj = testfinal.getAnotherObj() ;
        anotherObj = new AnotherObj();
        testfinal.getAnotherObj();
    }
}
