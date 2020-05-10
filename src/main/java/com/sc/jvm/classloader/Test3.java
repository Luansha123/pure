package com.sc.jvm.classloader;

import java.util.UUID;

/*
    当一个常量值并非编译期可以确定的，那么其值就不会被放入到调用类的常量池中，
    这是在运行程序时，会导致主动使用这个常量所在的类，显然回导致这个类被初始化。
 */
public class Test3 {
    public static void main(String[] args) {
        System.out.println(MyParent3.str2);
    }
}

class MyParent3{

    public static final String str = UUID.randomUUID().toString();
    public static final String str2 = "hello";
    static {
        System.out.println("static code ");
    }
}
