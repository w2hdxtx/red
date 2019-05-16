package com.lv.juc01;

import java.util.concurrent.Semaphore;

/**
 * @author lvxh  2019-05-14 13:34
 */
public class Code08_Semaphore {

    // 控制访问资源的线程数目，限流
    public static void test01(){

        Semaphore semaphore = new Semaphore(3); // 只允许3个线程连接数据库

        for (int i=0; i<10; i++)
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        semaphore.acquire(1);   // 获取1个许可，未获取到许可的线程会阻塞
                        System.out.println(Thread.currentThread().getName() + " 获取到数据库连接");
                        Thread.sleep(1000);      // 模拟进行数据库操作
                        System.out.println(Thread.currentThread().getName() + " 执行完毕，已经归还数据库连接");
                        semaphore.release(1);   // 归还许可

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "线程" + i).start();

    }


    public static void main(String[] args) {
        test01();
    }


}
