package com.dragonfly;

public class SleepInterrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (Thread.currentThread().isInterrupted()) {
                        Thread.sleep(9000);
                        System.out.println(Thread.currentThread().isInterrupted());
                    }
                } catch (InterruptedException e) {
                    System.out.println("sleep interrupte");
                }
            }
        });
        thread.start();
        thread.interrupt();
        Thread.sleep(9000);
    }
}
