package com.example.homework.util.converter;

import com.example.homework.controller.NameController.DetectionType;
import org.springframework.core.convert.converter.Converter;

public class StringToDetectionTypeConverter implements Converter<String, DetectionType> {
    @Override
    public DetectionType convert(String source) {
        try {
            return DetectionType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DetectionType.INVALID;
        }
    }
}
