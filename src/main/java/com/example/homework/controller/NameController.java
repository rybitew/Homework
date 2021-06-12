package com.example.homework.controller;

import com.example.homework.exception.InvalidNameException;
import com.example.homework.exception.InvalidTypeException;
import com.example.homework.service.Gender;
import com.example.homework.service.NameService;
import com.example.homework.util.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class NameController {
    public enum DetectionType {
        FULL, FIRST, INVALID
    }

    private final NameService service;

    @Autowired
    public NameController(NameService service) {
        this.service = service;
    }

    @GetMapping("/tokens/{type}")
    public ResponseEntity<List<String>> getAllTokens(@PathVariable FileType type) {
        try {
            return ResponseEntity.ok(service.findAllTokens(type));
        } catch (InvalidTypeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/identify/{type}/{name}")
    public ResponseEntity<Gender> identifyGenderByName(@PathVariable DetectionType type, @PathVariable String name) {
        try {
            switch (type) {
                case FULL:
                    return ResponseEntity.ok(service.identifyGenderByFullName(name));
                case FIRST:
                    return ResponseEntity.ok(service.identifyGenderByFirstName(name));
                default:
                    return ResponseEntity.badRequest().build();
            }
        } catch (InvalidNameException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
