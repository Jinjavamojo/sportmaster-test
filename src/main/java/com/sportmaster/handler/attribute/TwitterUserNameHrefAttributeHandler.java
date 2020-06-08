package com.sportmaster.handler.attribute;

import org.springframework.stereotype.Component;

@Component
public class TwitterUserNameHrefAttributeHandler implements AttributeHandler {

    @Override
    public String handle(String input) {
        return String.format("href=\"http://twitter.com/%s\"", input);
    }

    @Override
    public String entityName() {
        return "twitter_username";
    }

    @Override
    public String attributeName() {
        return "href";
    }
}
