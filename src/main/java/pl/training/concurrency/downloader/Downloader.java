package pl.training.concurrency.downloader;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.function.Consumer;

public class Downloader {

    private static final int BUFFER_SIZE = 4096;

    private SwingExecutor swingExecutor = new SwingExecutor();

    public Stoppable download(URL url, OutputStream outputStream, Consumer<Long> onData) throws IOException {
        ObservableBytesStream observableBytesStream = new ObservableBytesStream(url, BUFFER_SIZE);
        Observable.create(observableBytesStream)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .doOnNext(outputStream::write)
                .observeOn(Schedulers.from(swingExecutor))
                .map(bytes -> (long) bytes.length)
                .subscribe(onData::accept, Throwable::printStackTrace, outputStream::close);
        return observableBytesStream::stop;
    }

}
