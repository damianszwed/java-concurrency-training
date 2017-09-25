package pl.training.concurrency.ex001;

public class App {

    public static void main(String[] args) {
        Task task = new Task();
        Thread threadOne = new Thread(task);
        Thread threadTwo = new Thread(task);
        Thread threadThree = new CustomThread();
        threadOne.start();
        threadTwo.start();
        threadThree.start();
    }

}
