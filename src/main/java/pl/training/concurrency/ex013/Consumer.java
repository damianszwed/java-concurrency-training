package pl.training.concurrency.ex013;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class Consumer implements Runnable {

    private List<String> buffer = new ArrayList<>();
    private Exchanger<List<String>> exchanger;

    public Consumer(Exchanger<List<String>> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            buffer = exchanger.exchange(buffer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Consumer: " + buffer.size());
        buffer.forEach(System.out::println);
        buffer.clear();
    }

}
