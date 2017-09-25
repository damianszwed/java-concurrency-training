package pl.training.concurrency.ex005;

import java.util.UUID;

public class PrintTask {

    public void execute() {
        System.out.printf("Printing (task id: %s)\n", UUID.randomUUID());
    }

}
