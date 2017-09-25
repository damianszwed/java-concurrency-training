package pl.training.concurrency.chat.v1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer {

    private static final int SERVER_PORT = 1234;

    private Workers workers = new Workers();
    private Logger logger = Logger.getLogger(getClass().getName());

    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            startListening(port, serverSocket);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Server failed to start - " + ex.getMessage());
        }
    }

    private void startListening(int port, ServerSocket serverSocket) throws IOException {
        logger.log(Level.INFO, "Server starts listening on port " + port);
        while (true) {
            Socket socket = serverSocket.accept();
            logger.log(Level.INFO, "New connection established...");
            ConnectionWorker worker = new ConnectionWorker(socket, workers);
            workers.add(worker);
            new Thread(worker).start();
        }
    }

    public static void main(String[] args) {
        new ChatServer().start(SERVER_PORT);
    }

}
