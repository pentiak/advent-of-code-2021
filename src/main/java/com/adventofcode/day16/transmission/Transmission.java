package com.adventofcode.day16.transmission;

public interface Transmission {

    boolean testBit(int n);

    int getValue(int beginIndex, int endIndex);
}
