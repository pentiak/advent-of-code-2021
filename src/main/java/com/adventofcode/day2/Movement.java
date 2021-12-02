package com.adventofcode.day2;

public enum Movement {
    FORWARD("forward"),
    UP("up"),
    DOWN("down");

    private final String text;

    Movement(String text) {
        this.text = text;
    }
}
