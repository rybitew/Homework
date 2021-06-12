package com.example.homework.util;

import com.example.homework.exception.InvalidNameException;
import com.example.homework.exception.InvalidTypeException;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Component
public class FileUtil {
    private static final String BASE_MALE_FILE = "male";
    private static final String BASE_FEMALE_FILE = "female";
    private String femaleFile;
    private String maleFile;

    public FileUtil() {
    }

    public FileUtil(String femalePath, String malePath) {
        femaleFile = femalePath;
        maleFile = malePath;
    }

    private InputStream getFileName(FileType type) throws FileNotFoundException {
        switch (type) {
            case MALE:
                if (maleFile == null) return getClass().getResourceAsStream("/" + BASE_MALE_FILE);
                return new FileInputStream(maleFile);
            case FEMALE:
                if (femaleFile == null) return getClass().getResourceAsStream("/" + BASE_FEMALE_FILE);
                return new FileInputStream(femaleFile);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public long countInFile(Collection<String> toFind, FileType type) throws IOException {
        if (toFind == null) throw new InvalidNameException();
        if (type == null || type.equals(FileType.INVALID)) throw new InvalidTypeException();
        try (InputStream is = getFileName(type); BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
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

        try (InputStream is = getFileName(type); BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            List<String> tokens = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                tokens.add(line);
            }
            return tokens;
        }
    }
}
