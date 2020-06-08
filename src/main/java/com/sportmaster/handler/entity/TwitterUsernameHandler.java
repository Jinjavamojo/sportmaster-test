package com.sportmaster.handler.entity;

import org.springframework.stereotype.Component;

@Component
public class TwitterUsernameHandler implements EntityHandler {

    @Override
    public String name() {
        return "twitter_username";
    }

    @Override
    public String handle(String input) {
        return input;
    }
}
