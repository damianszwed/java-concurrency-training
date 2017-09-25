package pl.training.concurrency.chat.v1;

import java.net.Socket;

public class ConnectionWorker implements Runnable {

    private MessageWriter writer;
    private Socket socket;
    private Workers workers;

    public ConnectionWorker(Socket socket, Workers workers) {
        this.socket = socket;
        this.workers = workers;
        writer = new MessageWriter(socket);
    }

    @Override
    public void run() {
       new MessageReader(socket, message -> workers.broadcast(message), () -> workers.remove(this)).read();
    }

    public void send(String message) {
        writer.write(message);
    }

}
