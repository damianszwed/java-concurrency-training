package pl.training.concurrency.downloader;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Downloader implements AutoCloseable {

    private static final int BUFFER_SIZE = 4096;

    private SwingExecutor swingExecutor = new SwingExecutor();
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public Downloader() {
    }

    public Downloader(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ObservableBytesStream download(URL url, OutputStream outputStream, Consumer<Integer> onProgress) throws IOException {
        ObservableBytesStream stream = new ObservableBytesStream(url, BUFFER_SIZE);
        ConnectableObservable<byte[]> bytesStream = Observable.create(stream).replay();
        bytesStream
                .observeOn(Schedulers.from(executorService))
                .subscribeOn(Schedulers.from(executorService))
                .subscribe(outputStream::write, Throwable::printStackTrace, outputStream::close);
        bytesStream
                .observeOn(Schedulers.from(swingExecutor))
                .subscribe(bytes -> onProgress.accept(bytes.length));
        bytesStream.connect();
        return stream;
    }

    @Override
    public void close() throws InterruptedException {
        executorService.awaitTermination(1, TimeUnit.SECONDS);
        executorService.shutdown();
    }

}
