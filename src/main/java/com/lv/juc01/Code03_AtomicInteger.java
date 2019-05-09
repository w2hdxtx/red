package com.lv.juc01;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author lvxh  2019-05-07 18:00
 */
public class Code03_AtomicInteger {

    private AtomicInteger i = new AtomicInteger(0);


    /**
     * AtomicInteger 示例
     */
//    @Test
    public void test01() throws InterruptedException {

        for (int j=0; j<10; j++){
            new Thread(() -> {

                int k = 0;
                while (k++ < 5000)
                    i.incrementAndGet();      // 等价于 ++i , getAndIncrement()等价于i++，保证原子性
            }).start();
        }

        Thread.sleep(10000);        // 等待上面的线程执行完毕
        System.out.println(i.get());
    }


    /**
     * Integer 自动装箱调用 valueOf() ，自动拆箱调用intValue()
     */
    public static void test02(){

        Integer i = 100;    // Integer i = Integer.valueOf(100);
        Integer j = 100;
        System.out.println(i == j);

        Integer a = 1000;
        Integer b = 1000;
        System.out.println(a == b);
    }

    /**
     * AtomicStampedReference 解决 ABA 问题示例：
     */
    private static Integer num = 100;    // 初值为100
    private static AtomicStampedReference<Integer> atf = new AtomicStampedReference<>(num,1); // 初始版本为1
    public static void test03() throws InterruptedException {

        Thread t = new Thread(() -> {
            try {
                int stamp = atf.getStamp(); // 记录拿到数据时的版本 1

                Thread.sleep(1000);

                boolean success = atf.compareAndSet(num, 200, stamp,stamp+1);   // 返回值代表是否修改成功

                System.out.println("内部线程是否修改成功：" + success);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();

        int stamp = atf.getStamp(); // 记录拿到数据时的版本 1

        boolean flag = atf.compareAndSet(num, 55, stamp,stamp+1);
        System.out.println("外部线程是否修改成功：" + flag);

       t.join(); // 等待t线程执行完毕才继续往下



    }






    static class Student{
        private String name;
        public Student(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }


    public static void test04(){

        Student stu1 = new Student("张三");
        Student stu2 = new Student("李四");
        Student stu3 = new Student("王五");

        AtomicReference<Student> af = new AtomicReference<>(stu1);
        System.out.println(af.get());

        boolean success = af.compareAndSet(stu1, stu2);
        System.out.println(success);
        System.out.println(af.get());

        System.out.println("************");

        success = af.compareAndSet(stu1, stu3);
        System.out.println(success);
        System.out.println(af.get());

    }



    public static void main(String[] args) throws InterruptedException {

        test03();

    }



}
