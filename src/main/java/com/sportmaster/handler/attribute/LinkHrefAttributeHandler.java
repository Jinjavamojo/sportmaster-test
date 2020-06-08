package com.sportmaster.handler.attribute;

import org.springframework.stereotype.Component;

@Component
public class LinkHrefAttributeHandler implements AttributeHandler {
    @Override
    public String handle(String input) {
        return String.format("href=\"%s\"", input);
    }

    @Override
    public String entityName() {
        return "link";
    }

    @Override
    public String attributeName() {
        return "href";
    }
}
