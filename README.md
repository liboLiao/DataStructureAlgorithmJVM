# DataStructureAlgorithmJVM
项目如其名

这里面有算法有：
回溯、分治、动态规划；
关于图结构的Dijkstra、Floyd、Kruskal、Prim；
KMP；
一些排序算法：冒泡排序、堆排序、插入排序、归并排序、快速排序、选择排序、希尔排序
AStar算法

还有关于jvm的一些测试用例：
sandbox子模块测试：
1.类加载的双亲委托模型；
2.static修饰的类变量在准备阶段赋初始值（例如int型赋初值0），在初始化阶段的<clinit>类方法中才真正赋值；
3.final static修饰的类变量在编译期已经赋值在Class文件中
  
nio子模块测试：
MappedByteBuffer使用

methodhandle子模块测试：
1.MethodHandle的使用；
2.Proxy生成的动态代理类是什么样的。

jvm子模块测试：
《深入理解java虚拟机》这本书中的例子，
可能包括的有：ClassLoader的体系、使用Intellij的插件show the bytecode of the class、java多态中的分派机制、volatile语义测试等等。

concurrent子模块：
测试java并发包中的一些锁

