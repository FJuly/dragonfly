package com.dragonfly;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Task21 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        TaskOne taskOne = new TaskOne();
        TaskTwo taskTwo = new TaskTwo(taskOne);

        executorService.execute(taskOne);
        executorService.execute(taskTwo);

        //executorService.shutdownNow();
    }
}

class TaskOne implements Runnable {
    @Override
    public void run() {
        System.out.println("task one start");

        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("exception");
            }
        }
        System.out.println("task one end");
    }
}

class TaskTwo implements Runnable {
    private final Runnable taskOne;

    TaskTwo(Runnable runnable) {
        taskOne = runnable;
    }

    @Override
    public void run() {
        System.out.println("task two begin");
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
            synchronized (taskOne) {
                taskOne.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task two end");
    }
}


