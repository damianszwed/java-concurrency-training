package pl.training.concurrency.downloader;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) throws IOException, InterruptedException {
        URL url = new URL("https://github.com/landrzejewski/java-concurrency-training/blob/master/src/main/java/pl/training/concurrency/ex008/Power.java");
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Downloader downloader = new Downloader(executorService);
        downloader.download(url);
        Thread.sleep(2_000);
        executorService.shutdown();

    }

}
