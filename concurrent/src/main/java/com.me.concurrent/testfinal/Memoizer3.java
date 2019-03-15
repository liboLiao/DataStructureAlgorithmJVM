package com.me.concurrent.testfinal;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by llb on 2018/9/19.
 */
public class Memoizer3 <A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;

    public Memoizer3(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(final A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if (f == null) {
            Callable<V> eval = new Callable<V>() {
                public V call() throws InterruptedException {
                    return c.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<V>(eval);
            f = ft;
            cache.put(arg, ft);
            ft.run();
        }
        try {
            return f.get();
        }catch (ExecutionException e) {
//            throw LaunderThrowable.launderThrowable(e.getCause());
        }
        return null;
    }
}
