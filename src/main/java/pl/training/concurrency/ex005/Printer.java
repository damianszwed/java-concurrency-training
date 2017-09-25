package pl.training.concurrency.ex005;

import java.util.Queue;

public class Printer implements Runnable {

    private Queue<PrintTask> printingQueue;

    public Printer(Queue<PrintTask> printingQueue) {
        this.printingQueue = printingQueue;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (printingQueue) {
                waitIfQueueIsEmpty();
                print();
            }
        }
    }

    private void print() {
        printingQueue.poll().execute();
        printingQueue.notifyAll();
    }

    private void waitIfQueueIsEmpty() {
        while (printingQueue.isEmpty()) {
            try {
                printingQueue.wait();
            } catch (InterruptedException e) {
                System.out.println("Printing was interrupted...");
            }
        }
    }

}
