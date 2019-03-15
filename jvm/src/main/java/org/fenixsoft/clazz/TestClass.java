package org.fenixsoft.clazz;

import java.util.Collections;

/**
 * 常量池中CONSTANT_Fieldref_info、CONSTANT_Method_info、CONSTANT_InterfaceMethodRef_info等类型的常量
 * 都会有一项Class_index属性，它指向一个CONSTANT_Class_info类型的常量，该常量描述了类全限定名
 * 还有CONSTANT_MethodType_info类型、CONSTANT_MethodHandle_info、CONSTANT_InvokeDynamic_info
 * 这3种和jdk1.7新增动态语言特性相关
 */
public class TestClass<Tc> {//Class级的属性列表里有签名属性
    //准备阶段赋值为0，初始化阶段在<clinit>方法中赋值123
    //CONSTANT_Fieldref_info : Class name : cp_info_#17 <org/fenixsoft/clazz/TestClass>
    public static int value = 123;
    //编译期赋值123在Class文件中
    //无CONSTANT_Fieldref_info类型常量，值已经编译在value1字段表的constant属性里
    public final static int value1 = 123;
    //CONSTANT_Fieldref_info : Class name : cp_info_#17 <org/fenixsoft/clazz/TestClass>
    private int m;
    //在Class文件中，对应的字段表含有签名属性
    //CONSTANT_Fieldref_info : Class name : cp_info_#17 <org/fenixsoft/clazz/TestClass>
    private Tc t;

    //以下测试字段的解析
    //引用类型如果在本类中没有使用（赋值语句等），那么就不会在常量池里编译出
    // CONSTANT_Fieldref_info和CONSTANT_Class_info信息
    private TestInterfaceRef testInterfaceRef;
    //有使用（赋值语句等），在常量池里就会有CONSTANT_Fieldref_info和CONSTANT_Class_info信息
    //CONSTANT_Fieldref_info : Class name : cp_info_#17 <org/fenixsoft/clazz/TestClass>
    private TestInterfaceRef testInterfaceRef1 = new TestInterfaceImplRef();
    //CONSTANT_Fieldref_info : Class name : cp_info_#17 <org/fenixsoft/clazz/TestClass>
    //"一二三"和"一二三四"字符串对象的引用被维护在虚拟机的一张（intern）内部列表里
    private String testStringRef = "一二三";
    private String testStringRef2 = "一二三四";
    //CONSTANT_Fieldref_info : Class name : cp_info_#17 <org/fenixsoft/clazz/TestClass>
    private String testStringRef1 = new String("abc");


    //编译后code里都是去泛型信息的,但会在方法表、相关字段表、
    // Class级的属性列表里增加一项Signature属性，
    // Signature属性记录有泛型信息
    public <Pm> int inc(Pm pt,Tc tc) {
        //已经去了泛型信息
        this.t = tc;
        //在本类中有了使用（赋值语句等），在常量池里就会有CONSTANT_Fieldref_info和CONSTANT_Class_info信息
        //CONSTANT_Fieldref_info : Class name : cp_info_#17 <org/fenixsoft/clazz/TestClass>
        //1.    new #2 <org/fenixsoft/clazz/TestInterfaceImplRef>
        //#2 : CONSTANT_Class_info : Class name : cp_info_#58 <org/fenixsoft/clazz/TestInterfaceImplRef>
        //2.    invokespecial #3 <org/fenixsoft/clazz/TestInterfaceImplRef.<init>>
        //#3 : CONSTANT_MethodRef_info : Class name : cp_info_#2 <org/fenixsoft/clazz/TestInterfaceRef>
//        this.testInterfaceRef = new TestInterfaceImplRef();

        //引用其他类实例的字段并赋值，
        /**
         6 new #2 <org/fenixsoft/clazz/TestInterfaceImplRef> #2 : 同上
         9 dup
         10 invokespecial #3 <org/fenixsoft/clazz/TestInterfaceImplRef.<init>> #3 : 同上
         13 getfield #12 <org/fenixsoft/clazz/TestInterfaceImplRef.ref> 生成一个要引用的CONSTANT_FiledRef_info#12
         #12 : CONSTANT_FiledRef_info : Class name : cp_info_#58 <org/fenixsoft/clazz/TestInterfaceImplRef>
         16 putfield #13 <org/fenixsoft/clazz/TestClass.testInterfaceRef>
         #13 : CONSTANT_FiledRef_info : Class name : cp_info_#17 <org/fenixsoft/clazz/TestClass>
         */
        this.testInterfaceRef = new TestInterfaceImplRef().ref;


       //在本类中有了使用testInterfaceRef，在常量池里就会有CONSTANT_Fieldref_info和CONSTANT_Class_info信息
        //还会出现CONSTANT_InterfaceMethodRef_info(含有ClassIndex)
        // invokeinterface #13 <org/fenixsoft/clazz/TestInterfaceRef.test>
        //#13 : CONSTANT_InterfaceMethodRef_info : Class name : cp_info_#68 <org/fenixsoft/clazz/TestInterfaceRef>
        testInterfaceRef.test();


        //此代码会在常量池里出现相应的CONSTANT_Method_info和CONSTANT_Class_info信息
        // invokestatic #13 <java/util/Collections.sort>
        //#13 : CONSTANT_Method_info : Class name : cp_info_#70 <java/utils/Collections>
        Collections.sort(null);

        //下面测试Class文件中的异常处理表
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
