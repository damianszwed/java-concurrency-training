package pl.training.concurrency.downloader;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class Downloader {

    private static final int BUFFER_SIZE = 4096;

    private ExecutorService executorService;

    public Downloader(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void download(URL url, OutputStream outputStream,  Consumer<Integer> onProgress) throws IOException {
        Observable.create(new ObservableBytesStream(url, BUFFER_SIZE))
                .observeOn(Schedulers.from(executorService))
                .subscribeOn(Schedulers.from(executorService))
                .subscribe(bytes -> {
                    outputStream.write(bytes);
                    onProgress.accept(bytes.length);
                });
    }

}
