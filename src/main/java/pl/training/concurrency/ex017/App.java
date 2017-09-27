package pl.training.concurrency.ex017;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class App {

    private final static NumberFormat numberFormat = NumberFormat.getNumberInstance();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 100_000_000; i++) {
            Product product = new Product("", 1);
            products.add(product);
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        UpdatePriceTask updatePriceTask = new UpdatePriceTask(products, 50, 0, products.size() - 1, 10_000_000);
        System.out.println("Start...");
        long startTime = System.nanoTime();
        forkJoinPool.execute(updatePriceTask);
        updatePriceTask.get();
        System.out.println("Time: " + numberFormat.format(System.nanoTime() - startTime) + " ns");
        forkJoinPool.shutdown();
    }

}
