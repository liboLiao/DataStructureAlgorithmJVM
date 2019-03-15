public  class DeadLoopClass {
    static int i;
    static {
        // 如果不加上这个if语句，编译器将提示“Initializer does not complete normally”并拒绝编译
//        if (true) {
//            System.out.println(Thread.currentThread() + "init DeadLoopClass");
//            while (true) {
//            }
//        }
        System.out.println(i++);
    }
}

