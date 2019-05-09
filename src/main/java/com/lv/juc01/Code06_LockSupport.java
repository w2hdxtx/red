package com.lv.juc01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @author lvxh  2019-05-09 16:36
 */
public class Code06_LockSupport {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread((() -> {

            System.out.println("进入");
            LockSupport.park();             // 无需获取到锁就可以调用
            System.out.println("阻塞已结束");

        }));

        t.start();

        Thread.sleep(3000);
//        LockSupport.unpark(t);      // 两种方式均可以打断 LockSupport.park() 的阻塞，但是中断方式会置位中断标记位
        t.interrupt();
        System.out.println(t.interrupted());  // 打印中断标记位












    }

}
