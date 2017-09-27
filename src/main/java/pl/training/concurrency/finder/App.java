package pl.training.concurrency.finder;

import java.nio.file.Paths;
import java.util.Arrays;

public class App {

    public static void main(String[] args) throws InterruptedException {
        Finder searchPhase = new Finder();
        searchPhase.find(Paths.get("."), Arrays.asList("iml", "xml", "java"), 2, 5_000).stream()
                .filter(file -> file.length() > 1024)
                .forEach(file -> System.out.println(file.getAbsolutePath()));
    }

}