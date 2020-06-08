package com.sportmaster.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportmaster.dto.RootJsonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class InputReader {

    @Autowired
    private ObjectMapper mapper;

    public RootJsonDto read(String data) throws IOException {
            return mapper.readValue(data, RootJsonDto.class);
    }
}
