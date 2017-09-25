package pl.training.concurrency.ex004;

public class App {

    public static void main(String[] args) {
        Thread thread = new Thread(new CleaningTask());
        Runtime.getRuntime().addShutdownHook(thread);
        System.out.println("VM is going down...");
    }

}
