package pl.training.concurrency.ex009;

import java.util.concurrent.*;

public class App {

    private static final int WAIT_TIME = 11;
    private static final int THREADS_COUNT = 2;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Double> power = new Power(2.0, 3.0);
        ExecutorService executorService  = Executors.newFixedThreadPool(THREADS_COUNT);
        Future<Double> powerResult = executorService.submit(power);
        executorService.shutdown();
        System.out.println("Waiting for result...");
        executorService.awaitTermination(WAIT_TIME, TimeUnit.SECONDS);
        System.out.println("Result: " + powerResult.get());
    }

}
