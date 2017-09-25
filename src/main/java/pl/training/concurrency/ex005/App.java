package pl.training.concurrency.ex005;

import java.util.LinkedList;
import java.util.Queue;

public class App {

    private static final int PRINTER_QUEUE_SIZE = 10;
    private static final int THREADS_COUNT = 100;

    public static void main(String[] args) {
        Queue<PrintTask> printingQueue = new LinkedList<>();
        new Thread(new Printer(printingQueue)).start();
        for (int i = 0; i < THREADS_COUNT; i++) {
            new Thread(new Employee(printingQueue, PRINTER_QUEUE_SIZE)).start();
        }
    }

}
