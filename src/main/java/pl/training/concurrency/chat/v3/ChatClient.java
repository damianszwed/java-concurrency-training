package pl.training.concurrency.chat.v3;

import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

public class ChatClient {

    private static final int SERVER_PORT = 1234;
    private static final String SERVER_HOST = "localhost";

    private Consumer<String> onMessage;
    private Runnable readFromSocket;
    private Runnable readFromConsole;

    public ChatClient(String host, int port, String user) throws IOException {
        Socket socket = new Socket(host, port);
        onMessage = message -> new MessageWriter(socket).write(message.startsWith("#") ? message : user + ": " + message);
        readFromSocket = () -> new MessageReader(socket, System.out::println, () -> {}).read();
        readFromConsole = () -> new MessageReader(System.in, onMessage).read();
    }

    public void start() throws IOException {
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
        new Thread(readFromSocket).start();
        Thread consoleMessageReader = new Thread(readFromConsole);
        consoleMessageReader.setDaemon(true);
        consoleMessageReader.start();
    }

    public static void main(String[] args) throws IOException {
        new ChatClient(SERVER_HOST, SERVER_PORT, "Jan").start();
    }

}
