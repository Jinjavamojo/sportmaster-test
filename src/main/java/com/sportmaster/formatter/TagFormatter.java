package com.sportmaster.formatter;

import com.sportmaster.dto.DataJsonDto;
import com.sportmaster.dto.PairDto;
import com.sportmaster.dto.RootJsonDto;
import com.sportmaster.dto.meta.AttributeDto;
import com.sportmaster.dto.meta.MetaInfoDto;
import com.sportmaster.handler.AttributeProcessor;
import com.sportmaster.handler.EntityProcessor;
import com.sportmaster.validation.InputValidator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Log4j2
public class TagFormatter {

    @Autowired
    InputValidator validator;

    @Autowired
    EntityProcessor entityProcessor;

    @Autowired
    AttributeProcessor attributeProcessor;


    public String format(RootJsonDto rootJsonDto, Map<String, MetaInfoDto> metaInfo) {

        final List<String> errors = validator.validate(rootJsonDto, metaInfo);
        if (!errors.isEmpty()) {
            errors.forEach(log::error);
            throw new RuntimeException("errors occured while validation");
        }
        //extract all intervals
        final List<Integer> toFormattingIntervals = Stream.concat(rootJsonDto.getData().stream().map(DataJsonDto::getStartPos), rootJsonDto.getData().stream().map(data -> data.getEndPos())).sorted().collect(Collectors.toList());
        final boolean isStartedFromMatching = toFormattingIntervals.contains(0);
        final boolean isEndWithMatching = toFormattingIntervals.get(toFormattingIntervals.size()-1).equals(rootJsonDto.getInputStr().length());
        String inputStr = rootJsonDto.getInputStr();

        //4 cases.
        //1 = string start from content that must be tagged and end with content that must be tagged too
        //2 = string only start from content that must be tagged
        //3 = string only end with content that must be tagged
        //4 = string have not tagged part at start and at the end;

        int index = 0;
        //1
        if (isStartedFromMatching && isEndWithMatching) {
            index = 1;
        }
        //2
        if (isStartedFromMatching && !isEndWithMatching) {
            index = 1;
            toFormattingIntervals.add(inputStr.length());
        }
        //3
        if (!isStartedFromMatching && isEndWithMatching) {
            toFormattingIntervals.add(0,0);
        }
        //4
        if (!isStartedFromMatching && !isEndWithMatching) {
            toFormattingIntervals.add(0,0);
            toFormattingIntervals.add(inputStr.length());
        }

        //extracting indexes of non-formatting part of input string;
        List<Integer> gaps = new ArrayList<>();
        for (int i = index; i < toFormattingIntervals.size() - 1; i += 2) {
            int gapStartPosition = toFormattingIntervals.get(i);
            int gapEndPosition = toFormattingIntervals.get(i + 1);
            gaps.add(gapStartPosition);
            gaps.add(gapEndPosition);
        }

        if (gaps.size() % 2 != 0) {
            throw new RuntimeException("can't extract pairs from gaps data");
        }

        //create non formatted parts of input string
        List<PairDto> notFormattedPartsOfString = new ArrayList<>();
        for (int i = 0; i < gaps.size(); i+=2) {
            final Integer startIndex = gaps.get(i);
            final Integer endIndex = gaps.get(i + 1);
            final String substring = StringUtils.substring(inputStr, startIndex, endIndex);
            notFormattedPartsOfString.add(PairDto.builder().startIndex(startIndex).endIndex(endIndex).data(substring).build());
        }

        //create formatted parts of input string
        final List<PairDto> formattedPartsOfString = rootJsonDto.getData().stream().map(dataJsonDto -> {
            final MetaInfoDto metaInfoDto = metaInfo.get(dataJsonDto.getType());

            final String data = StringUtils.substring(rootJsonDto.getInputStr(), dataJsonDto.getStartPos(), dataJsonDto.getEndPos());
            final String wrappedValue = wrapValue(data, metaInfoDto, metaInfoDto.getAttributes());
            return PairDto.builder()
                    .startIndex(dataJsonDto.getStartPos())
                    .endIndex(dataJsonDto.getEndPos()).data(wrappedValue).build();
        }).collect(Collectors.toList());

        return Stream.concat(formattedPartsOfString.stream(), notFormattedPartsOfString.stream())
                .sorted(Comparator.comparing(PairDto::getStartIndex))
                .map(pairDto -> pairDto.getData())
                .collect(Collectors.joining());
    }


    private String wrapValue(String valueToWrap, MetaInfoDto metaInfoDto, List<AttributeDto> attributes) {

        StringBuilder builder = new StringBuilder();
        builder.append("<").append(metaInfoDto.getTag());
        if (attributes != null) {
            attributes.forEach(attributeDto -> {
                String s = attributeProcessor.processString(valueToWrap, metaInfoDto.getType(), attributeDto.getName());
                builder.append(" ").append(s);
            });
        }
        builder
                .append(">")
                .append(entityProcessor.processString(valueToWrap,metaInfoDto.getType()))
                .append("</")
                .append(metaInfoDto.getTag())
                .append(">");
        return builder.toString();
    }
}
