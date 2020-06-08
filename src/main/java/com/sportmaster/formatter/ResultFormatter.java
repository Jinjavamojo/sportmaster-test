package com.sportmaster.formatter;

import com.sportmaster.reader.InputReader;
import com.sportmaster.reader.MetaInfoReader;
import com.sportmaster.dto.RootJsonDto;
import com.sportmaster.dto.meta.MetaInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Component
public class ResultFormatter {

    @Autowired
    private TagFormatter tagFormatter;

    @Autowired
    private InputReader inputReader;

    @Autowired
    private MetaInfoReader metaInfoReader;

    public String formatStr(String fileDataPath, String fileMetaPath) throws IOException {
        RootJsonDto staff = inputReader.read(Files.readString(Path.of(fileDataPath)));
        final Map<String, MetaInfoDto> metaInfo = metaInfoReader.read(Files.readString(Path.of(fileMetaPath)));
        return tagFormatter.format(staff,metaInfo);
    }
}
