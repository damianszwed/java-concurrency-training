package pl.training.concurrency.ex002;

public class App {

    private static final int WAIT_TIME = 11_000;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Task(), "task");
        thread.start();
        thread.join(WAIT_TIME);
        System.out.printf("Thread %s finished\n", Thread.currentThread().getName());
    }

}
