package pl.training.concurrency.downloader;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;

public class App extends JFrame {

    private JLabel progressLabel = new JLabel();
    private JButton stop = new JButton("Stop");
    private long bytes;
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private ObservableBytesStream observableBytesStream;

    public App() {
        setBounds(0,0,320,240);
        add(progressLabel, BorderLayout.NORTH);
        add(stop, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        App app = new App();
        app.download();
    }

    private void download() throws IOException, InterruptedException {
        URL url = new URL("http://releases.ubuntu.com/16.04.3/ubuntu-16.04.3-desktop-amd64.iso");
        FileOutputStream outputStream = new FileOutputStream("./downloads/ubuntu.iso");
        try (Downloader downloader = new Downloader()) {
            observableBytesStream = downloader.download(url, outputStream, this::showProgress);
            stop.addActionListener(e -> observableBytesStream.setClose(true));
        }
    }

    private void showProgress(int bytesWrite) {
        bytes += bytesWrite;
        progressLabel.setText("Bytes: " + numberFormat.format(bytes));
    }

}
