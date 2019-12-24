package com.sc.juc;


import org.junit.Test;

public class ThreadCommunication {


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
     *
     */
    @Test
    public void testSellTicket2(){
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

class Ticket2 implements Runnable{
    private int number = 1000;

    @Override
    public void run() {
        while(true){
            synchronized (this){
                if(number > 0){
                    System.out.println(Thread.currentThread().getName() + " is selling the " + number + " ticket");
                    number --;
                }
            }
        }
    }
}
