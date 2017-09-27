package pl.training.concurrency;

import edu.umd.cs.mtc.MultithreadedTestCase;
import org.junit.Before;

import java.util.concurrent.LinkedTransferQueue;

public class ProducerConsumerTest extends MultithreadedTestCase {

    private LinkedTransferQueue<String> storage = new LinkedTransferQueue<>();

    @Before
    public void init() {
        super.initialize();
        setTrace(true);
        System.out.println("Init...");
    }

    @Override
    public void finish() {
        super.finish();
        assertTrue(storage.isEmpty());
    }

    public void thread1() throws InterruptedException {
        System.out.println("Thread1: " + storage.take());
    }

    public void thread2() throws InterruptedException {
        waitForTick(2);
        System.out.println("Thread2: " + storage.take());
    }

    public void thread3() throws InterruptedException {
        waitForTick(1);
        storage.put("Java");
        waitForTick(2);
        waitForTick(1);
        storage.put("Scala");
        System.out.println("Thread3: inserted 2 elements");
    }

}
