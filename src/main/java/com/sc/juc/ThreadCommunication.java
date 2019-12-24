package com.sc.juc;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 经典的生产者与消费者问题，线程间的通讯
 */
public class ThreadCommunication {

    /**
     * 1生产1消费，使用synchronized方式
     * 如果将cake 中的consume和produce方法中的判断用if而不是while，多生产者和消费者模式下会出问题
     */
    @Test
    public void testPC() {
        Cake cake = new Cake();
        new Thread(() -> {
            while (true) {
                cake.consumeCake();
            }
        }, "consumer").start();
        new Thread(() -> {
            while (true) {
                cake.produceCake();
            }
        }, "producer").start();
    }

    /**
     * 多生产者多消费者模式，使用synchronized方式
     */
    @Test
    public void testPC2() {
        Cake cake = new Cake();
        new Thread(() -> {
            while (true) {
                cake.consumeCake();
            }
        }, "consumer2").start();
        new Thread(() -> {
            while (true) {
                cake.produceCake();
            }
        }, "producer2").start();
        new Thread(() -> {
            while (true) {
                cake.consumeCake();
            }
        }, "consumer3").start();
        new Thread(() -> {
            while (true) {
                cake.produceCake();
            }
        }, "producer3").start();
    }


    /**
     * 多生产者多消费者模式，使用juc.locks.Lock和juc.locks.Condition
     */
    @Test
    public void testPC3() {
        Cake2 cake2 = new Cake2();

        new Thread(() -> {
            while (true) {
                cake2.consumeCake();
            }
        }, "consumer").start();
        new Thread(() -> {
            while (true) {
                cake2.consumeCake();
            }
        }, "consumer2").start();
        new Thread(() -> {
            while (true) {
                cake2.consumeCake();
            }
        }, "consumer3").start();
        new Thread(() -> {
            while (true) {
                cake2.produceCake();
            }
        }, "producer").start();
        new Thread(() -> {
            while (true) {
                cake2.produceCake();
            }
        }, "producer2").start();
        new Thread(() -> {
            while (true) {
                cake2.produceCake();
            }
        }, "producer3").start();

    }


    /**
     * 使用jucl.Condition，精准的唤醒指定线程
     */
    public static void main(String[] args) {
        ClazzForOrder cfo = new ClazzForOrder();
        for (int i = 0; i < 10; i++) {

        }
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                cfo.wakeup();
            }
        }, "A").start();
//        new Thread(() -> {
//            for (int i = 0; i < 10; i++) {
//                cfo.wakeup();
//            }
//        }, "B").start();
//        new Thread(() -> {
//            for (int i = 0; i < 10; i++) {
//                cfo.wakeup();
//            }
//        }, "C").start();

    }


}


class Cake {
    private int num = 0;

    public synchronized void produceCake() {
        while (num > 0) {     // 多生产者多消费者模式下只能用while，不能用if进行判断
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " is making cake, now there is " + num + " cake");
        num++;
        notifyAll();
    }

    public synchronized void consumeCake() {
        while (num <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " is consuming cake, now there is " + num + " cake");
        num--;
        notifyAll();
    }
}

class Cake2 {
    private int num;
    private Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void consumeCake() {
        lock.lock();
        try {
            while (num <= 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + " is consuming, now there is " + num + " cake");
            num--;
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void produceCake() {
        lock.lock();
        try {
            while (num > 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + " is producing, now there is " + num + " cake");
            num++;
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * A->B->C，给我来10次
 */
class ClazzForOrder {

    private Lock lock = new ReentrantLock();
    private String name = "A";
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void wakeup() {
        lock.lock();
        try {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printA(){
        while()
    }
}
