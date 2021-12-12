package com.adventofcode.day12;

public record Cave(String name, boolean big) {
    public Cave(String name) {
        this(name, Character.isUpperCase(name.charAt(0)));
    }
}
