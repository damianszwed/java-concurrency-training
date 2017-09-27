package pl.training.concurrency.ex019;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class TaskWithPhases implements Runnable, Comparable<Long>{

    private static final int TIMEOUT = 500;

    private long priority;
    private Phaser phaser;

    public TaskWithPhases(long priority, Phaser phaser) {
        this.priority = priority;
        this.phaser = phaser;
    }

    @Override
    public int compareTo(Long priority) {
        if (this.priority < priority) {
            return 1;
        }
        if (this.priority > priority) {
            return -1;
        }
        return 0;
    }

    @Override
    public void run() {
        phaser.arrive();
        System.out.printf("Phase 1 started by %s\n", Thread.currentThread().getName());
        sleep();
        System.out.printf("Phase 1 finished by %s\n", Thread.currentThread().getName());
        phaser.arriveAndAwaitAdvance();
        System.out.printf("Phase 2 started by %s\n", Thread.currentThread().getName());
        sleep();
        System.out.printf("Phase 2 finished by %s\n", Thread.currentThread().getName());
        phaser.arriveAndAwaitAdvance();
        System.out.printf("Phase 3 started by %s\n", Thread.currentThread().getName());
        sleep();
        System.out.printf("Phase 3 finished by %s\n", Thread.currentThread().getName());
        phaser.arriveAndDeregister();
    }

    private void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
