package pl.training.concurrency.chat.v1;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionWorker implements Runnable {

    private MessageWriter writer;
    private Socket socket;
    private Workers workers;
    private Logger logger = Logger.getLogger(getClass().getName());

    public ConnectionWorker(Socket socket, Workers workers) {
        this.socket = socket;
        this.workers = workers;
        writer = new MessageWriter(socket);
    }

    @Override
    public void run() {
        try {
            new MessageReader(socket.getInputStream(), message -> workers.broadcast(message)).run();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Send message failed - " + ex.getMessage());
        }
    }

    public void send(String message) {
        writer.write(message);
    }

}
