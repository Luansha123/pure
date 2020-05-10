package com.sc.jvm.classloader;

public class Test6 {

    public static void main(String[] args) {
//        System.out.println(System.getProperty("sun.boot.class.path"));
//        System.out.println(System.getProperty("java.ext.dirs"));
//        System.out.println(System.getProperty("java.class.path"));

        System.out.println("------------------------");

        System.out.println(MyClassLoader.class.getClassLoader());
        System.out.println(Test6.class.getClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(System.getProperty("java.system.class.path"));



    }
}
