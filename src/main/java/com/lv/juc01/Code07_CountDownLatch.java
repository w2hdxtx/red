package com.lv.juc01;

import java.util.concurrent.CountDownLatch;

/**
 * @author lvxh  2019-05-14 12:42
 */
public class Code07_CountDownLatch {


    // 规定线程运行的起点和终点
    public static void test01() throws InterruptedException {

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(10);

        for (int i=0; i<10; i++)
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        startLatch.await();         // 阻塞等待开始
                        Thread.sleep(1000);   // 模拟执行一些操作
                        System.out.println(Thread.currentThread().getName()+ "执行完毕");
                        endLatch.countDown();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }, "Threas-" + i).start();


        System.out.println("全部线程开始执行");
        startLatch.countDown();
        endLatch.await();               // 阻塞等待全部线程执行完毕
        System.out.println("全部线程执行完毕");

    }


    // CountDownLatch 让线程按指定的顺序执行
    public static void test02(){

        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    System.out.println("线程t1开始执行");
                    Thread.sleep(1000);
                    System.out.println("线程t1执行完毕");
                    latch1.countDown();     // 允许线程t2执行

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    latch1.await();             // 阻塞等待线程t1执行完毕
                    System.out.println("线程t2开始执行");
                    Thread.sleep(1000);
                    System.out.println("线程t2执行完毕");
                    latch2.countDown();          // 允许线程t3执行

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    latch2.await();             // 阻塞等待线程t2执行完毕
                    System.out.println("线程t3开始执行");
                    Thread.sleep(1000);
                    System.out.println("线程t3执行完毕");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        t3.start();
        t2.start();
        t1.start();

    }

    // Thread 的 join() 也可以让线程按指定的顺序执行
    public static void test03(){

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    System.out.println("线程t1开始执行");
                    Thread.sleep(1000);
                    System.out.println("线程t1执行完毕");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    t1.join();             // 阻塞等待线程t1执行完毕
                    System.out.println("线程t2开始执行");
                    Thread.sleep(1000);
                    System.out.println("线程t2执行完毕");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    t2.join();             // 阻塞等待线程t2执行完毕
                    System.out.println("线程t3开始执行");
                    Thread.sleep(1000);
                    System.out.println("线程t3执行完毕");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        t3.start();
        t2.start();
        t1.start();

    }


    public static void main(String[] args) throws InterruptedException {
//        test01();
//        test02();
        test03();
    }



}
