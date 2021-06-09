package com.example.homework.service;

import com.example.homework.exception.InvalidNameException;
import com.example.homework.util.FileType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class NameServiceTest {

    private static NameService nameService;

    private static final String MALE_LOWERCASE = "jan";
    private static final String MALE_UPPERCASE = "Jan";
    private static final String FEMALE_LOWERCASE = "anna";
    private static final String FEMALE_UPPERCASE = "Anna";
    private static final String INCONCLUSIVE = "alex";
    private static final String MALE_SECOND = "jakub";
    private static final String FEMALE_SECOND = "maria";
    private static final String LASTNAME = "nowak";



    private static final List<String> MALE_CONTENT = List.of("zbigniew", "wojciech", "antoni", "bartosz", "grzegorz", MALE_LOWERCASE, INCONCLUSIVE, MALE_SECOND);
    private static final List<String> FEMALE_CONTENT = List.of("gertruda", "maria", "olga", "katarzyna", "agata", FEMALE_LOWERCASE, INCONCLUSIVE, FEMALE_SECOND);

    @BeforeAll
    static void init() throws IOException {
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

        nameService = new NameService(tmpMale.getPath(), tmpFemale.getPath());
    }

    @Test
    void findAllTokens_ReturnMaleTokens_WhenMale() throws IOException {
        assertEquals(MALE_CONTENT, nameService.findAllTokens(FileType.MALE));
    }

    @Test
    void findAllTokens_ReturnFemaleTokens_WhenFemale() throws IOException {
        assertEquals(FEMALE_CONTENT, nameService.findAllTokens(FileType.FEMALE));
    }

    @Test
    void findAllTokens_ThrowNullPointerException_WhenNull() {
        assertThrows(NullPointerException.class, () -> nameService.findAllTokens(null));
    }

    @Test
    void identifyGenderByFirstName_ReturnMale_WhenMaleUppercase() throws IOException {
        assertEquals(Gender.MALE, nameService.identifyGenderByFirstName(MALE_UPPERCASE + " " + LASTNAME));
    }

    @Test
    void identifyGenderByFirstName_ReturnMale_WhenMaleLowercase() throws IOException {
        assertEquals(Gender.MALE, nameService.identifyGenderByFirstName(MALE_LOWERCASE + " " + LASTNAME));
    }

    @Test
    void identifyGenderByFirstName_ReturnMale_WhenFemaleUppercase() throws IOException {
        assertEquals(Gender.FEMALE, nameService.identifyGenderByFirstName(FEMALE_UPPERCASE + " " + LASTNAME));
    }

    @Test
    void identifyGenderByFirstName_ReturnMale_WhenFemaleLowercase() throws IOException {
        assertEquals(Gender.FEMALE, nameService.identifyGenderByFirstName(FEMALE_LOWERCASE + " " + LASTNAME));
    }

    @Test
    void identifyGenderByFirstName_ReturnInconclusive_WhenInconclusiveName() throws IOException {
        assertEquals(Gender.INCONCLUSIVE, nameService.identifyGenderByFirstName(INCONCLUSIVE + " " + LASTNAME));
    }

    @Test
    void identifyGenderByFirstName_ReturnInconclusive_WhenInconclusiveMaleName() throws IOException {
        assertEquals(Gender.INCONCLUSIVE, nameService.identifyGenderByFirstName(INCONCLUSIVE + " " + MALE_SECOND));
    }

    @Test
    void identifyGenderByFirstName_ReturnInconclusive_WhenInconclusiveFemaleName() throws IOException {
        assertEquals(Gender.INCONCLUSIVE, nameService.identifyGenderByFirstName(INCONCLUSIVE + " " + FEMALE_SECOND));
    }

    @Test
    void identifyGenderByFirstName_ThrowInvalidNameException_WhenBlank() {
        assertThrows(InvalidNameException.class, () -> nameService.identifyGenderByFirstName(""));
        assertThrows(InvalidNameException.class, () -> nameService.identifyGenderByFirstName(" "));
        assertThrows(InvalidNameException.class, () -> nameService.identifyGenderByFirstName(null));
    }

    @Test
    void identifyGenderByFullName_ReturnMale_WhenMale() throws IOException {
        assertEquals(Gender.MALE, nameService.identifyGenderByFullName(MALE_UPPERCASE + " " + LASTNAME));
    }

    @Test
    void identifyGenderByFullName_ReturnMale_WhenMaleFemaleFMale() throws IOException {
        assertEquals(Gender.MALE, nameService.identifyGenderByFullName(MALE_UPPERCASE+ " " + FEMALE_SECOND + " " + MALE_SECOND + " " + LASTNAME));
    }

    @Test
    void identifyGenderByFullName_ReturnMale_WhenMaleInconclusive() throws IOException {
        assertEquals(Gender.MALE, nameService.identifyGenderByFullName(MALE_UPPERCASE + " " + INCONCLUSIVE + " " + LASTNAME));
    }

    @Test
    void identifyGenderByFullName_ReturnFemale_WhenFemale() throws IOException {
        assertEquals(Gender.FEMALE, nameService.identifyGenderByFullName(FEMALE_UPPERCASE + " " + LASTNAME));
    }

    @Test
    void identifyGenderByFullName_ReturnFemale_WhenFemaleMaleFemale() throws IOException {
        assertEquals(Gender.FEMALE, nameService.identifyGenderByFullName(FEMALE_UPPERCASE+ " " + MALE_SECOND + " " + FEMALE_SECOND + " " + LASTNAME));
    }

    @Test
    void identifyGenderByFullName_ReturnMale_WhenFemaleInconclusive() throws IOException {
        assertEquals(Gender.FEMALE, nameService.identifyGenderByFullName(FEMALE_UPPERCASE + " " + INCONCLUSIVE + " " + LASTNAME));
    }

    @Test
    void identifyGenderByFullName_ReturnInconclusive_WhenFemaleMale() throws IOException {
        assertEquals(Gender.INCONCLUSIVE, nameService.identifyGenderByFullName(FEMALE_UPPERCASE + " " + MALE_UPPERCASE + " " + LASTNAME));
    }
}