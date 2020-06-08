package com.sportmaster.validation;

import com.sportmaster.dto.DataJsonDto;
import com.sportmaster.dto.RootJsonDto;
import com.sportmaster.dto.meta.MetaInfoDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class InputValidator  {


    public List<String> validate(RootJsonDto rootJsonDto, Map<String, MetaInfoDto> metaInfos) {

        List<String> errors = new ArrayList<>();
        final List<Integer> toFormattingIntervals = Stream.concat(rootJsonDto.getData().stream()
                .map(DataJsonDto::getStartPos), rootJsonDto.getData().stream().map(DataJsonDto::getEndPos))
                .sorted().collect(Collectors.toList());

        final Set<Integer> uncorrectPositionValues = toFormattingIntervals.stream()
                .filter(r -> r > rootJsonDto.getInputStr().length())
                .collect(Collectors.toSet());

        if (uncorrectPositionValues.size() > 0) {
            errors.add(String.format("input data contains char positions that greater than string length. Numbers=%s",
                    uncorrectPositionValues.stream().map(String::valueOf).collect(Collectors.joining(","))));
        }

        for (DataJsonDto datum : rootJsonDto.getData()) {
            final MetaInfoDto metaInfoDto = metaInfos.get(datum.getType());
            if (metaInfoDto == null) {
                errors.add("unknown meta type=" + datum.getType());
            }
        }
        return errors;
    }
}
