package pl.training.concurrency.lock;

import org.junit.Before;
import org.junit.Test;
import pl.training.concurrency.utils.TestLock;

import java.util.Collection;

public class TaskTest {

    private static final int TIMEOUT = 1_000;

    private TestLock testLock = new TestLock();
    private Task task = new Task(testLock);

    @Before
    public void init() {
        for (int i = 0; i < 10; i++) {
            new Thread(task).start();
        }
    }


    @Test
    public void testRun() throws InterruptedException {
        while (testLock.hasQueuedThreads()) {
            Collection<Thread> lockedThreads = testLock.getLockedThreads();
            System.out.println("Locked threads: " + lockedThreads.size());
            System.out.println("Lock owner: " + testLock.getOwner().getName());
            //lockedThreads.forEach(thread -> System.out.println(thread.getName()));
            Thread.sleep(TIMEOUT);
        }
    }

}
