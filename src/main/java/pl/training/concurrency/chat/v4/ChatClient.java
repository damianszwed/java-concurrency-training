package pl.training.concurrency.chat.v4;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.io.IOException;
import java.net.Socket;

public class ChatClient {

    private static final int SERVER_PORT = 1234;
    private static final String SERVER_HOST = "localhost";

    private void start(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        Connection connection = new Connection(socket);

        Observable.create(new ObservableInputStream(System.in))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(connection::send);

        Observable.create(new ObservableSocket(socket))
                .subscribe(System.out::println);
    }

    public static void main(String[] args) throws IOException {
        new ChatClient().start(SERVER_HOST, SERVER_PORT);
    }

}
