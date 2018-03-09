package com.dragonfly;

import java.util.concurrent.atomic.AtomicInteger;

public class DeadLock {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(100);
        atomicInteger.getAndIncrement();

        DeadLockA deadLockA = new DeadLockA();
        DeadLockB deadLockB = new DeadLockB();

        deadLockA.b = deadLockB;
        deadLockB.a = deadLockA;

        Thread t1 = new Thread(() -> {
            try {
                deadLockA.lockA();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                deadLockB.lockB();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        t1.join();
    }
}


class DeadLockA {
    DeadLockB b;
    public synchronized void lockA() throws InterruptedException {
        Thread.sleep(100);
        b.lockB();
    }
}


class DeadLockB {
    DeadLockA a;
    public synchronized void lockB() throws InterruptedException {
        a.lockA();
    }
}
