package com.sc.jvm.classloader;

import java.io.*;
import java.util.Properties;

public class Test2 {
    public static void main(String[] args) {
        System.out.println(MyParent2.f);
    }
}

/*
反编译一个javal类：
    1. 进入到该类的target/classes目录
    2. 执行命令：javap -c com.sc.jvm.classloader.Test1
*/
class MyParent2{
    /*
    助记符：
        ldc:将int,float或是String类型的常量值从常量池中推送至栈顶
        bipush: 将单字节（-128 - 127）的常量值推送至栈顶
        sipush: 将一个短整型常量值（-32768 - 32767 ）推送至栈顶
        iconst_1： 将int类型1推送至栈顶，（额外的，当值是0, 1,2,3,4,5时，直接为iconst_n;
            当值是-1时，为iconst_m1）(m表示minus，零下),
            如上面的short s = 1时，显示的是iconst_1,而非bipush
     */
    //
    public static final String str = "parent static attr"; // ldc
    public static final float f = 2.3f;
    public static final int i = 1; // iconst_1
    public static final short s = 63; // bipush 63
    public static final int i2 = 128; //  sipush  128
    public static final int i3 = 18; //  sipush  128
//    public static final int i = 1;

    static {
        System.out.println("My parent static block");
    }
}
