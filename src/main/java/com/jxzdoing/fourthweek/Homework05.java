package com.jxzdoing.fourthweek;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:jack
 * @date:Create on 2022/5/26 20:52
 */
public class Homework05 implements Callable<Integer> {

    final static Lock lock = new ReentrantLock(true);
    final static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        Homework05 homework =new Homework05();
        FutureTask<Integer> task = new FutureTask<>(homework);

        Thread t = new Thread(task);
        t.start();

        Thread.sleep(1);
        lock();

        try {
            System.out.println("异步计算结果为:"+task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("计算耗时:"+(System.currentTimeMillis() - start)+"ms");

    }

    public static void lock(){
        try {
            lock.lock();
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Integer call() throws Exception {
        return sum();
    }

    private static int sum() throws InterruptedException {
        int res;
        try {
            lock.lock();
            res = fibo(36);
            condition.await();
        } finally {
            lock.unlock();
        }
        return res;
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
