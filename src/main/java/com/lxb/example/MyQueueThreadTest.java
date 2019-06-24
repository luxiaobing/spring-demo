package com.lxb.example;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程实现阻塞队列
 *
 * @author Xiaobing.Lu
 * @create 2019-02-22 22:19
 **/
public class MyQueueThreadTest {


    private LinkedList<Object> list = new LinkedList<Object>();

    private AtomicInteger count = new AtomicInteger();

    private final int minSize = 0;
    private final int maxSize ;

    public MyQueueThreadTest(int maxSize){
        this.maxSize = maxSize;
    }

    Object lock = new Object();

    public Object put(Object obj){
        Object ret = null;
        System.out.println("put="+count.get());
        synchronized (lock){
            while(count.get() == this.maxSize){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            ret = list.add(obj);
            count.incrementAndGet();
            System.out.println("新加入的元素:"+ obj);

            lock.notify();
        }

        return  ret;

    }

    public Object take(){
        Object ret = null;
        synchronized (lock){
            System.out.println("take="+count.get());
           while(count.get() == this.minSize){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            ret = list.removeFirst();
            count.decrementAndGet();

            //唤醒另一个线程
            lock.notify();
        }

        return  ret;
    }
    public static void main(String[] args){

        final MyQueueThreadTest mq = new MyQueueThreadTest(5);
        mq.put("a");
        mq.put("b");
        mq.put("c");
        mq.put("d");
        mq.put("e");

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                mq.put("f");
                mq.put("g");
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                Object o1 = mq.take();
                System.out.println("移除元素为："+o1);
                Object o2 = mq.take();
                System.out.println("移除元素为："+o2);
                /*Object o3 = mq.take();
                System.out.println("移除元素为："+o3);
                Object o4 = mq.take();
                System.out.println("移除元素为："+o4);
                Object o5 = mq.take();
                System.out.println("移除元素为："+o5);
                Object o6 = mq.take();
                System.out.println("移除元素为："+o6);

                Object o7 = mq.take();
                System.out.println("移除元素为："+o7);
                Object o8 = mq.take();
                System.out.println("移除元素为："+o8);

                Object o9 = mq.take();
                System.out.println("移除元素为："+o9);*/
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();

    }


}
