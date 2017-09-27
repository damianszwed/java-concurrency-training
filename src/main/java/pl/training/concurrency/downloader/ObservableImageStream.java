package pl.training.concurrency.downloader;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.InputStream;

public class ObservableImageStream implements ObservableOnSubscribe<Image> {

    private InputStream inputStream;

    public ObservableImageStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void subscribe(ObservableEmitter<Image> observableEmitter) throws Exception {
        observableEmitter.onNext(ImageIO.read(inputStream));
    }

}
