package com.me.concurrent.testfinal;

/**
 * Created by llb on 2019/1/24.
 * 对于final域，编译器和处理器要遵守两个重排序规则。
 1）在构造函数内对一个final域的写入，与随后把这个被构造对象的引用赋值给一个引用
 变量，这两个操作之间不能重排序。
    11）JMM禁止编译器把final域的写重排序到构造函数之外。
    12）编译器会在final域的写之后，构造函数return之前，插入一个StoreStore屏障。这个屏障
    禁止处理器把final域的写重排序到构造函数之外。
 2）初次读一个包含final域的对象的引用，与随后初次读这个final域，这两个操作之间不能
 重排序。
    21）编译器会在读final域操作的前面插入一个LoadLoad屏障。
 *
 * 本例final域为一个引用类型，它引用一个int型的数组对象。对于引用类型，写final域的重
 排序规则对编译器和处理器增加了如下约束：在构造函数内对一个final引用的对象的成员域
 的写入，与随后在构造函数外把这个被构造对象的引用赋值给一个引用变量，这两个操作之
 间不能重排序。
 */
public class FinalReferenceExample {
    final int[] intArray; // final是引用类型
    static FinalReferenceExample obj;
    public FinalReferenceExample() { // 构造函数
        intArray = new int[1]; // 1
        intArray[0] = 1; // 2
    }
    public static void writerOne () { // 写线程A执行
        obj = new FinalReferenceExample (); // 3
    }
    public static void writerTwo () { // 写线程B执行
        obj.intArray[0] = 2; // 4
    }
    public static void reader () { // 读线程C执行
        if (obj != null) { // 5
            int temp1 = obj.intArray[0]; // 6
        }
    }
}
