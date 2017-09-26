package pl.training.concurrency.chat.v3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer {

    private static final int SERVER_PORT = 1234;
    private static final int CONNECTIONS_LIMIT = 1024;

    private ChatRooms chatRooms = new ChatRooms();
    private Logger logger = Logger.getLogger(getClass().getName());
    private ExecutorService executorService;

    public ChatServer(ExecutorService executorService) {
        this.executorService = executorService;
    }

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
            ConnectionWorker worker = new ConnectionWorker(socket, chatRooms);
            chatRooms.join(ChatRooms.MAIN_ROOM, worker);
            executorService.execute(worker);
        }
    }

    public static void main(String[] args) {
        new ChatServer(Executors.newFixedThreadPool(CONNECTIONS_LIMIT)).start(SERVER_PORT);
    }

}
