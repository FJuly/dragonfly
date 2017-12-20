package com.dragonfly;

import java.util.concurrent.*;

public class DubboTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (!Thread.interrupted()) {
//                    System.out.println("xxx");
//                }
//            }
//        });
//
//        t.start();
//        TimeUnit.SECONDS.sleep(10);
//        t.interrupt();
//        while (true) {}



        FutureTask<Integer> futureTask = new FutureTask<>(new Task1(), 1);

        futureTask.run();
        futureTask.run();
        futureTask.run();
        futureTask.run();
        futureTask.run();
        futureTask.run();
    }
    // 泛化引用

    // 参数回调
}


class Task implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return 1122;
    }
}

class Task1 implements Runnable {
    @Override
    public void run() {
            System.out.println("111");
    }
}