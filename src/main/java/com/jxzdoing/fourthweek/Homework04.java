package com.jxzdoing.fourthweek;

import java.util.concurrent.*;

/**
 * @author:jack
 * @date:Create on 2022/5/26 9:50
 */
public class Homework04 implements Callable<Integer> {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable callable = new Homework04();
        Future<Integer> future = executorService.submit(callable);
        executorService.shutdown();
        try {
            executorService.awaitTermination(1000,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("异步计算结果为:"+future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("计算耗时:"+(System.currentTimeMillis() - start)+"ms");

    }
    @Override
    public Integer call() throws Exception {
        return sum();
    }
    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
