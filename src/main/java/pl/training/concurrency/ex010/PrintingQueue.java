package pl.training.concurrency.ex010;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class PrintingQueue {

    private static final int MAX_TIMEOUT = 10_000;

    private Semaphore semaphore;
    private Random random = new Random();

    public PrintingQueue(int size) {
         semaphore = new Semaphore(size);
    }

    public void add(String document) {
        try {
            semaphore.acquire();
            int printTime = random.nextInt(MAX_TIMEOUT);
            System.out.format("Thread: %s, Printing time: %d ms\n", Thread.currentThread().getName(), printTime);
            Thread.sleep(printTime);
            System.out.format("Thread: %s, releasing\n", Thread.currentThread().getName());
            semaphore.release();
        } catch (InterruptedException e) {
            System.out.println("Thread was interrupted");
        }

    }

}
