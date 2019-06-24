package com.lxb.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-05-19 23:38
 **/
public class TestSynchronized {
    public  void testSynchronizedLock(Integer num){
        System.out.println("begin" + num);

        synchronized (this){

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("middle" + num);

        }
        System.out.println("end" + num);
    }

    class MultiThreadTest implements Callable<Integer> {
        private Integer num;
        public MultiThreadTest(Integer num) {
            this.num = num;
        }

        public Integer call() {
            testSynchronizedLock(num);
            return 0;
        }
    }

    public void testSynchronized(){
        ExecutorService ex = Executors.newFixedThreadPool(5);
        List<Future<Integer>> futures = new ArrayList<Future<Integer>>();
        for (int i = 0; i < 10; i++) {
            futures.add(ex.submit(new MultiThreadTest(i)));
        };
    }
    public static void main(String[] args) {
        TestSynchronized a = new TestSynchronized();
        a.testSynchronized();
    }
}
