package pl.training.concurrency.barrier;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Barrier {

    public Runnable onStart = () -> {};

    private CyclicBarrier cyclicBarrier;
    private Runnable notifyOnStart = () -> onStart.run();

    public void start(Runnable ... runnables) {
        cyclicBarrier = new CyclicBarrier(runnables.length + 1);
        new Thread(toSynchronizedRunnable(notifyOnStart)).start();
        Arrays.stream(runnables)
                .map(this::toSynchronizedRunnable)
                .map(Thread::new)
                .forEach(Thread::start);
    }

    private Runnable toSynchronizedRunnable(Runnable runnable) {
        return () -> {
            try {
                cyclicBarrier.await();
                runnable.run();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        };
    }

}
