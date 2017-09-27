package pl.training.concurrency.downloader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;

public class App extends JFrame {

    private JLabel progressLabel = new JLabel();
    private JButton stopButton = new JButton("Stop");
    private JLabel imageLabel = new JLabel();
    private NumberFormat numberFormat = NumberFormat.getNumberInstance();
    private long bytes;

    public App() {
        setBounds(0, 0, 320, 60);
        setLayout(new FlowLayout());
        add(progressLabel);
        add(stopButton);
        add(imageLabel);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void start() throws IOException {
        FileInputStream imageStream = new FileInputStream("image.png");
        ImageDownloader imageDownloader = new ImageDownloader(new ImageResizer());
        imageDownloader.download(imageStream, 0.5)
                .subscribe(image -> imageLabel.setIcon(new ImageIcon(image)));

        URL url = new URL("http://releases.ubuntu.com/16.04.3/ubuntu-16.04.3-desktop-amd64.iso");
        FileOutputStream outputStream = new FileOutputStream("./ubuntu.iso");
        Downloader downloader = new Downloader();
        Stoppable task = downloader.download(url, outputStream, this::showProgress);
        stopButton.addActionListener(e -> task.stop());
    }

    private void showProgress(long savedBytes) {
        bytes += savedBytes;
        progressLabel.setText(numberFormat.format(bytes) + "Bytes");
    }

    public static void main(String[] args) throws IOException {
        new App().start();
    }

}
