package com.example.homework.util;

import com.example.homework.exception.InvalidNameException;
import com.example.homework.exception.InvalidTypeException;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class FileUtil {
    private final File FEMALE_FILE;
    private final File MALE_FILE;

    public FileUtil() throws FileNotFoundException, URISyntaxException {
        MALE_FILE = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "male");
        FEMALE_FILE = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "female");

    }

    public FileUtil(String femalePath, String malePath) {
        FEMALE_FILE = new File(femalePath);
        MALE_FILE = new File(malePath);
    }

    private File getFileName(FileType type) {
        switch (type) {
            case MALE:
                return MALE_FILE;
            case FEMALE:
                return FEMALE_FILE;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public long countInFile(Collection<String> toFind, FileType type) throws IOException {
        if (toFind == null) throw new InvalidNameException();
        if (type == null || type.equals(FileType.INVALID)) throw new InvalidTypeException();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFileName(type)))) {
            String line;
            long count = 0;
            while ((line = reader.readLine()) != null) {
                if (toFind.contains(line.toLowerCase())) count++;
            }
            return count;
        }
    }

    public List<String> readAllTokens(FileType type) throws IOException {
        if (type == null || type.equals(FileType.INVALID)) throw new InvalidTypeException();

        try (BufferedReader reader = new BufferedReader(new FileReader(getFileName(type)))) {
            String line;
            List<String> tokens = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                tokens.add(line);
            }
            return tokens;
        }
    }
}
