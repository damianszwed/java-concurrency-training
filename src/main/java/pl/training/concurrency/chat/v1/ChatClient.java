package pl.training.concurrency.chat.v1;

import java.io.IOException;
import java.net.Socket;

public class ChatClient {

    private MessageWriter messageWriter;
    private Socket socket;
    private String user;

    public ChatClient(String host, int port, String user) throws IOException {
        socket = new Socket(host, port);
        messageWriter = new MessageWriter(socket);
        this.user = user;
    }

    public void start() throws IOException {
        MessageReader serverMessageReader = new MessageReader(socket.getInputStream(), System.out::println);
        serverMessageReader.start();

        MessageReader consoleMessageReader = new MessageReader(System.in, message -> messageWriter.write(user + ": " + message));
        consoleMessageReader.setDaemon(true);
        consoleMessageReader.start();
    }

    public static void main(String[] args) throws IOException {
        new ChatClient("localhost", 1234, "Tomek").start();
    }

}
