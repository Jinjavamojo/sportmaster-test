package com.sportmaster.dto.meta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Accessors(chain = true, fluent = false)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaInfoDto {

    String type;

    String tag;

    List<AttributeDto> attributes;

}
