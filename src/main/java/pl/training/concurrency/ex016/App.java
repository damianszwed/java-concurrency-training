package pl.training.concurrency.ex016;

import java.util.concurrent.CompletableFuture;

public class App {

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5_000);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        completableFuture.thenAccept(aVoid -> System.out.println("1"))
              .thenAccept(aVoid -> System.out.println("2"));


        CompletableFuture<Void> completableFutureWithResult = CompletableFuture.supplyAsync(() -> "Result")
                .thenAccept(result -> System.out.println(result));

        Thread.sleep(10_000);
    }

}
