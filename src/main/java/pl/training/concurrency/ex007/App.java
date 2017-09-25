package pl.training.concurrency.ex007;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App {

    private static final int PRINTER_QUEUE_SIZE = 10;
    private static final int THREADS_COUNT = 100;

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition storeIsFull = lock.newCondition();
        Condition storeIsNotFull = lock.newCondition();
        Queue<PrintTask> printingQueue = new LinkedList<>();
        new Thread(new Printer(printingQueue, lock, storeIsFull, storeIsNotFull)).start();
        for (int i = 0; i < THREADS_COUNT; i++) {
            new Thread(new Employee(printingQueue, PRINTER_QUEUE_SIZE, lock, storeIsFull, storeIsNotFull)).start();
        }
    }

}
