package com.lv.juc01;

/**
 * @author lvxh  2019-05-15 17:02
 */

class User{

    public String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}

public class Code10_ThreadLocal {

    private static ThreadLocal<User> threadLocal = new ThreadLocal<User>(){
        @Override
        protected User initialValue() {
            return new User("张三");
        }
    };

    public static void test01(){

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    User user = threadLocal.get();
                    System.out.println("线程t1：" + user + "  hashCode：" + user.hashCode());
                    user.name = "111111111111111111";
                    System.out.println("线程t1：" + threadLocal.get() + "  hashCode：" + user.hashCode());
                    Thread.sleep(2000);
                    System.out.println("线程t1在t2修改之后获取：" + threadLocal.get() + "  hashCode：" + user.hashCode() );

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(1000);     // 阻塞一下，让t1先执行
                    User user = threadLocal.get();
                    System.out.println("线程t2：" + user + "  hashCode：" + user.hashCode());
                    user.name = "222222222222222";
                    System.out.println("线程t2：" + threadLocal.get() + "  hashCode：" + user.hashCode());


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();


    }

    public static void main(String[] args) {
        test01();
    }


}
