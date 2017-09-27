package pl.training.concurrency.ex014;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.Phaser;
import java.util.function.Predicate;

public class App {

    public static void main(String[] args) {
        Phaser phaser = new Phaser(3);
        Path path = Paths.get(".");
        Predicate<File> filePredicate = file -> file.length() > 1024;
        Arrays.asList(
                new Search(path, "java", filePredicate, phaser),
                new Search(path, "txt", filePredicate, phaser),
                new Search(path, "xml", filePredicate, phaser)
        ).forEach(searchFiles -> new Thread(searchFiles).start());
    }

}
