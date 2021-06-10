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
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/identify/first-name/{name}")
    public ResponseEntity<Gender> identifyGenderFirstName(@PathVariable String name) {
        try {
            return ResponseEntity.ok(service.identifyGenderByFirstName(name));
        }catch (InvalidNameException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/identify/full-name/{name}")
    public ResponseEntity<Gender> identifyGenderFullName(@PathVariable String name) {
        try {
            return ResponseEntity.ok(service.identifyGenderByFullName(name));
        }catch (InvalidNameException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
