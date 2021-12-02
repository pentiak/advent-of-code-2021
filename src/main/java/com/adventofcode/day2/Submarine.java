package com.adventofcode.day2;

public interface Submarine {

    void up(int amount);

    void down(int amount);

    void forward(int amount);

    int getForward();

    int getDepth();
}
