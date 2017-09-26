package pl.training.concurrency.chat.v3;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageWriter {

    private PrintWriter writer;
    private Logger logger = Logger.getLogger(getClass().getName());

    public MessageWriter(Socket socket) {
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Creating output stream failed - " + ex.getMessage());
        }
    }

    public void write(String message) {
        writer.println(message);
    }

}
