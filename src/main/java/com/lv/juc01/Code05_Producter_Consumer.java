package com.lv.juc01;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lvxh  2019-05-09 16:09

 *      synchronized(obj){                              lock.lcok()
 *        if(...){                                      if(...){
 *            xxx;                    等价于                 xxx;
 *            obj.notifyAll();        =====>                condition1.signalAll();
 *        }                                             }
 *        else                                          else
 *          obj.wait();                                     condition2.await();
 *      }                                               lock.unlock();
 *
 */

class MyArrayListX{

    private int capacity = 100;
    private int productorNum = 0;
    private ArrayList<Integer> list = new ArrayList<>();

    private ReentrantLock lock = new ReentrantLock();           // 锁
    private Condition producterCodition = lock.newCondition();  // 生产者监视器，容量已满时，生产者会在这个Condition对象上阻塞
    private Condition consumerCodition = lock.newCondition();   // 消费者监视器，没有产品可以消费时，消费者会在这个Condition对象上阻塞

    public  void product(int threadNum){
        try {
            lock.lock();                            // 获取锁
            if (list.size() < capacity){
                list.add(++productorNum);
                System.out.println("生产者" +threadNum + "生产了产品" + productorNum);
                consumerCodition.signalAll();       // 只唤醒在 consumerCodition 阻塞的线程，即只唤醒消费者
                Thread.sleep(100);
            }
            else
                producterCodition.await();         // 当前线程（生产者线程）在 producterCodition 上阻塞

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();                          // 释放锁
        }
    }

    public void consume(int threadNum){
        try {
            lock.lock();                            // 获取锁
            if (list.size() > 0){
                int i = list.remove(0);
                System.out.println("消费者者" +threadNum + "消费了产品" + i);
                producterCodition.signalAll();          // 只唤醒在 producterCodition 阻塞的线程，即只唤醒生产者
                Thread.sleep(100);
            }
            else
                consumerCodition.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();                            // 释放锁
        }
    }

}



class ProducterX implements Runnable{

    private MyArrayListX myArrayListX;
    private int threadNum;

    public ProducterX(MyArrayListX myArrayListX, int threadNum){
        this.myArrayListX = myArrayListX;
        this.threadNum = threadNum;
    }

    @Override
    public void run() {
        while (true)
            myArrayListX.product(threadNum);
    }
}


class ConsumerX implements Runnable{

    private MyArrayListX myArrayListX;
    private int threadNum;

    public ConsumerX(MyArrayListX myArrayListX, int threadNum){
        this.myArrayListX = myArrayListX;
        this.threadNum = threadNum;
    }

    @Override
    public void run() {
        while (true)
            myArrayListX.consume(threadNum);
    }
}




public class Code05_Producter_Consumer {

    public static void main(String[] args) {

        MyArrayListX myArrayListX = new MyArrayListX();

        new Thread(new ProducterX(myArrayListX, 1)).start();
        new Thread(new ProducterX(myArrayListX, 2)).start();
        new Thread(new ConsumerX(myArrayListX, 1)).start();
        new Thread(new ConsumerX(myArrayListX, 2)).start();

    }

}