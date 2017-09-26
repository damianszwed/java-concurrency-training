package pl.training.concurrency.ex010;

public class App {

    public static void main(String[] args) {
        PrintingQueue printingQueue = new PrintingQueue(5);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> printingQueue.add("Document")).start();
        }
    }

}
