package pl.training.concurrency.cache;

import java.util.HashMap;

public class App {

    private static final int TIMEOUT = 2_000;

    public static void main(String[] args) throws InterruptedException {
        ConcurrentCache<Long, String> concurrentCache = new ConcurrentCache<>(new HashMap<>(), 2);
        concurrentCache.put(1L, "a");
        concurrentCache.put(2L, "b");
        concurrentCache.get(2L);
        concurrentCache.put(3L, "c");

        Thread.sleep(TIMEOUT);
    }

}
