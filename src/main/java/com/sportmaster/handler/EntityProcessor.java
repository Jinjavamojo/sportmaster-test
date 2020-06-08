package com.sportmaster.handler;

import com.sportmaster.handler.entity.EntityHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j2
public class EntityProcessor {

    @Autowired
    private List<EntityHandler> entityHandlers;

    public String processString(String input, String entityName) {
        final List<EntityHandler> handlers = entityHandlers.stream().filter(entityHandler -> entityHandler.name().equals(entityName)).collect(Collectors.toList());
        if (handlers.size() > 1) {
            throw new RuntimeException(String.format("count of entity type handlers by name=%s is more than 1", entityName));
        }
        if (handlers.isEmpty()) {
            log.info("can't find entity type handler for '{}'", entityName);
            return input;
        }
        return handlers.get(0).handle(input);
    }
}
