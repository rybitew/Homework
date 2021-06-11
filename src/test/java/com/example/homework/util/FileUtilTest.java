package com.example.homework.util;

import com.example.homework.exception.InvalidNameException;
import com.example.homework.exception.InvalidTypeException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FileUtilTest {

    private static FileUtil fileUtil;

    private static final List<String> MALE_CONTENT = List.of("zbigniew", "wojciech", "antoni", "bartosz");
    private static final List<String> FEMALE_CONTENT = List.of("gertruda", "maria", "olga", "katarzyna", "agata");

    @BeforeAll
    static void beforeAll() throws IOException {
        File tmpMale = File.createTempFile("test_male", "");
        tmpMale.deleteOnExit();
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpMale)))) {
            for (String s : MALE_CONTENT) {
                writer.write(s + "\n");
            }
        }

        File tmpFemale = File.createTempFile("test_female", "");
        tmpFemale.deleteOnExit();
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpFemale)))) {
            for (String s : FEMALE_CONTENT) {
                writer.write(s + "\n");
            }
        }
        fileUtil = new FileUtil(tmpFemale.getPath(), tmpMale.getPath());
    }

    @Test
    void countInFile_ReturnOne_WhenOneMale() throws IOException {
        assertEquals(1, fileUtil.countInFile(MALE_CONTENT.subList(0, 1), FileType.MALE));
    }

    @Test
    void countInFile_ReturnZero_WhenNotExistingMale() throws IOException {
        assertEquals(0, fileUtil.countInFile(List.of("not", "existing", "male", "name"), FileType.MALE));
    }

    @Test
    void countInFile_ReturnMaleContentSize_WhenMaleContent() throws IOException {
        assertEquals(MALE_CONTENT.size(), fileUtil.countInFile(MALE_CONTENT, FileType.MALE));
    }

    @Test
    void countInFile_ReturnZero_WhenEmptyList() throws IOException {
        assertEquals(0, fileUtil.countInFile(Lists.emptyList(), FileType.MALE));
    }

    @Test
    void countInFile_ReturnOne_WhenOneFemale() throws IOException {
        assertEquals(1, fileUtil.countInFile(FEMALE_CONTENT.subList(0, 1), FileType.FEMALE));
    }

    @Test
    void countInFile_ReturnZero_WhenNotExistingFemale() throws IOException {
        assertEquals(0, fileUtil.countInFile(List.of("not", "existing", "male", "name"), FileType.FEMALE));
    }

    @Test
    void countInFile_ReturnFemaleContentSize_WhenFemaleContent() throws IOException {
        assertEquals(FEMALE_CONTENT.size(), fileUtil.countInFile(FEMALE_CONTENT, FileType.FEMALE));
    }

    @Test
    void countInFile_ThrowInvalidNameException_WhenListIsNull() {
        assertThrows(InvalidNameException.class, () -> fileUtil.countInFile(null, FileType.FEMALE));
    }

    @Test
    void countInFile_ThrowInvalidTypeException_WhenTypeIsNullOrInvalid() {
        assertThrows(InvalidTypeException.class, () -> fileUtil.countInFile(FEMALE_CONTENT, null));
        assertThrows(InvalidTypeException.class, () -> fileUtil.countInFile(FEMALE_CONTENT, FileType.INVALID));
    }

    @Test
    void readAllTokens_ReturnFemaleContent_WhenTypeFemale() throws IOException {
        assertEquals(FEMALE_CONTENT, fileUtil.readAllTokens(FileType.FEMALE));
    }

    @Test
    void readAllTokens_ReturnMaleContent_WhenTypeMale() throws IOException {
        assertEquals(FEMALE_CONTENT, fileUtil.readAllTokens(FileType.FEMALE));
    }

    @Test
    void readAllTokens_ThrowInvalidTypeException_WhenTypeIsNullOrInvalid() {
        assertThrows(InvalidTypeException.class, () -> fileUtil.readAllTokens(null));
        assertThrows(InvalidTypeException.class, () -> fileUtil.readAllTokens(FileType.INVALID));
    }
}