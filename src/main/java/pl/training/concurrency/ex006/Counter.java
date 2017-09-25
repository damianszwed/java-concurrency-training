package pl.training.concurrency.ex006;

import java.util.Random;

public class Counter implements Runnable {

    private static final int MAX_TIMEOUT = 1_000;
    private static final long COUNTER_INIT_VALUE = 1;

    private Random random = new Random();
    private ThreadLocal<Long> counter = new ThreadLocal<>();

    @Override
    public void run() {
        counter.set(COUNTER_INIT_VALUE);
        while (true) {
            try {
                Thread.sleep(random.nextInt(MAX_TIMEOUT));
                counter.set(counter.get() + 1);
                System.out.printf("Thread: %s, counter value: %d\n", Thread.currentThread().getName(), counter.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
