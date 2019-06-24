package com.lxb.test;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.alibaba.fastjson.*;

/**
 * @author Xiaobing.Lu
 * @Description 测试类
 * @date 2019/5/17 17:55
 */
public class TestLock {

    protected Jedis jedis;
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;
    private static final String LOCK_KEY = "sz.lu.test.lock.key";
    private static final String requestId = "2019450978006";
    int initNum = 1;



    public TestLock(Jedis jedis) {

        this.jedis = jedis;
    }

    /**
     * 尝试获取分布式锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public  boolean tryGetDistributedLock(Jedis jedis , String requestId, int expireTime) {

        String result = jedis.set(LOCK_KEY, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }


    /**
     * 释放分布式锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public  boolean releaseDistributedLock( Jedis jedis, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(LOCK_KEY), Collections.singletonList(requestId));

        System.out.println("result=" + JSON.toJSONString(result));
        jedis.close();
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }

    public void testRedisLock(Jedis jedis,int i){
        tryGetDistributedLock(jedis,  requestId, 1);
        initNum ++ ;
        System.out.println(initNum);
        releaseDistributedLock(jedis, requestId);
    }
    public void testUnLock(int i){
        initNum ++ ;
        System.out.println(initNum);
        System.out.println("hh" + initNum);
    }
    public synchronized  void testSynchronizedLock(int i ){
        initNum ++ ;
        System.out.println(initNum);
        System.out.println("hh" + initNum);
    }

    class MultiThreadTest implements Callable<Integer> {
        private Integer num ;
        private Jedis jedis;
        public MultiThreadTest(Jedis jedis, Integer num) {
            this.num = num;
            this.jedis = jedis;
        }
        public Integer call() {
            //testUnLock(num);
            testRedisLock(jedis, num);
            //testSynchronizedLock(num);
            return 0;
        }
    }
    public void test(Jedis jedis){
        ExecutorService ex = Executors.newFixedThreadPool(1000);
        List<Future<Integer>> futures = new ArrayList<Future<Integer>>();
        for (int i = 0; i < 100000; i++) {
            futures.add(ex.submit(new MultiThreadTest(jedis, initNum)));
        }
    }
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379 ); // 默认端口
        jedis.auth("12345");
        TestLock  t = new TestLock(jedis);
        t.test(jedis);

        /*Jedis jedis = new Jedis("47.100.94.134", 4001); // 默认端口
        jedis.auth("I5JPCPkMWTNz"); // 指定密码
        System.out.println("Connection to server sucessfully");

        jedis.setex(LOCK_KEY, 20,"1");
        // 获取存储的数据并输出
        System.out.println("Stored string in redis:: " + jedis.get(LOCK_KEY));*/

    }

}
