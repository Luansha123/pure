package com.sc.juc;


import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SellTicketDemoTest {


    /**
     * 资源类Ticket不实现Runnable，使用synchronized的写法
     */
    @Test
    public void testSellTicket() {
        Ticket t = new Ticket();


        new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                t.sellTicket();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                t.sellTicket();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                t.sellTicket();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                t.sellTicket();
            }
        }, "D").start();


    }

    /**
     * 资源类Ticket实现了Runnable接口，使用synchronized的写法
     */
    @Test
    public void testSellTicket2() {
        Ticket2 t2 = new Ticket2();
        new Thread(() -> {
            t2.run();
        }, "D").start();

        new Thread(() -> {
            t2.run();
        }, "A").start();
        new Thread(() -> {
            t2.run();
        }, "B").start();
        new Thread(() -> {
            t2.run();
        }, "C").start();

    }

    /**
     * 资源类不实现Runnable,使用java.util.concurrent包下的Lock类实现
     *
     */
    @Test
    public void testSellTicket03() {
        Ticket03 ticket03 = new Ticket03();
        new Thread(() -> {
            while(true){
                if(ticket03.getNumber() > 0){
                    ticket03.sellTicket();
                }else{
                    break;
                }
            }
        }, "A").start();
        new Thread(() -> {
            while(true){
                if(ticket03.getNumber() > 0){
                    ticket03.sellTicket();
                }else{
                    break;
                }
            }
        }, "B").start();
        new Thread(() -> {
            while(true){
                if(ticket03.getNumber() > 0){
                    ticket03.sellTicket();
                }else{
                    break;
                }
            }
        }, "C").start();
        new Thread(() -> {
            while(true){
                if(ticket03.getNumber() > 0){
                    ticket03.sellTicket();
                }else{
                    break;
                }
            }
        }, "D").start();


        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class Ticket {
    private int number = 100;

    public synchronized void sellTicket() {
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + " is selling the " + number + " ticket, " + (number - 1) + " tickets left");
            number--;
        }
    }
}

class Ticket2 implements Runnable {
    private int number = 1000;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (number > 0) {
                    System.out.println(Thread.currentThread().getName() + " is selling the " + number + " ticket");
                    number--;
                }
            }
        }
    }
}

class Ticket03 {
    private int number = 100;
    private Lock lock = new ReentrantLock();

    public int getNumber() {
        return number;
    }

    public void sellTicket() {
        lock.lock();
        try {
            if(number > 0){
                System.out.println(Thread.currentThread().getName() + " is selling the " + number + " ticket");
                number --;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
