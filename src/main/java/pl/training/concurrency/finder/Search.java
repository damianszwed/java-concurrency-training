package pl.training.concurrency.finder;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class Search implements Callable<List<File>> {

    private Path path;
    private String name;
    private List<File> files = new ArrayList<>();

    public Search(Path path, String name) {
        this.path = path;
        this.name = name;
    }

    @Override
    public List<File> call() throws Exception {
        process(path.toFile());
        return files;
    }

    private void process(File file) {
        if (file.isDirectory()) {
            processDirectory(file);
        } else  {
            processFile(file);
        }
    }

    private void processDirectory(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            Arrays.stream(files).forEach(this::process);
        }
    }

    private void processFile(File file) {
        if (file.getName().contains(name)) {
            files.add(file);
        }
    }

}