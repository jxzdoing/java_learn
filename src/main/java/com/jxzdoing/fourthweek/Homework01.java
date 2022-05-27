package com.jxzdoing.fourthweek;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author:jack
 * @date:Create on 2022/5/24 21:59
 */
public class Homework01 implements Callable<Integer> {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        Homework01 homework = new Homework01();

        FutureTask<Integer> task = new FutureTask<>(homework);

        new Thread(task).start();

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
        return sum();
    }
}
