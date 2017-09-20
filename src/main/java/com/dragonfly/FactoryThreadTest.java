package com.dragonfly;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class FactoryThreadTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 5; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        System.out.println("#");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            System.out.println("thread exit");
                            return;
                        }
                    }
                }
            });
        }
        TimeUnit.MILLISECONDS.sleep(1000);
        executorService.shutdownNow();
        System.out.println("main thread exit");
    }
}

class DaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {

        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    }
}


    class OutDemo {
        void show() {
            System.out.println("this is Out showing.");
        }
    }

