package pl.training.concurrency.downloader;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
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
                .map(bytes -> (long) bytes.length)
                .observeOn(Schedulers.from(swingExecutor))
                .subscribe(onData::accept, Throwable::printStackTrace, outputStream::close);
        return observableBytesStream::stop;
    }

/*
    public Stoppable download(URL url, OutputStream outputStream, Consumer<Long> onData) throws IOException {
        ObservableBytesStream observableBytesStream = new ObservableBytesStream(url, BUFFER_SIZE);
        Observable.create(observableBytesStream)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .doOnNext(new io.reactivex.functions.Consumer<byte[]>() {

                    @Override
                    public void accept(byte[] bytes) throws Exception {
                        outputStream.write(bytes);
                    }

                })
                .observeOn(Schedulers.from(swingExecutor))
                .subscribe(new io.reactivex.functions.Consumer<byte[]>() {

                               @Override
                               public void accept(byte[] bytes) throws Exception {
                                   onData.accept((long) bytes.length);
                               }

                           },
                        new io.reactivex.functions.Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                outputStream.close();
                            }
                        });
        return new Stoppable() {

            @Override
            public void stop() {
                observableBytesStream.stop();
            }

        };
    }*/


}
