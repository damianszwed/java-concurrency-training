package pl.training.concurrency.ex013;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class Producer implements Runnable {

    private List<String> buffer = new ArrayList<>();
    private Exchanger<List<String>> exchanger;

    public Producer(Exchanger<List<String>> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        System.out.println("Producer: " + buffer.size());
        for (int i = 0; i < 10; i++) {
            buffer.add("" + i);
            System.out.println("Producing...");
        }
        try {
            buffer = exchanger.exchange(buffer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
