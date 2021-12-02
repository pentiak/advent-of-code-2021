package com.adventofcode.day2;

public class SimpleSubmarine implements Submarine {
    private int forward = 0;
    private int depth = 0;

    @Override
    public void forward(int amount) {
        forward += amount;
    }

    @Override
    public void up(int amount) {
        depth -= amount;
    }

    @Override
    public void down(int amount) {
        depth += amount;
    }

    @Override
    public int getForward() {
        return forward;
    }

    @Override
    public int getDepth() {
        return depth;
    }
}
