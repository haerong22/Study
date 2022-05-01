package com.example.enumparam.config;

import com.example.enumparam.domain.Position;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPosition implements Converter<String, Position> {

    @Override
    public Position convert(String source) {
        return Position.valueOf(source.toUpperCase());
    }
}
