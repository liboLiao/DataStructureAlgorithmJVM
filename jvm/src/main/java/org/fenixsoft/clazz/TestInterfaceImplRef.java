package org.fenixsoft.clazz;

/**
 * Created by llb on 2019/1/15.
 */
public class TestInterfaceImplRef implements TestInterfaceRef {
    public TestInterfaceImplRef ref = this;
    public void test() {
        System.out.println("");
    }
}
