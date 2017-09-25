package pl.training.concurrency.chat.v1;

import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

public class ChatClient {

    private static final int SERVER_PORT = 1234;
    private static final String SERVER_HOST = "localhost";

    private MessageWriter messageWriter;
    private String user;
    private Consumer<String> onMessage = message -> messageWriter.write(user + ": " + message);
    private Runnable readFromSocket;
    private Runnable readFromConsole = () -> new MessageReader(System.in, onMessage);

    public ChatClient(String host, int port, String user) throws IOException {
        Socket socket = new Socket(host, port);
        messageWriter = new MessageWriter(socket);
        readFromSocket = () -> new MessageReader(socket, System.out::println).read();
        this.user = user;
    }

    public void start() throws IOException {
        new Thread(readFromSocket).start();

        /*Thread consoleMessageReader = new Thread(new Runnable() {

            @Override
            public void run() {
                new MessageReader(System.in, new Consumer<String>() {

                    @Override
                    public void accept(String message) {
                        messageWriter.write(user + ": " + message);
                    }

                });
            }

        });*/

        Thread consoleMessageReader = new Thread(readFromConsole);
        consoleMessageReader.setDaemon(true);
        consoleMessageReader.start();
    }

    public static void main(String[] args) throws IOException {
        new ChatClient(SERVER_HOST, SERVER_PORT, "Tomek").start();
    }

}
