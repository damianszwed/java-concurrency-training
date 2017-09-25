package pl.training.concurrency.chat.v1;

import java.io.IOException;
import java.net.Socket;

public class ChatClient {

    private static final int SERVER_PORT = 1234;
    private static final String SERVER_HOST = "localhost";

    private MessageWriter messageWriter;
    private Socket socket;
    private String user;

    public ChatClient(String host, int port, String user) throws IOException {
        socket = new Socket(host, port);
        messageWriter = new MessageWriter(socket);
        this.user = user;
    }

    public void start() throws IOException {
        new Thread(() -> new MessageReader(socket, System.out::println).read()).start();

        Thread consoleMessageReader = new Thread(() -> new MessageReader(System.in, message -> messageWriter.write(user + ": " + message)));
        consoleMessageReader.setDaemon(true);
        consoleMessageReader.start();
    }

    public static void main(String[] args) throws IOException {
        new ChatClient(SERVER_HOST, SERVER_PORT, "Tomek").start();
    }

}
