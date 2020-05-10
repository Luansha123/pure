package com.sc.base;

import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test1 {
    static Lock lock = new ReentrantLock();
    static Condition condition1 = lock.newCondition();
    static Condition condition2 = lock.newCondition();
    static boolean likeApple = false;
    static boolean likeOrange = false;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            lock.lock();
            try{
                while (!likeApple) {
                    System.out.println("don't like apple");
                    try {
                        condition1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                lock.unlock();
            }

            System.out.println("now like apple");
        }, "t1");
        Thread t2 = new Thread(() -> {
            lock.lock();
            try{
                while (!likeOrange) {
                    System.out.println("don't like orange");
                    try {
                        condition2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                lock.unlock();
            }

            System.out.println("now like orange");
        }, "t2");

        t1.start();
        t2.start();
        Thread.sleep(10);


        Thread t3 = new Thread(() -> {
            lock.lock();
            try {
                likeApple = true;
                condition1.signal();
            }finally {
                lock.unlock();
            }
        }, "t3");
        Thread t4 = new Thread(() -> {
            lock.lock();
            try {
                likeOrange = true;
                condition2.signal();
            }finally {
                lock.unlock();
            }
        }, "t4");

        t3.start();
        t4.start();
    }


    @Test
    public void test04(){
//        ThreadPoolExecutor
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<?> submit1 = executorService.submit(() -> {
            System.out.println("task1......");
        });



        Future<String> submit = executorService.submit(() -> {
            System.out.println("task2......");
            return "task2 ...";
        });


    }

    @Test
    public void test05() throws InterruptedException {
        Timer timer = new Timer();

        TimerTask ts1 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task1");
            }
        };

        TimerTask ts2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task2");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        timer.schedule(ts1, 1000);
        timer.schedule(ts2, 2000);

        Thread.sleep(3000L);
    }


    @Test
    public void test06() throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool(2);
        pool.execute(new MyTask(6));
        System.out.println(pool.invoke(new MyTask(6)));

        Thread.sleep(3000L);
    }

}
class MyTask extends RecursiveTask<Integer>{

    private int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if(n == 1){
            System.out.println("1.....");
            return 1;
        }
        MyTask my = new MyTask(n-1);
        my.fork();

        Integer result = n + my.join();
        return result;
    }
}