package pl.training.concurrency.ex009;

import java.util.ArrayList;
import java.util.List;
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

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(new Sum(2, 3), 300, TimeUnit.SECONDS);

        List<Sum> sumList = new ArrayList<>();
        sumList.add(new Sum(2, 3));
        sumList.add(new Sum(4, 5));

        ThreadPoolExecutor threadPoolExecutor =  (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        List<Future<Long>> results = threadPoolExecutor.invokeAll(sumList);
        Runnable resultsChecker = () -> results.stream()
                .filter(Future::isDone)
                .forEach(longFuture -> {
                    try {
                        System.out.print(longFuture.get() + " ");
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });
        scheduledExecutorService.scheduleAtFixedRate(resultsChecker, 1, 2, TimeUnit.SECONDS);
    }

}
