package com.sc.base;


import org.junit.Test;

import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;

// 打印A,B,C 顺序5次
public class Test2 {

    private static Object lock = new Object();
    private static String flag = "a"; //
    public static void main(String[] args) throws InterruptedException {


        Thread t1 = new Thread(() -> {
            synchronized (lock){
                while(flag != "a"){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("a");
                flag = "b";
                lock.notifyAll();
            }

        }, "t1");

        Thread t2 = new Thread(() -> {
            synchronized (lock){
                while(flag != "b"){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("b");
                flag = "c";
                lock.notifyAll();
            }
        }, "t2");
        Thread t3 = new Thread(() -> {
            synchronized (lock){
                while(flag != "c"){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("c");
                flag = "a";
                lock.notifyAll();
            }

        }, "t3");

//        for (int i = 0; i < 5; i++) {
//
//        }

        t2.start();
        Thread.sleep(1000);
        t1.start();
        t3.start();

    }

    @Test
    public void test04(){
        LongAdder longAdder = new LongAdder();
        for (int i = 0; i < 10; i++) {
            longAdder.add((long) i);
        }
        System.out.println(longAdder.longValue());
    }
}
