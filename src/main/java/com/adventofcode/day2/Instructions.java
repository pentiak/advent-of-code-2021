package com.adventofcode.day2;

import java.util.List;
import java.util.function.Function;

public class Instructions<T> {

    private final Function<String, T> parser;

    public Instructions(Function<String, T> parser) {
        this.parser = parser;
    }

    public List<T> parse(List<String> input) {
        return input.stream().map(parser).toList();
    }
}
