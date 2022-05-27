package com.jxzdoing.fourthweek;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author:jack
 * @date:Create on 2022/5/25 21:44
 */
public class Homework03 implements Callable<Integer> {

    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        Homework03 homework = new Homework03();

        FutureTask<Integer> task = new FutureTask<>(homework);

        Thread t = new Thread(task);
        t.start();

        Thread.sleep(500);

        synchronized (lock){
            lock.notify();
        }

        System.out.println("子线程:"+t.getName()+"执行完成.");

        int result = 0;
        try {
            result = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");


    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }

    @Override
    public Integer call() throws Exception {
        synchronized (lock){
            lock.wait();
            return sum();
        }
    }
}
