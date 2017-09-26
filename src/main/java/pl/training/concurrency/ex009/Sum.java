package pl.training.concurrency.ex009;

import java.util.concurrent.Callable;

public class Sum implements Callable<Long> {

    private static final int TIMEOUT = 10_000;

    private long elementOne;
    private long elementTwo;

    public Sum(long elementOne, long elementTwo) {
        this.elementOne = elementOne;
        this.elementTwo = elementTwo;
    }

    @Override
    public Long call() throws Exception {
        Thread.sleep(TIMEOUT);
        return elementOne + elementTwo;
    }

}
