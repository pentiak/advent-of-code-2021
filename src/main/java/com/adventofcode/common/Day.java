package com.adventofcode.common;

public abstract class Day {

    protected final String inputPath;

    protected Day(String inputPath) {
        this.inputPath = inputPath;
    }

    public abstract Object part1();

    public abstract Object part2();
}
