package pl.training.concurrency.finder;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Finder {

    public List<File> find(Path path, List<String> files, int numberOfThreads, int timeout) throws InterruptedException {
        List<Search> tasks = files.stream()
                .map(name -> new Search(path, name))
                .collect(Collectors.toList());

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<List<File>>> results = executorService.invokeAll(tasks);
        executorService.shutdown();
        executorService.awaitTermination(timeout, TimeUnit.MILLISECONDS);

        return results.stream()
                .map(this::getResult)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<File> getResult(Future<List<File>> futureResult) {
        try {
            return futureResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
