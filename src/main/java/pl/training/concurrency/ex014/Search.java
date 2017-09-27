package pl.training.concurrency.ex014;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Search implements Runnable {

    private Path path;
    private String name;
    private Predicate<File> searchedFile;
    private Phaser phaser;
    private List<File> files = new ArrayList<>();

    public Search(Path path, String name, Predicate<File> searchedFile, Phaser phaser) {
        this.path = path;
        this.name = name;
        this.searchedFile = searchedFile;
        this.phaser = phaser;
    }

    @Override
    public void run() {
        phaser.arriveAndAwaitAdvance();
        System.out.printf("%s: Starting phase %d\n", Thread.currentThread().getName(), phaser.getPhase());
        process(path.toFile());
        if (!hasResults()) {
            return;
        }
        files = files.stream().filter(searchedFile).collect(Collectors.toList());
        if (!hasResults()) {
            return;
        }
        files.forEach(file -> System.out.println(file.getAbsolutePath()));
    }

    private boolean hasResults() {
        if (files.isEmpty()) {
            System.out.printf("%s: Finished with no results\n", Thread.currentThread().getName());
            phaser.arriveAndDeregister();
            return false;
        }
        System.out.printf("%s: Finished with %d results\n", Thread.currentThread().getName(), files.size());
        phaser.arriveAndAwaitAdvance();
        return true;
    }

    private void process(File file) {
        if (file.isDirectory()) {
            processDirectory(file);
        } else {
            processFile(file);
        }
    }

    private void processDirectory(File file) {
        File[] files = file.listFiles();
        if (file != null){
            Arrays.stream(files).forEach(this::process);
        }
    }

    private void processFile(File file) {
        if (file.getName().contains(name)) {
            files.add(file);
        }
    }

}
