package pl.training.concurrency.chat.v1;

import java.util.ArrayList;
import java.util.List;

public class Workers {

    private List<ConnectionWorker> workers = new ArrayList<>();

    public synchronized void add(ConnectionWorker worker) {
        workers.add(worker);
    }

    public synchronized void broadcast(String message) {
        workers.forEach(worker -> worker.send(message));
    }

    public synchronized void remove(ConnectionWorker worker) {
        workers.remove(worker);

    }

}
