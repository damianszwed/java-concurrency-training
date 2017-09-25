package pl.training.concurrency.ex002;

import java.util.Random;

public class Task implements Runnable {

    private static final int MAX_TIMEOUT = 10_000;

    private Random random = new Random();

    @Override
    public void run() {
        try {
            Thread.sleep(random.nextInt(MAX_TIMEOUT));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Thread %s finished\n", Thread.currentThread().getName());
    }

}
