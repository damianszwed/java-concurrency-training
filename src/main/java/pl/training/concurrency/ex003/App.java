package pl.training.concurrency.ex003;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class App {

    private static final int MAX_TIMEOUT = 1_500;

    public static void main(String[] args) throws InterruptedException {
        Queue<Long> values = new LinkedList<>(Arrays.asList(2L, 3L, 4L));
        TaskProcessor taskProcessor = new TaskProcessor(values);
        Thread thread = new Thread(taskProcessor);
        thread.start();
        thread.join(MAX_TIMEOUT);
        thread.interrupt();
    }

}
