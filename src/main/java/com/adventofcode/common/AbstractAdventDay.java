package com.adventofcode.common;

public abstract class AbstractAdventDay implements AdventDay {

    protected String inputPath;

    @Override
    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    @Override
    public Object part1() {
        return null;
    }

    @Override
    public Object part2() {
        return null;
    }
}
