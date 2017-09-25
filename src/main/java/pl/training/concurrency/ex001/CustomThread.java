package pl.training.concurrency.ex001;

public class CustomThread extends Thread {

    @Override
    public void run() {
        while (true) {
            System.out.println("Current thread: " + Thread.currentThread().getName());
        }
    }

}
