package com.example.homework.util.converter;

import com.example.homework.util.FileType;
import org.springframework.core.convert.converter.Converter;

public class StringToFileTypeConverter implements Converter<String, FileType> {

    @Override
    public FileType convert(String source) {
        try {
            return FileType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return FileType.INVALID;
        }
    }
}
