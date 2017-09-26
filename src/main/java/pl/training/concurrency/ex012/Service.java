package pl.training.concurrency.ex012;

import java.util.UUID;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Service implements Runnable {

    private static final int TIMEOUT = 300;

    private CyclicBarrier cyclicBarrier;

    public Service(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        String id = UUID.randomUUID().toString();
        System.out.printf("Service %s started...\n", id);
        try {
            Thread.sleep(TIMEOUT);
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.printf("Service %s closed...\n", id);
    }

}
