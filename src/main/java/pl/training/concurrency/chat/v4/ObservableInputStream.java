package pl.training.concurrency.chat.v4;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ObservableInputStream implements ObservableOnSubscribe<String> {

    private InputStream inputStream;

    public ObservableInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String message;
        while ((message = reader.readLine()) != null) {
            observableEmitter.onNext(message);
        }
    }

}
