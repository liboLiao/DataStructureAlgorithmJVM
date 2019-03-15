package org.fenixsoft.clazz;

/**
 * Created by llb on 2018/10/12.
 * 测试class文件中的相关描述，字段表结构，属性表结构等
 */
public class TestClass<Tc> {
    public static int value = 123;//准备阶段赋值为0，初始化阶段在<clinit>方法中赋值123
    public final static int value1 = 123;//编译期赋值123在Class文件中
    private int m;
    private Tc t;
    private TestInterfaceRef testInterfaceRef;

    public <Pm> int inc(Pm pt,Tc tc) {
        this.t = tc;
        int x;
        try {
            x = 1;
            return x;
        } catch (Exception e) {
            x = 2;
            return x;
        } finally {
            x = 3;
        }
    }

}
