package pl.training.concurrency.ex015;

import java.nio.file.Paths;
import java.util.Arrays;

public class App {

    public static void main(String[] args) throws InterruptedException {
        SearchPhase searchFilesPhase = new SearchPhase();
        searchFilesPhase.start(Paths.get("."), Arrays.asList("iml", "xml", "java"), 3, 5_000).stream()
                .filter(file -> file.length() > 1024)
                .forEach(file -> System.out.println(file.getAbsolutePath()));
    }

}