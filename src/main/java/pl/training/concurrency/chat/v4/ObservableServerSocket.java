package pl.training.concurrency.chat.v4;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import java.net.ServerSocket;
import java.net.Socket;

public class ObservableServerSocket implements ObservableOnSubscribe<Socket> {

    private ServerSocket serverSocket;

    public ObservableServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void subscribe(ObservableEmitter<Socket> observableEmitter) throws Exception {
        while (true) {
            Socket socket = serverSocket.accept();
            observableEmitter.onNext(socket);
        }
    }

}
