package com.dragonfly;

import java.util.concurrent.*;

public class BlockingQueueTest {
    // 试用几种阻塞队列
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<TaskThree> blockingQueue = new LinkedBlockingDeque<>();

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(new ConsumerTask(blockingQueue));
        for (int i = 0; i<10; i ++) {
            blockingQueue.put(new TaskThree(i));
        }
        executorService.shutdownNow();
    }
}


class TaskThree implements Runnable {

    private  int id = 0;

    TaskThree(int taskId) {
        id = taskId;
    }
    @Override
    public void run() {
        System.out.println("taskId=" + id);
    }
}

class ConsumerTask implements Runnable {

    private BlockingQueue<TaskThree> blockingQueue;

    ConsumerTask(BlockingQueue<TaskThree> queue) {
        blockingQueue = queue;
    }
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                TaskThree taskThree;
                taskThree = blockingQueue.take();
                taskThree.run();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}