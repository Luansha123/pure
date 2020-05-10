package com.sc.jvm.classloader;

/*
    -XX:+<option> 表示开启option选项      -XX:+TraceClassLoading
    -XX:-<option> 表示关闭option选项
    -XX:<option>=value 表示将option选项的值设置为value
 */

public class Test1 {

    public Test1() {
        System.out.println("class Test1 is loaded by :" + this.getClass().getClassLoader());
    }
    public static void main(String[] args) {
        System.out.println(MyChild1.str2);
    }
}

class MyParent1{
    public static String str = "parent static attr";
    static {
        System.out.println("My parent static block");
    }
}

class MyChild1 extends MyParent1{
    public static String str2 = "child static attr";
    // public static String str2 = "child static attr";
    static {
        System.out.println("My child static block");
    }
}