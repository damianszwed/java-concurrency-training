package pl.training.concurrency.ex007;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Employee implements Runnable {

    private Queue<PrintTask> printingQueue;
    private int tasksLimit;
    private Lock lock;
    private Condition storeIsFull;
    private Condition storeIsNotFull;

    public Employee(Queue<PrintTask> printingQueue, int tasksLimit, Lock lock, Condition storeIsFull, Condition storeIsNotFull) {
        this.printingQueue = printingQueue;
        this.tasksLimit = tasksLimit;
        this.lock = lock;
        this.storeIsFull = storeIsFull;
        this.storeIsNotFull = storeIsNotFull;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            waitIfQueueIsFull();
            createTask();
            lock.unlock();
        }
    }

    private void createTask() {
        System.out.println("Creating new task...");
        printingQueue.add(new PrintTask());
        storeIsNotFull.signal();
    }

    private void waitIfQueueIsFull() {
        while (tasksLimit == printingQueue.size()) {
            try {
                System.out.println("Queue limit reached...");
                storeIsFull.await();
            } catch (InterruptedException e) {
                System.out.println("Employee was interrupted...");
            }
        }
    }

}
