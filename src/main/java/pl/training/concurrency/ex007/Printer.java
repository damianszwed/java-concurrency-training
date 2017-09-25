package pl.training.concurrency.ex007;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Printer implements Runnable {

    private Queue<PrintTask> printingQueue;
    private Lock lock;
    private Condition storeIsFull;
    private Condition storeIsNotFull;

    public Printer(Queue<PrintTask> printingQueue, Lock lock, Condition storeIsFull, Condition storeIsNotFull) {
        this.printingQueue = printingQueue;
        this.lock = lock;
        this.storeIsFull = storeIsFull;
        this.storeIsNotFull = storeIsNotFull;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            waitIfQueueIsEmpty();
            print();
            lock.unlock();
        }
    }

    private void print() {
        printingQueue.poll().execute();
        storeIsFull.signalAll();
    }

    private void waitIfQueueIsEmpty() {
        while (printingQueue.isEmpty()) {
            try {
                storeIsNotFull.await();
            } catch (InterruptedException e) {
                System.out.println("Printing was interrupted...");
            }
        }
    }

}
