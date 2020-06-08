package com.sportmaster.handler.entity;

import org.springframework.stereotype.Component;

@Component
public class LinkHandler implements EntityHandler {

    @Override
    public String name() {
        return "link";
    }

    @Override
    public String handle(String input) {
        return input;
    }
}
