package pl.training.concurrency.chat.v4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Connections {

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private List<Connection> connections = new ArrayList<>();

    public void add(Connection connection) {
        readWriteLock.writeLock().lock();
        connections.add(connection);
        readWriteLock.writeLock().unlock();
    }

    public void broadcast(String message) {
        readWriteLock.readLock().lock();
        connections.forEach(connection -> connection.send(message));
        readWriteLock.readLock().unlock();
    }

}
