package com.example.homework.util;

import com.example.homework.exception.InvalidNameException;
import com.example.homework.exception.InvalidTypeException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class FileUtil {
    private final String FEMALE_FILE;
    private final String MALE_FILE;

    public FileUtil(String femalePath, String malePath) {
        FEMALE_FILE = femalePath;
        MALE_FILE = malePath;
    }

    private String getFileName(FileType type) {
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
        try (Stream<String> lines = Files.lines(Paths.get(getFileName(type)))) {
            return lines.distinct().filter(token -> toFind.contains(token.toLowerCase())).count();
        }
    }

    public List<String> readAllTokens(FileType type) throws IOException {
        if (type == null || type.equals(FileType.INVALID)) throw new InvalidTypeException();
        return Files.readAllLines(Paths.get(getFileName(type)));
    }
}
