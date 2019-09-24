package com.gojek.assignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Runner {

    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        System.out.println("Enter first file path");
        File firstFile = new File(in.next());
        System.out.println("Enter second file path");
        File secondFile = new File(in.next());
        FileComparator fileComparator = new FileComparator();
        boolean isEqual = fileComparator.compare(firstFile, secondFile);
        System.out.println("File 1 and File 2 are equal => " + isEqual);
    }

    static class InputReader {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

    }

}
