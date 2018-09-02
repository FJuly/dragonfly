package com.dragonfly;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class JstackCase {

    public static Executor executor = Executors.newFixedThreadPool(5);

    public static ReentrantLock lock = new ReentrantLock();


    public static void main(String[] args) {
        Task task1 = new Task();
        Task task2 = new Task();
        executor.execute(task1);
        executor.execute(task2);
    }


    static class Task implements Runnable {
        @Override
        public void run() {
            lock.lock();
            calculate();
            lock.unlock();
        }

        public void calculate() {
            int i = 0;
            while (true) {
                i++;
            }
        }
    }
}


