package pl.training.concurrency.chat.v4;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection {

    private PrintWriter writer;
    private Logger logger = Logger.getLogger(getClass().getName());

    public Connection(Socket socket) {
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Creating output stream failed - " + ex.getMessage());
        }
    }

    public void send(String message) {
        writer.println(message);
    }

}
