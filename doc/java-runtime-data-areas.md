##. Java runtime data areas [https://docs.oracle.com/javase/specs/jvms/se14/html/jvms-2.html#jvms-2.5](https://docs.oracle.com/javase/specs/jvms/se14/html/jvms-2.html#jvms-2.5)

Java 虚拟机定义了多种运行时数据区域，在执行程序的过程中会用到这些运行时数据区域。其中的一些运行时数据区域在虚拟机启动的时候创建，在虚拟机退出的时候销毁。另一些运行时数据区域是每个线程独有的，在线程创建的时候创建这些区域，在线程退出的时候销毁。

## 线程相关的。
### pc register 程序计数器，用于存放当前执行的非 native 方法的指令的地址。[https://docs.oracle.com/javase/specs/jvms/se14/html/jvms-2.html#jvms-2.5.1](https://docs.oracle.com/javase/specs/jvms/se14/html/jvms-2.html#jvms-2.5.1)

Java 虚拟机支持多个线程同时执行。每个线程都有自己的程序计数器。在任何时刻一个线程只能执行一个方法，这个方法就是该线程的当前方法。如果当前方法不是 native 方法，程序计数器保存的是当前正在执行的 java 虚拟机指令的地址。如果线程当前执行的方法是 native 方法，程序计数器中的值是未定义的。程序计数器的宽度用来保存返回地址或者特定平台的 native 指针足够了。

### java virtual machine stacks
 
每个 Java 线程都有一个 Java 虚拟机栈，Java 虚拟机栈是在线程创建的时候创建的。Java 虚拟机栈上保存着栈帧(frame)。Java 虚拟机栈和常见语言的栈是类似的，比如和 C 语言的：栈上保存着局部变量和中间结果，参与了方法的调用和返回过程。不能直接操作 Java 虚拟机栈（大部分的栈都只能入栈、出栈），只能对帧进行入栈和出栈，帧可能是从堆上分配的。Java 虚拟机栈的内存可以是不连续的。

在第一版的《Java 虚拟机规范》中，Java 虚拟机栈叫 Java 栈。

Java 虚拟机规范允许 Java 虚拟机栈是固定大小的或者是通过计算来按需扩展和收缩。如果 Java 虚拟机栈的大小是固定的，每个 Java 虚拟机栈的大小在创建的时候或许可以是独立选择的。

Java 虚拟机在实现的时候或许可以为程序员或者用户来控制 Java 虚拟机栈的大小。同样的，如果是可以动态扩展和收缩的 Java 虚拟机栈也可以设置其最大、最小大小。

下列异常状态和 Java 虚拟机栈相关:
* 经过计算后发现线程需要的 Java 虚拟机栈超过了允许的最大深度，Java 虚拟机会抛出栈溢出错误 StackOverflowError 。
* 如果 Java 虚拟机栈可以动态扩展，在尝试扩展的时候如果没有足够的内存来扩展，或者在创建线程的时候没有足够的内存来初始化 Java 虚拟机栈，Java 虚拟机便会抛出 OutOfMemoryError 。
## 2. 所有线程共享的。
1. ### 堆
Java 虚拟机只有一个堆，所有线程都共享这个堆。堆是运行时数据区，所有类的实例、数组内存都是从堆上分配的。

堆是在 Java 虚拟机启动的时候创建的，对象的堆存储空间通过自动存储管理系统（垃圾回收器）来回收的。对象从来不会显式回收。Java 虚拟机不会假定一种特定的垃圾回收器，可以根据实现者的系统要求来选择存储管理技术。

堆也许是固定大小的或者也许是通过计算按需动态扩展和如果大堆不再需要的话还可以收缩堆。堆内存也可以不连续。

Java 虚拟机在实现的时候或许可以为程序员或者用户来控制堆的大小。同样的，如果是可以动态扩展和收缩的堆也可以设置堆的最大、最小大小。

下列异常状态和堆相关:
垃圾回收系统回收的堆空间不够所需的时候，Java 虚拟机会抛出 OutOfMemoryError 错误。

2. ### 方法区
Java 虚拟机有个方法区，所有的线程都共享方法区。

方法区和常规语言编译后的存储区是相似的，或者是和操作系统进程中的文本段是相似的。方法区保存了每个类的结构，比如运行时常量池、字段和方法数据和普通方法、构造方法、包括用于类和接口初始化的方法、实例的初始化方法。

方法区是在虚拟机启动的时候创建的。尽管方法区在逻辑上是堆的一部分，简单的实现或许不会对这部分区域进行垃圾回收或者收缩。Java 虚拟机规范不会强制要求方法区的位置或者管理编译后代码的策略。方法区或许是固定大小的，或许是可以根据计算按需扩展，如果不再需要大块的方法区还可以收缩。方法区的内存可以是不连续的。

Java 虚拟机在实现的时候或许可以为程序员或者用户来控制方法区的大小。同样的，如果是变长大小的方法区的话也可以设置方法区的最大、最小大小。

下列的异常状态和方法区相关：

如果方法区中中内存不够请求需要分配的，Java 虚拟机会抛出 outOfMemoryError 。

3. ### 运行时常量池

运行时常量池是每个类或者接口在运行时字节码文件中常量表的一种表示运行时常量池包含几种常量，从编译期的数值型的字面量到运行时解析的方法和字段的引用。运行时常量池的功能和常规语言中的符号表的功能是类似的，尽量运行时常量池包含的数据比典型的符号表更宽泛。

运行时常量池是从方法区中分配的，一个类或接口对应的运行时常量池是在类或接口创建的时候被虚拟机构造的。

下列的异常情况和一个类或者接口的运行时常量池的构造相关：

在创建类或接口的时候，如果方法区的内存不够构建运行时常量池所需的时候，Java 虚拟机会抛出 OutOfMemoryError 。
看 [§5 (Loading, Linking, and Initializing)](https://docs.oracle.com/javase/specs/jvms/se14/html/jvms-5.html) 来了解构建运行时常量池的信息。

4. ### 本地方法栈
在 Java 虚拟机实现的时候或许用常规的栈，通俗的说就是"C栈"，来支持本地方法（用除了 Java 语言之外的其他语言写得方法）。 Native method stacks may also be used by the implementation of an interpreter for the Java Virtual Machine's instruction set in a language such as C. 
Java 虚拟机的实现不能加载本地方法，Java 虚拟机的实现不依赖常规栈，也就不需要提供本地方法栈。如果提供了的话，每个线程在创建的时候会分配本地方法栈。

Java 虚拟机规范允许本地方法栈是固定大小的，或者是按需计算动态扩展或收缩的。如果本地方法栈是固定大小的，每个本地方法栈在创建的时候其大小可以是独立选择的。

Java 虚拟机在实现的时候或许可以为程序员或者用户来控制本地方法栈的初始大小。同样的，如果是变长大小的本地方法栈的话也可以设置本地方法栈的最大、最小大小。

下列异常情况和本地方法栈相关：
经过计算一个线程需要的本地方法栈比允许的还要大的时候，Java 虚拟机会抛出 StackOverFlowError 错误。

如果本地方法栈是可以动态扩展的，在尝试扩展的过程中没有足够的内存可用的话，或者是在创建线程的时候没有足够的内存来创建初始化本地方法栈，Java 虚拟机会抛出 OutOfMemoryError 错误。

5. ### 栈帧

栈帧是用来存储数据、中间结果的，也包括执行动态链接、方法的返回值、异常的转发。

每次在方法调用的时候会创建新的栈帧，方法调用结束的时候栈帧会销毁，不管是正常结束还是意外结束（抛出未捕获的异常）。栈帧是在创建这个栈帧的线程的 Java 虚拟机栈上分配的。每个栈帧都有其局部变量数组、操作数栈、指向当前方法所在的类的运行时常量池的引用。

栈帧也许还会扩展实现一些额外的特定实现的信息，比如调试信息。

局部变量数组和操作数栈的大小是在编译期由伴随着和栈帧关联的方法的代码决定的。因此栈帧的数据结构的大小唯一依赖的是 Java 虚拟机的实现，这些数据结构的内存可以在方法调用的同时去分配。在一个给定的活动线程中任意时刻只有当前执行的方法对应的栈帧是激活的。当前执行的方法所在的类被定义为当前类。 Operations on local variables and the operand stack are typically with reference to the current frame.

当前栈帧有两种方式可以成为非当前栈帧，一种是当前栈帧所属的方法调用了另外一个方法，另一种是当前栈帧所属的方法结束了。当方法被调用的时候，一个新的栈帧被创建了，当控制权转移到这个方法的时候这个刚创建的栈帧就是当前栈帧了。当方法返回的时候，如果有返回值的话，当前栈帧会把其所属的方法调用的结果传回给前一个栈帧。当前栈帧被废弃了，之前的栈帧就成为了当前栈帧。

注意线程创建的栈帧是该线程局部的，其他线程不能引用该栈帧。

6. ### 局部变量
每个栈帧都包含一个变量数组，这些变量就是方法的局部变量。局部变量数组的长度在编译期由伴随着和栈帧关联的方法的代码决定的，并保存在一个类或者接口的二进制表示中。

一个单独的变量可以持有一个 boolean、 byte、 char、 short、 int、 float、 reference、 或者是一个 returnAddress 类型的值 。一对儿局部变量可以持有一个 long 或者 double 类型的值。

局部变量是通过索引来确定的。第一个局部变量的索引是 0 ，局部变量数组的索引是一个 integer 类型的值，这个值的取值范围只能是 [0, 局部变量数组长度 -1 ] 。

long 或者 double 类型的值占用两个连续的局部变量。这样的一个值大概只能通过通过较小的索引来确定。例如，一个 double 类型的值保存在局部变量数组中，索引 n 实际占用了索引是 n 和 n+1 这两个变量。然后，位于索引为 n+1 的局部变量是不可以通过索引 n+1 来访问到的。n+1 索引所在的变量可以被存进去。然而，这样做了之后就会让变量 n 的内容作废了。

Java 虚拟机没有要求 n 为偶数。凭直觉，long 和 double 的值没必要在局部变量数组中进行 64 位对齐。虚拟机的实现者可以自由使用合适的方式来用两个局部变量来保存一个 long 或者 double 的值。

在方法调用的时候 Java 虚拟机使用局部变量来传参的。在类方法调用的时候，所有的参数在传递的时候都是从 0 开始的连续的局部变量。在实例方法调用的时候，局部变量 0 用于传递一个引用（在 Java 语言里就是 this 引用），这个引用指向的对象调用了这个实例方法。其他的所有参数接着从局部变量 1 开始连续传递。

7. ### 操作数栈
每个栈帧都包含一个后进先出的栈，这个栈就是操作数栈。栈帧的操作数栈的最大深度是在编译期由栈帧关联的方法的代码决定的。

在上下文明确的前提下，我们把当前栈帧的操作数栈简称为操作数栈。

栈帧上的操作数栈在被创建的时候是空的。Java 虚拟机提供指令加载常量或者局部变量或者字段到操作数栈。操作数栈也用于准备参数传递给方法并且接收参数的结果。

例如，指令 iadd 把两个整型值加在一起。Java 虚拟机要求在相加之前，两个被加的整型值要被放在栈顶，先前的指令会把这两个整型值放到栈顶。两个整型值都从操作数栈出栈了。这两个整型值相加后，相加的和又被压入了操作数栈了。
Subcomputations may be nested on the operand stack, resulting in values that can be used by the encompassing computation.

操作数栈上的每个 entry 都可以持有一个 Java 虚拟机的任何类型的值，包括 long 或 double 类型的值。

操作数栈上的值在操作的时候需要和其类型相适应。例如，不可能把两个 int 类型的值入栈之后再接着把它们当做 long 或者把两个 float 类型的值入栈后再接着用 iadd 指令把他们相加。只有少部分的 Java 虚拟机指令操作运行时数据区的时候用 raw 而不管特定的类型。这些指令在定义的时候就决定了他们不能修改或者打破完整的值。在操作数栈上的这些操作约束在验证字节码的时候会强制检查的。

在任意时刻，操作数栈都有其栈深度。long 或者 double 类型的值占用了两个单位的深度，其他任意类型的值都只占用了一个单位的深度。

8. ### 动态链接




9. ### 对象的表示
Java 虚拟机不会对对象的内部结构有强制的特定要求。

在 Oracle 实现的一些 Java 虚拟机中，指向类实例的引用是一个指针。这个指针指向了一个 handle 。这个 handler 又由两个指针组成，一个指针指向了一个表，这个表包含了引用对象的方法和一个指针，这个指针又指向了一个 class 对象，这个 class 对象代表了引用对象的类型。另一个指针指向了内存，这块内存就是从堆上给对象分配出的数据。












参考链接[https://javapapers.com/core-java/java-jvm-run-time-data-areas/](https://javapapers.com/core-java/java-jvm-run-time-data-areas/)
[http://java8.in/java-virtual-machine-run-time-data-areas/](http://java8.in/java-virtual-machine-run-time-data-areas/)
[https://www.netjstech.com/2017/10/jvm-run-time-data-areas-java-memory-allocation.html](https://www.netjstech.com/2017/10/jvm-run-time-data-areas-java-memory-allocation.html)
[https://blog.tomandersen.cn/2020/02/16/Java%E4%B8%AD%E5%AE%9E%E4%BE%8B%E5%88%9D%E5%A7%8B%E5%8C%96%E6%96%B9%E6%B3%95-init-%E5%8E%9F%E7%90%86%E8%A7%A3%E6%9E%90/](https://blog.tomandersen.cn/2020/02/16/Java%E4%B8%AD%E5%AE%9E%E4%BE%8B%E5%88%9D%E5%A7%8B%E5%8C%96%E6%96%B9%E6%B3%95-init-%E5%8E%9F%E7%90%86%E8%A7%A3%E6%9E%90/)
[https://www.javaworld.com/article/3040564/java-101-class-and-object-initialization-in-java.html](https://www.javaworld.com/article/3040564/java-101-class-and-object-initialization-in-java.html)


疑问:
https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html#jls-17.5

https://answersdrive.com/what-is-native-method-stack-in-java-5572972
https://howtodoinjava.com/spring-boot-tutorials/
https://www.baeldung.com/spring-boot-start
https://www.journaldev.com/7969/spring-boot-tutorial
https://www.tutorialspoint.com/spring_boot/spring_boot_application_properties.htm
https://docs.oracle.com/javase/specs/jvms/se14/html/jvms-5.html

https://beginnersbook.com/2013/04/java-static-dynamic-binding/

http://slurp.doc.ic.ac.uk/pubs/observing/linking.html











