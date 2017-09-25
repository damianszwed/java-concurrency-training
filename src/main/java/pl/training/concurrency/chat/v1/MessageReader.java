package pl.training.concurrency.chat.v1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageReader {

    private BufferedReader reader;
    private Consumer<String> onMessage;
    private Runnable onClose;
    private Logger logger = Logger.getLogger(getClass().getName());

    public MessageReader(InputStream inputStream, Consumer<String> onMessage) {
        this.onMessage = onMessage;
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public MessageReader(Socket socket, Consumer<String> onMessage, Runnable onClose) {
        this.onMessage = onMessage;
        this.onClose = onClose;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Creating input stream failed - " + ex.getMessage());
        }
    }

    public void read() {
        String message;
        try {
            while ((message = reader.readLine()) != null) {
                onMessage.accept(message);
            }
            onClose.run();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Read message failed - " + ex.getMessage());
            onClose.run();
        }
    }

}
