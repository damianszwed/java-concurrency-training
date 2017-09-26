package pl.training.concurrency.downloader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) throws IOException, InterruptedException {
        URL url = new URL("file:C:\\Program Files\\Java\\jdk-9\\bin\\awt.dll");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Downloader downloader = new Downloader(executorService);
        FileOutputStream outputStream = new FileOutputStream("./downloads/" + UUID.randomUUID().toString() +".txt");
        FileOutputStream outputStream2 = new FileOutputStream("./downloads/" + UUID.randomUUID().toString() +".txt");
        downloader.download(url, outputStream, App::showProgress);
        downloader.download(url, outputStream2, App::showProgress);
        Thread.sleep(2_000);
        executorService.shutdown();
    }

    private static void showProgress(int bytesWrited) {
        System.out.println("Bytes writed: " + bytesWrited);
    }

}
