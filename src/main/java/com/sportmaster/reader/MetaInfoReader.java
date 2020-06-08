package com.sportmaster.reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportmaster.dto.meta.MetaInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MetaInfoReader {

    @Autowired
    private ObjectMapper mapper;

    public Map<String, MetaInfoDto> read(String data) throws IOException {
        return mapper.readValue(data, new TypeReference<List<MetaInfoDto>>() {
        }).stream().collect(Collectors.toMap(MetaInfoDto::getType, v -> v));
    }
}
