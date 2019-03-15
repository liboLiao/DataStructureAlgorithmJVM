package com.me.concurrent;

/**
 * （instance=new Singleton();）======>
 * memory = allocate();　　// 1：分配对象的内存空间
 * ctorInstance(memory);　 // 2：初始化对象
 * instance = memory;　　  // 3：设置instance指向刚分配的内存地址
 *
 * 重排序后：
 * memory = allocate();　　// 1：分配对象的内存空间
 * instance = memory;　　  // 3：设置instance指向刚分配的内存地址
 * // 注意，此时对象还没有被初始化！
 * ctorInstance(memory);　 // 2：初始化对象
 *
 * DoubleCheckedLocking示例代码的第7行（instance=new Singleton();）如果
 * 发生重排序，另一个并发执行的线程B就有可能在第4行判断instance不为null。
 * 线程B接下来将访问instance所引用的对象，但此时这个对象可能还没有被A线程初始化!
 *
 * 添加上volatile关键字后，可以解决重排序问题
 * JMM内存屏障保守的插入策略：
 * ·在每个volatile写操作的前面插入一个StoreStore屏障。
 * ·在每个volatile写操作的后面插入一个StoreLoad屏障。
 * ·在每个volatile读操作的后面插入一个LoadLoad屏障。
 * ·在每个volatile读操作的后面插入一个LoadStore屏障。
 */
public class DoubleCheckedLocking { // 1
    private static /*volatile*/ Instance instance; // 2
    public static Instance getInstance() { // 3
        if (instance == null) { // 4:第一次检查
            synchronized (DoubleCheckedLocking.class) { // 5:加锁
                if (instance == null) // 6:第二次检查
                    instance = new Instance(); // 7:问题的根源出在这里
            } // 8
        } // 9
        return instance; // 10
    } // 11

    static class Instance{}
}
