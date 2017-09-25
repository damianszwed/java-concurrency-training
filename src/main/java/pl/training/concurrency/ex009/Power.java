package pl.training.concurrency.ex009;

import java.util.concurrent.Callable;

public class Power implements Callable<Double> {

    private static final int TIMEOUT = 10_000;

    private double base;
    private double exponent;

    public Power(double base, double exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    @Override
    public Double call() throws Exception {
        Thread.sleep(TIMEOUT);
        return Math.pow(base, exponent);
    }

}
