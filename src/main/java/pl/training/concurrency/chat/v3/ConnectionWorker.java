package pl.training.concurrency.chat.v3;

import java.net.Socket;

public class ConnectionWorker implements Runnable {

    private MessageWriter writer;
    private Socket socket;
    private ChatRooms chatRooms;
    private String currentRoom = ChatRooms.MAIN_ROOM;

    public ConnectionWorker(Socket socket, ChatRooms chatRooms) {
        this.socket = socket;
        this.chatRooms = chatRooms;
        writer = new MessageWriter(socket);
    }

    @Override
    public void run() {
       new MessageReader(socket, this::onMessage, () -> chatRooms.remove(currentRoom, this)).read();
    }

    private void onMessage(String message) {
        if (message.startsWith("#")) {
            join(message.split("#")[1]);
        } else {
            chatRooms.broadcast(currentRoom, message);
        }
    }

    private void join(String roomName) {
        chatRooms.create(roomName);
        chatRooms.join(roomName, this);
        currentRoom = roomName;
    }

    public void send(String message) {
        writer.write(message);
    }

}
