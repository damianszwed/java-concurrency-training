package pl.training.concurrency.ex004;

public class CleaningTask implements Runnable {

    @Override
    public void run() {
        System.out.println("Closing files...");
    }

}
