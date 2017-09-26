package pl.training.concurrency.ex011;

import java.util.concurrent.CountDownLatch;

public class Meeting implements Runnable {

    private CountDownLatch countDownLatch;

    public Meeting(int participants) {
        countDownLatch = new CountDownLatch(participants);
    }

    public void add(String participantName) {
        countDownLatch.countDown();
        System.out.printf("Participant %s connected\n", participantName);
    }

    @Override
    public void run() {
        System.out.println("Initializng meeting");
        try {
            countDownLatch.await();
            System.out.println("Starting...");
        } catch (InterruptedException e) {
            System.out.println("Meeting has ended");
        }
    }

}
