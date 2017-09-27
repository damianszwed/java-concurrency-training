package pl.training.concurrency.ex015;

import java.util.concurrent.Callable;

public class ReportGenerator implements Callable<String> {

    private static final int TIMEOUT = 5_000;

    @Override
    public String call() throws Exception {
        System.out.println("Generating report...");
        Thread.sleep(TIMEOUT);
        System.out.println("Done");
        return "New report";
    }

}
