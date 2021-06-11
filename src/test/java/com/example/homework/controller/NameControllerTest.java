package com.example.homework.controller;

import com.example.homework.exception.InvalidNameException;
import com.example.homework.exception.InvalidTypeException;
import com.example.homework.service.Gender;
import com.example.homework.service.NameService;
import com.example.homework.util.FileType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NameController.class)
class NameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NameService service;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final List<String> MALE_CONTENT = List.of("zbigniew", "wojciech", "antoni", "bartosz");
    private static final List<String> FEMALE_CONTENT = List.of("gertruda", "maria", "olga", "katarzyna", "agata");

    @Test
    void getAllTokens_ReturnMaleContent200_WhenTypeMale() throws Exception {
        when(service.findAllTokens(FileType.MALE)).thenReturn(MALE_CONTENT);

        this.mockMvc.perform(get("/tokens/MALE"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(MALE_CONTENT)));
    }

    @Test
    void getAllTokens_ReturnMaleContent200_WhenTypeMaleLowercase() throws Exception {
        when(service.findAllTokens(FileType.MALE)).thenReturn(MALE_CONTENT);

        this.mockMvc.perform(get("/tokens/male"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(MALE_CONTENT)));
    }

    @Test
    void getAllTokens_ReturnFemaleContent200_WhenTypeFemale() throws Exception {
        when(service.findAllTokens(FileType.FEMALE)).thenReturn(FEMALE_CONTENT);

        this.mockMvc.perform(get("/tokens/FEMALE"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(FEMALE_CONTENT)));
    }

    @Test
    void getAllTokens_Return400_WhenTypeInvalid() throws Exception {
        when(service.findAllTokens(FileType.INVALID)).thenThrow(InvalidTypeException.class);

        this.mockMvc.perform(get("/tokens/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void identifyGenderByName_ReturnMale200_WhenMaleAndFirstName() throws Exception {
        when(service.identifyGenderByFirstName("wojciech")).thenReturn(Gender.MALE);

        this.mockMvc.perform(get("/identify/first/wojciech"))
                .andExpect(status().isOk())
                .andExpect(content().string("\"MALE\""));
    }

    @Test
    void identifyGenderByName_Return400_WhenBlankAndFirst() throws Exception {
        when(service.identifyGenderByFirstName(" ")).thenThrow(InvalidNameException.class);
        when(service.identifyGenderByFirstName(null)).thenThrow(InvalidNameException.class);

        this.mockMvc.perform(get("/identify/first/ "))
                .andExpect(status().isBadRequest());
    }

    @Test
    void identifyGenderByName_ReturnMale200_WhenMaleAndFullName() throws Exception {
        when(service.identifyGenderByFullName("wojciech")).thenReturn(Gender.MALE);

        this.mockMvc.perform(get("/identify/full/wojciech"))
                .andExpect(status().isOk())
                .andExpect(content().string("\"MALE\""));
    }
}