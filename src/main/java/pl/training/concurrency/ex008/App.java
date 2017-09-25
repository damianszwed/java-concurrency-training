package pl.training.concurrency.ex008;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class App {

    private static final int TIMEOUT = 1_000;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Double> power = new Power(2.0, 3.0);
        FutureTask<Double> futureTask = new FutureTask<>(power);
        new Thread(futureTask).start();

        while (!futureTask.isDone()) {
            System.out.println("Waiting for result...");
            Thread.sleep(TIMEOUT);
        }
        System.out.println("Result: " + futureTask.get());
    }

}
