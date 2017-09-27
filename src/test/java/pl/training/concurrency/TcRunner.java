package pl.training.concurrency;

import edu.umd.cs.mtc.TestFramework;

public class TcRunner {

    public static void main(String[] args) throws Throwable {
        ProducerConsumerTest producerConsumerTest = new ProducerConsumerTest();
        TestFramework.runManyTimes(producerConsumerTest, 10);
    }

}
