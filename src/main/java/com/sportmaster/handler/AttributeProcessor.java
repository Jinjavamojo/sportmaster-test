package com.sportmaster.handler;

import com.sportmaster.handler.attribute.AttributeHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j2
public class AttributeProcessor {

    @Autowired
    private List<AttributeHandler> attributeHandlers;

    public String processString(String input, String entityName, String attributeName) {
        final List<AttributeHandler> handlers = attributeHandlers.stream()
                .filter(attributeHandler -> attributeHandler.entityName().equals(entityName)
                        && attributeHandler.attributeName().equals(attributeName)).collect(Collectors.toList());
        if (handlers.size() > 1) {
            throw new RuntimeException(String.format("count of attribute type handlers by name=%s is more than 1",
                    attributeName));
        }

        if (handlers.isEmpty()) {
            log.warn("can't find attribute type handler for '{} {}'", entityName, attributeName);
            return input;
        }
        return handlers.get(0).handle(input);
    }
}
