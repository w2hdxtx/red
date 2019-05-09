package com.lv.juc01;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author lvxh  2019-05-07 16:26
 */


public class Code02_Volatile {

    private volatile int i = 0;


    /**
     * volatile不保证原子性
     */
//    @Test
    public void test01() throws InterruptedException {

        for (int j=0; j<10; j++){
            new Thread(() -> {

                int k = 0;
                while (k++ < 5000)
                    ++i;
            }).start();

        }
        Thread.sleep(10000); // 等待上面的线程执行完毕
        System.out.println(i);
    }


    public static void main(String[] args) {
        int[] arr = new int[10];
        System.out.println(Arrays.toString(arr));
    }







}
