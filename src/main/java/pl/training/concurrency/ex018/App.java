package pl.training.concurrency.ex018;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) throws InterruptedException {
        Observable.just("Java").subscribe(System.out::println);
        Observable<Integer> numbersStream = Observable.fromArray(2,3,4,5)
                .filter(number -> number % 2 == 0)
                .map(number -> number * 2);
        Observable<Long> sumStream = Observable.fromCallable(new Sum(2, 4));
        Observable.combineLatest(numbersStream, sumStream, (number, sum) -> number * sum)
                .subscribe(System.out::println);
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> observableEmitter) throws Exception {
                Random random = new Random();
                for (int i = 0; i < 50; i++) {
                    observableEmitter.onNext(random.nextInt());
                }
                observableEmitter.onComplete();
            }

        }).subscribe(System.out::println);

        Disposable disposable = Observable.create(App::randomNumberEmitter)
                .subscribe(System.out::println);
        disposable.dispose();

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(numbersStream.subscribe(System.out::println));
        compositeDisposable.add(sumStream.subscribe(System.out::println));
        compositeDisposable.dispose();

        PublishSubject<Integer> subject = PublishSubject.create();
        subject.doOnNext(App::saveToDatabase)
                .filter(App::isImportant)
                .forEach(App::sendEmail);
        subject.onNext(9);
        subject.onNext(0);

        Observable.just(Arrays.asList(1, 2, 34))
                .flatMapIterable(integers -> integers)
                .forEach(System.out::println);

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        sumStream
                .subscribeOn(Schedulers.io())
              //  .observeOn(Schedulers.from(executorService))
                .subscribe(System.out::println);

        Thread.sleep(20_000);

    }

    private static void randomNumberEmitter(ObservableEmitter<Integer> observableEmitter) {
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            observableEmitter.onNext(random.nextInt());
        }
        observableEmitter.onComplete();
    }

    private static boolean isImportant(Integer value) {
        return value == 0;
    }

    private static void saveToDatabase(Integer value) {
        System.out.println("Saving data: " + value);
    }

    private static void sendEmail(Integer value) {
        System.out.println("Sending email: " + value);
    }

}
