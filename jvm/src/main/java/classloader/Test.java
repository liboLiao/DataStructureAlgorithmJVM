package classloader;

import java.sql.DriverManager;
import java.util.ServiceLoader;

public class Test {
    public static void main(String[] args) {
        ClassLoader.getSystemClassLoader();
        final ClassLoader contextClassLoader =
                Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader);
        System.out.println(Test.class.getClassLoader());
        System.out.println("Parent ClassLoader : "+Test.class.getClassLoader().getParent());
        System.out.println("ServiceLoader`s ClassLoader : "+ServiceLoader.class.getClassLoader());
        System.out.println("DriverManager`s ClassLoader : "+DriverManager.class.getClassLoader());

        try {
//            Class<ForNamedClass> forNamedClassClass =
//                    (Class<ForNamedClass>) Class.forName("classloader.ForNamedClass", false, null);
            Class<ForNamedClass> forNamedClassClass =
                    (Class<ForNamedClass>) Class.forName("classloader.ForNamedClass", false, Test.class.getClassLoader().getParent());
//            Class<ForNamedClass> forNamedClassClass =
//                    (Class<ForNamedClass>) Class.forName("classloader.ForNamedClass", false, Test.class.getClassLoader());
            System.out.println("");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

