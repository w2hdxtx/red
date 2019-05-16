package com.lv.juc01;

import java.util.concurrent.CyclicBarrier;

/**
 * @author lvxh  2019-05-14 14:20
 */
public class Code09_CyclicBarrier {

    // cyclicBarrier 允许线程相互等待，最终达到公共屏障点
    public static void test01(){

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3); // 参与者数目是3

        for (int i=0; i<3; i++)
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        System.out.println(Thread.currentThread().getName() + "准备完毕");
                        Thread.sleep(1000);

                        // 在所有线程都调用这行代码之前会一直阻塞，即保证大家同时到达这个位置然后才能执行下面的代码
                        cyclicBarrier.await();
                        System.out.println(Thread.currentThread().getName() + "开始执行");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "线程"+i).start();

//        cyclicBarrier.reset();            可以重置计数状态

    }


    public static void main(String[] args) {
        test01();
    }


}
