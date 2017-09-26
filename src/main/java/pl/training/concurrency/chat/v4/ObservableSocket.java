package pl.training.concurrency.chat.v4;

import java.io.IOException;
import java.net.Socket;

public class ObservableSocket extends ObservableInputStream {

    public ObservableSocket(Socket socket) throws IOException {
        super(socket.getInputStream());
    }

}
