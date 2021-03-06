package com.example.homework.service;

import com.example.homework.exception.InvalidNameException;
import com.example.homework.exception.InvalidTypeException;
import com.example.homework.util.FileType;
import com.example.homework.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class NameService {

    private final FileUtil fileUtil;

    @Autowired
    public NameService(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    public NameService(String malePath, String femalePath) {
        fileUtil = new FileUtil(femalePath, malePath);
    }

    public List<String> findAllTokens(FileType type) throws IOException {
        if (type == null || type.equals(FileType.INVALID)) throw new InvalidTypeException();
        return fileUtil.readAllTokens(type);
    }

    public Gender identifyGenderByFirstName(String name) throws IOException {
        if (name == null || name.isBlank()) throw new InvalidNameException();

        String firstName = name.toLowerCase().split(" ")[0];

        long maleCount = fileUtil.countInFile(Collections.singleton(firstName), FileType.MALE);
        long femaleCount = fileUtil.countInFile(Collections.singleton(firstName), FileType.FEMALE);


        if (femaleCount == maleCount) return Gender.INCONCLUSIVE;
        else if (maleCount > femaleCount) return Gender.MALE;
        else return Gender.FEMALE;
    }

    public Gender identifyGenderByFullName(String name) throws IOException {
        if (name == null || name.isBlank()) throw new InvalidNameException();

        String[] tokens = name.toLowerCase().split(" ");
        long maleCount = fileUtil.countInFile(Arrays.asList(tokens), FileType.MALE);
        long femaleCount = fileUtil.countInFile(Arrays.asList(tokens), FileType.FEMALE);

        if (maleCount > femaleCount) return Gender.MALE;
        if (maleCount < femaleCount) return Gender.FEMALE;
        else return Gender.INCONCLUSIVE;
    }
}
