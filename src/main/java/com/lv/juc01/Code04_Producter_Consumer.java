package com.lv.juc01;

import java.util.ArrayList;

/**
 * @author lvxh  2019-05-09 15:21
 */


/***
 * 资源类
 */
class MyArrayList{

    private int capacity = 100;     // 数组容量
    private int productorNum = 0;   // 产品编号
    private ArrayList<Integer> list = new ArrayList<>();    // 存储产品的数组

    // 生产者线程调用这个函数
    public synchronized void product(int threadNum){
        try {

            if (list.size() < capacity){    // 还没到达最大容量，可以生产
                list.add(++productorNum);   // 生产一个产品
                System.out.println("生产者" +threadNum + "生产了产品" + productorNum);
                this.notifyAll();           // 唤醒在 this 上阻塞的线程，把他们放入锁池，被唤醒的线程包含生产者+消费者
                Thread.sleep(100);
            }
            else
                this.wait();    // 容量已满，不能生产了，将生产者线程放入等待队列

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 消费者线程调用这个函数
    public synchronized void consume(int threadNum){
        try {
            if (list.size() > 0){               // 还有产品可以消费
                int i = list.remove(0);   // 消费一个产品
                System.out.println("消费者者" +threadNum + "消费了产品" + i);
                this.notifyAll();               // 唤醒在 this 上阻塞的线程，把他们放入锁池，被唤醒的线程包含生产者+消费者
                Thread.sleep(100);
            }
            else
                this.wait();    // 已经没有产品可以消费了，把消费者线程放入等待队列

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


// 生产者
class Producter implements Runnable{

    private MyArrayList myArrayList;
    private int threadNum;              // 生产者编号

    public Producter(MyArrayList myArrayList, int threadNum){
        this.myArrayList = myArrayList;
        this.threadNum = threadNum;
    }

    @Override
    public void run() {     // 死循环生产
        while (true)
            myArrayList.product(threadNum);
    }
}


// 消费者
class Consumer implements Runnable{

    private MyArrayList myArrayList;
    private int threadNum;          // 消费者编号

    public Consumer(MyArrayList myArrayList, int threadNum){
        this.myArrayList = myArrayList;
        this.threadNum = threadNum;
    }

    @Override
    public void run() {     // 死循环消费
        while (true)
            myArrayList.consume(threadNum);
    }
}




public class Code04_Producter_Consumer {

    public static void main(String[] args) {

        MyArrayList myArrayList = new MyArrayList();

        //开启两个生产者和两个消费者
        new Thread(new Producter(myArrayList, 1)).start();
        new Thread(new Producter(myArrayList, 2)).start();

        new Thread(new Consumer(myArrayList, 1)).start();
        new Thread(new Consumer(myArrayList, 2)).start();

    }

}











