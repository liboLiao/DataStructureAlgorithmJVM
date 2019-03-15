package com.me.concurrent.testfinal;

/**
 * Created by llb on 2018/9/19.
 */
public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
