package pl.training.concurrency.ex005;

import java.util.Queue;

public class Employee implements Runnable {

    private Queue<PrintTask> printingQueue;
    private int tasksLimit;

    public Employee(Queue<PrintTask> printingQueue, int tasksLimit) {
        this.printingQueue = printingQueue;
        this.tasksLimit = tasksLimit;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (printingQueue) {
                waitIfQueueIsFull();
                createTask();
            }
        }
    }

    private void createTask() {
        System.out.println("Creating new task...");
        printingQueue.add(new PrintTask());
        printingQueue.notify();
    }

    private void waitIfQueueIsFull() {
        while (tasksLimit == printingQueue.size()) {
            try {
                System.out.println("Queue limit reached...");
                printingQueue.wait();
            } catch (InterruptedException e) {
                System.out.println("Employee was interrupted...");
            }
        }
    }

}
