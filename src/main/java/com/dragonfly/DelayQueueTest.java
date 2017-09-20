package com.dragonfly;

import java.util.concurrent.Delayed;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueTest {
    public static void main(String[] args) {
        PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<>();

        queue.add(new PriorizedTask());
    }
}


class DelayTask implements Runnable, Delayed {

    @Override
    public long getDelay(TimeUnit unit) {
        return 0;
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }

    @Override
    public void run() {

    }
}


class PriorizedTask implements Runnable {

    @Override
    public void run() {

    }
}