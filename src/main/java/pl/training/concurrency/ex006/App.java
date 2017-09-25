package pl.training.concurrency.ex006;

public class App {

    public static void main(String[] args) {
        Counter counter = new Counter();
        new Thread(counter).start();
        new Thread(counter).start();
    }

}
