package pl.training.concurrency.chat.v4;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer {

    private static final int SERVER_PORT = 1234;

    private List<RemoteClient> remoteClients = new ArrayList<>(); // do sprawdze
    private Logger logger = Logger.getLogger(getClass().getName());

    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            logger.log(Level.INFO, "Server starts listening on port " + port);
            ObservableServerSocket observableServerSocket = new ObservableServerSocket(serverSocket);
            Observable.create(observableServerSocket).subscribe(this::onSocketConnection);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Server failed to start - " + ex.getMessage());
        }
    }

    private void onSocketConnection(Socket socket) throws IOException {
        remoteClients.add(new RemoteClient(socket));
        ObservableSocket observableSocket = new ObservableSocket(socket);
        Observable.create(observableSocket)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(this::broadcast);
    }

    public static void main(String[] args) {
        new ChatServer().start(SERVER_PORT);
    }

    private void broadcast(String message) {
        remoteClients.forEach(remoteClient -> remoteClient.send(message));
    }

}
