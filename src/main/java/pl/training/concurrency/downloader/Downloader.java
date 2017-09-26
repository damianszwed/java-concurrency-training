package pl.training.concurrency.downloader;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

public class Downloader {

    private static final int BUFFER_SIZE = 4096;

    private ExecutorService executorService;

    public Downloader(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void download(URL url) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(UUID.randomUUID().toString() +".txt");
        ObservableBytesStream observableBytesStream = new ObservableBytesStream(url, BUFFER_SIZE);
        Observable.create(observableBytesStream)
                .observeOn(Schedulers.from(executorService))
                .subscribeOn(Schedulers.from(executorService))
                .subscribe(fileOutputStream::write);
    }

}
