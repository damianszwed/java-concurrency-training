package pl.training.concurrency.chat.v3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ChatRooms {

    public static final String MAIN_ROOM = "MAIN_ROOM";

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private Map<String, Workers> rooms = new HashMap<>();

    public ChatRooms() {
        rooms.put(MAIN_ROOM, new Workers());
    }

    public void create(String name) {
        readWriteLock.writeLock().lock();
        if (!rooms.containsKey(name)) {
            rooms.put(name, new Workers());
        }
        readWriteLock.writeLock().unlock();
    }

    public void join(String name, ConnectionWorker worker) {
        readWriteLock.writeLock().lock();
        rooms.get(name).add(worker);
        readWriteLock.writeLock().unlock();
    }

    public void broadcast(String currentRoom, String message) {
        readWriteLock.readLock().lock();
        rooms.get(currentRoom).broadcast("(" + currentRoom + "): " + message);
        readWriteLock.readLock().unlock();
    }

    public void remove(String roomName, ConnectionWorker worker) {
        readWriteLock.writeLock().lock();
        rooms.get(roomName).remove(worker);
        readWriteLock.writeLock().lock();
    }

}
