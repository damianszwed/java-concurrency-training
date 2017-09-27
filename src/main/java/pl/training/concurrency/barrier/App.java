package pl.training.concurrency.barrier;

public class App {

    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();
        Barrier barrier = new Barrier();
        barrier.onStart = () -> System.out.println("Started...");
        barrier.start(task, task, task);
    }

}
