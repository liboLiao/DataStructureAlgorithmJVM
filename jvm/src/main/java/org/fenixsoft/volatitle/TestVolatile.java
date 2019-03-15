package org.fenixsoft.volatitle;

/**
 * Created by llb on 2019/1/19.
 * java -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -Xcomp -XX:CompileCommand=dontinline,*TestVolatile.setValue -XX:CompileCommand=compileonly,*TestVolatile.setValue org.fenixsoft.volatitle.TestVolatile
 http://g.oswego.edu/dl/jmm/cookbook.html
 */
public class TestVolatile {
    static volatile int value;

    public void setValue() {
        value = 10;
    }

    public static void main(String[] args) {
        value = 10;
    }
}
