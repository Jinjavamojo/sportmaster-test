package com.sportmaster.handler.attribute;

import org.springframework.stereotype.Component;

@Component
public class TwitterUserNameClassAttributeHandler implements AttributeHandler {

    @Override
    public String handle(String input) {
        return "class=\"twitter_class_name\"";
    }

    @Override
    public String entityName() {
        return "twitter_username";
    }

    @Override
    public String attributeName() {
        return "class";
    }
}
