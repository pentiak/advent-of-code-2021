package com.adventofcode.day18;

public interface SnailfishNumber {

    void addSnailfishNumber(SnailfishNumber snailfishNumber);

    SnailfishNumberNesting getParent();

    void setParent(SnailfishNumberNesting parent);

    boolean isRegular();

    long getMagnitude();
}
