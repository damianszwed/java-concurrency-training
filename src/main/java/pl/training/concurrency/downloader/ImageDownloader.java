package pl.training.concurrency.downloader;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.awt.*;
import java.io.InputStream;
import java.util.function.Consumer;

public class ImageDownloader {

    private ImageResizer imageResizer;
    private SwingExecutor swingExecutor = new SwingExecutor();

    public ImageDownloader(ImageResizer imageResizer) {
        this.imageResizer = imageResizer;
    }

    public Observable<Image> download(InputStream inputStream, double size) {
        return Observable.create(new ObservableImageStream(inputStream))
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .map(image -> imageResizer.resize(image, size))
                .observeOn(Schedulers.from(swingExecutor));
    }

}