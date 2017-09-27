package pl.training.concurrency.ex012;

import java.util.concurrent.CyclicBarrier;

public class App {

    private static final int TIMEOUT = 1_000;

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        for(int i = 0; i < 5; i++) {
            new Thread(new Service(cyclicBarrier)).start();
            Thread.sleep(TIMEOUT);
            new Thread(new Service(cyclicBarrier)).start();
        }
    }

}
