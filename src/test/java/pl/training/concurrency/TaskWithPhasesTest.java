package pl.training.concurrency;

import org.junit.Before;
import org.junit.Test;
import pl.training.concurrency.ex019.TaskWithPhases;
import pl.training.concurrency.utils.TestExecutor;
import pl.training.concurrency.utils.TestPhaser;
import pl.training.concurrency.utils.TestThreadFactory;

import java.util.concurrent.*;

public class TaskWithPhasesTest {

    private static final int PARTIES = 3;

    private Phaser phaser = new TestPhaser(PARTIES);
    private ThreadPoolExecutor executor= new TestExecutor(3, 10, 10,
            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));

    @Before
    public void init() {
        executor.setThreadFactory(new TestThreadFactory());
        for (int i = 1; i <= PARTIES; i++) {
            executor.execute(new TaskWithPhases(i, phaser));
        }
    }

    @Test
    public void testRun() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        executor.shutdown();
    }

}
