package com.ne;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by llb on 2019/1/16.
 */
public class DynamicProxyTest {
    interface IHello{
        void sayHello();
    }

    static class Hello implements IHello {
        @Override
        public void sayHello() {
            System.out.println("hello proxy!");
        }
    }

    static class DynamicProxy implements InvocationHandler{

        Object src;
        Object bind(Object src){
            this.src = src;
            return Proxy.newProxyInstance(src.getClass().getClassLoader(),new Class[]{IHello.class},this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("welcome ");
            return method.invoke(src,args);
        }
    }

    public static void main(String[] args) throws Throwable {
        //把生成的代理类持久化到本地文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        IHello hello = (IHello)new DynamicProxy().bind(new Hello());
        hello.sayHello();
    }
}
