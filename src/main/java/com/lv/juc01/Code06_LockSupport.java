package com.lv.juc01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @author lvxh  2019-05-09 16:36
 */
public class Code06_LockSupport {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(){
            @Override
            public void run() {
                while (true){
                    System.out.println("进入");
                    LockSupport.park();                     // 无需获取到锁就可以调用
                    System.out.println(interrupted());      // 清除中断标记位，不要在线程外部调用
                    System.out.println("阻塞已结束");
            }
        }};

        t.start();

       while (true){
           Thread.sleep(1000);
           t.interrupt();             // 打断 LockSupport.park()，同时置位中断标记位
//        LockSupport.unpark(t);      // 打断 LockSupport.park()，不修改中断标记位
       }












    }

}
