package com.lxb.test;

import java.util.*;

import java.util.concurrent.*;

/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-03-23 20:22
 **/
public class TestThread {
    public static void main(String[] args) {
        TestThread t = new TestThread();
        t.test();

    }
    public  void test(){
        int threadNum = 3;
        int index = 0;
        ExecutorService ex = Executors.newFixedThreadPool(threadNum);
        List<Future<Integer>> futures = new ArrayList<Future<Integer>>();

        for(int i = 0;i < 12 ; i++){

            futures.add(ex.submit(new ThreadTask(i, i+1)));
            try {
                Integer a = futures.get(i).get();
                System.out.println("a="+a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }
    class ThreadTask implements Callable<Integer>{
        private int start;
        private int end;

        public ThreadTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public Integer call() throws  Exception{
            System.out.println(Thread.currentThread().getName()+"start=" + start + "end=" + end);

           // System.out.println(1/0);

            return start;
        }
    }

    class myUncaughtExcuptionHandler implements Thread.UncaughtExceptionHandler {
        /**
         * 处理异常
         */
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("Exception::" + e);
        }
    }
}
