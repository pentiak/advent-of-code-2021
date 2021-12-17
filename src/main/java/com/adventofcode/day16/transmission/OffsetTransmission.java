package com.adventofcode.day16.transmission;

public class OffsetTransmission implements Transmission {

    private final Transmission delegate;
    private final int offset;

    public OffsetTransmission(Transmission delegate, int offset) {
        this.delegate = delegate;
        this.offset = offset;
    }

    @Override
    public boolean testBit(int n) {
        return delegate.testBit(n + offset);
    }

    @Override
    public int getValue(int beginIndex, int endIndex) {
        return delegate.getValue(beginIndex + offset, endIndex + offset);
    }
}
