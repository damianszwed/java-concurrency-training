package pl.training.concurrency.chat.v3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Workers {

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private List<ConnectionWorker> workers = new ArrayList<>();

    public void add(ConnectionWorker worker) {
        readWriteLock.writeLock().lock();
        workers.add(worker);
        readWriteLock.writeLock().unlock();
    }

    public void broadcast(String message) {
        readWriteLock.readLock().lock();
        workers.forEach(worker -> worker.send(message));
        readWriteLock.readLock().unlock();
    }

    public void remove(ConnectionWorker worker) {
        readWriteLock.writeLock().lock();
        workers.remove(worker);
        readWriteLock.writeLock().unlock();
    }

}
