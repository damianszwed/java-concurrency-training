package pl.training.concurrency.downloader;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

public class ObservableBytesStream implements ObservableOnSubscribe<byte[]> {

    private InputStream inputStream;
    private int bufferSize;
    private boolean stop;

    public ObservableBytesStream(URL url, int bufferSize) throws IOException {
        this(url.openConnection().getInputStream(), bufferSize);
    }

    public ObservableBytesStream(InputStream inputStream, int bufferSize) {
        this.inputStream = inputStream;
        this.bufferSize = bufferSize;
    }

    @Override
    public void subscribe(ObservableEmitter<byte[]> observableEmitter) throws Exception {
        byte[] buffer = new byte[bufferSize];
        int readBytes;
        while ((readBytes = inputStream.read(buffer)) > 0 && !stop) {
            observableEmitter.onNext(Arrays.copyOf(buffer, readBytes));
        }
        observableEmitter.onComplete();
    }

    public void stop() {
        this.stop = true;
    }

}
