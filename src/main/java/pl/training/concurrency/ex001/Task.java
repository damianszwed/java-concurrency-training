package pl.training.concurrency.ex001;

public class Task implements Runnable {

    public void run() {
        while (true) {
            System.out.println("Current thread: " + Thread.currentThread().getName());
        }
    }

}
