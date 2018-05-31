package com.dragonfly;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Arrays.asList;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        for (int i = 2; i <= 10; i++) {
//            new Thread(new FibonacciTask(i)).start();
        }
        System.out.print("task over!");

        // Executors

        ArrayList<Future<List<Integer>>> futures = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();

        // executorService.awaitTermination();

        for (int i = 2; i <= 10; i++) {
            futures.add(executorService.submit(new FibonacciTask(i)));
        }
        for (Future<List<Integer>> listFuture : futures) {
            List<Integer> list = listFuture.get();
            for (Integer var : list) {
                System.out.print("#" + var);
            }
            System.out.println("\n-------------------------------");
        }

        ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<>();
        hashMap.values().iterator();
        HashMap<String, String> map = new HashMap<>(); // HashMap 通过快速失败解决put时候多线程同步问题
        // Collections.synchronizedMap()  SynchronizedMap
    }
}

class FibonacciTask implements Callable<List<Integer>> {

    private int n;

    private static int taskCount = 0;
    private final int taskId = taskCount++;

    FibonacciTask(int count) {
        this.n = count;
    }


    private List<Integer> fibonacciRecursive(int n) {
        Integer[] fibs = new Integer[n];
        fibs[0] = 0;
        fibs[1] = 1;
        for (int i = 2; i < n; i++) {
            fibs[i] = fibs[i - 2] + fibs[i - 1];
        }
        return asList(fibs);
    }

    private void print(int[] fibs) {
        System.out.print("当前执行的任务是:" + taskId);
        for (int fib : fibs) {
            System.out.print(fib + " ");
        }
    }

    @Override
    public List<Integer> call() throws Exception {
        return fibonacciRecursive(n);
    }
}
// 1.线程返回数据

// 2.定制线程池

// 3.线程中抛出异常 线程中抛出的异常try catch捕捉不到，需要通过专门的异常的异常处理器去处理

// 4.synchronized 和 lock区别, lock更加的灵活

// 5.volatile 关键字，可见性，同步也会导致主存刷新，比如说一个域由synchronized包围就不需要volatile关键字，volatile关键字不提倡使用,
// volatile关键字不会保证原子性，使用volatile修饰的变量每次从操作时候会从主存中重新获取值
// Happens-before原则，想保证执行动作B的线程看到动作A的结果（无论A和B是否发生在同一个线程中），A和B之间就必须满足happens-before关系。

// 6.原子类 Atomic类被设计用来构建java.util.concurrent中的类

// 7.synchronized 同步块 synchronized(this)

// 8.synchronized 锁默认加在this上

// 9.线程本地存储

// 10.线程终结方式

// 11.线程的集中几种状态
// 1):新建状态：资源初始化
// 2):就绪状态：等待cpu分配时间
// 3):阻塞状态：线程可以运行，但是某个条件阻止它运行，cpu此时不会分配时间
// 4):死亡状态：不可以再调度

// 12.线程阻塞时被打断会抛出异常,exec.shutdownNow()会向所有线程发送interrupt(),而且在io和synchronized上不可中断

// 13.ReentrantLock上阻塞的任务可以被打断，也叫可重入锁

// 14.通过interrupted()可以检测到状态,从而可以通过这种方式退出线程

// 15.wait()调用会释放对象上的锁,并且只能在同步控制块中或者同步控制方法中调用

// 16.notify 和 notifyAll 区别

// 17.Condition使用，使用await挂起任务 使用signalAll唤醒任务，比较复杂，在更加困难的多线程中使用

// 18.几种阻塞队列的区别，生产者 消费者封装好的解决方案

// 19.哲学家进餐问题 死锁构成的几个必要条件：

// 20. 新类库里面的构件 CountDownLatch 同步多个任务，多个任务都完成的时候才能继续

// 21.CyclicBarrier可以多次使用,玩一个赛马游戏

// 22.DelayedQueue延迟队列，任务到期才能取出来，如果多个任务到期根据排序获取一个任务执行

// 23.PriorityBockingQueue优先级队列, 根据优先级获取队列中的元素，线程安全的

// 24.信号量  Semaphore 允许n个任务同时访问一个资源,迁入迁出对象

// 25.Exchanger 两个线程之间交换数据，主要应用场景，一个任务在创建对象，对象创建代价高昂，
// 另一个任务在消费这些对象，使用Exchanger在一边生产的同时也可以一边消费

// 26.并发类比较

