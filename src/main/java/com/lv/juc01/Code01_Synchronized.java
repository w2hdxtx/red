package com.lv.juc01;


import org.junit.Test;

/**
 * @author lvxh  2019-05-07 16:20
 */
public class Code01_Synchronized {


    /**
     * 必须先获取到一个对象的锁，才能调用这个对象的wait()，否则会抛出 llegalMonitorStateException
     * 因为wait()会释放锁，释放锁之前必须先持有锁
     */
    @Test
    public void test01() throws InterruptedException {
        Object obj = new Object();

        synchronized (obj){

            obj.wait();
        }

    }

































}
