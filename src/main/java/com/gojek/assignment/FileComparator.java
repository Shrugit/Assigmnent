package com.gojek.assignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileComparator implements IComparator<File, File> {

    private static final int MAX_THREAD = 10;

    private static final String EMPTY = "";

    private final ExecutorService pool = Executors.newFixedThreadPool(MAX_THREAD);

    @Override
    public boolean compare(File file1, File file2) {
        try {
            final BufferedReader bufferedReader1 = new BufferedReader(new FileReader(file1));
            final BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file2));
            List<Future<CompareTask>> taskList = new ArrayList<>();
            String firstFileLine;
            String secondFileLine;
            // Comparing and getting line by line from both files
            while ((firstFileLine = bufferedReader1.readLine()) != null
                    && (secondFileLine = bufferedReader2.readLine()) != null) {
                taskList.add(pool.submit(new CompareTask(firstFileLine, secondFileLine)));
            }
            // Might be file one has more lines as compare to file two
            while ((firstFileLine = bufferedReader1.readLine()) != null) {
                taskList.add(pool.submit(new CompareTask(firstFileLine, EMPTY)));
            }
            // Might be file two has more lines as compare to file one
            while ((secondFileLine = bufferedReader2.readLine()) != null) {
                taskList.add(pool.submit(new CompareTask(EMPTY, secondFileLine)));
            }
            boolean isFileDataEqual = true;
            for (final Future<CompareTask> task : taskList) {
                try {
                    CompareTask compareTask = task.get();
                    System.out.println(compareTask.getOutput());
                    isFileDataEqual = isFileDataEqual && compareTask.isEqual();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
            pool.shutdown();
            return isFileDataEqual;
        } catch (IOException e) {
            return false;
        }
    }

}
