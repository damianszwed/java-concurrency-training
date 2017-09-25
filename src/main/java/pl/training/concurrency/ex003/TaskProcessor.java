package pl.training.concurrency.ex003;

import java.util.Queue;

public class TaskProcessor implements Runnable {

    private static final int MAX_TIMEOUT = 1_000;

    private Queue<Long> values;

    public TaskProcessor(Queue<Long> values) {
        this.values = values;
    }

    @Override
    public void run() {
        if (!Thread.currentThread().isInterrupted()) {
            try {
                while (!values.isEmpty()) {
                    Thread.sleep(MAX_TIMEOUT);
                    System.out.printf("Processing: %s\n", values.poll());
                }
            } catch (InterruptedException e) {
                System.out.println("Processing remaining values...");
            }
        }
    }

}
