package pl.training.concurrency.chat.v2;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable e) {
        Logger logger = Logger.getLogger(thread.getName());
        logger.log(Level.SEVERE, e.getMessage());
    }

}
