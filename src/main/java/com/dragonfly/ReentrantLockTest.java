package com.dragonfly;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    public static void main(String[] args) throws InterruptedException {

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        lock.lock();
        condition.await();

    }
}
