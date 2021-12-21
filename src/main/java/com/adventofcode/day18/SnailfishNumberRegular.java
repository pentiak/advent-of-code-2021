package com.adventofcode.day18;

public class SnailfishNumberRegular implements SnailfishNumber {
    private int number;
    private SnailfishNumberNesting parent;

    public SnailfishNumberRegular(int number) {
        this.number = number;
    }

    @Override
    public void addSnailfishNumber(SnailfishNumber snailfishNumber) {
        throw new UnsupportedOperationException("Regular snailfish number cannot contain next snailfish numbers");
    }

    @Override
    public String toString() {
        return "" + number;
    }

    @Override
    public SnailfishNumberNesting getParent() {
        return parent;
    }

    @Override
    public void setParent(SnailfishNumberNesting parent) {
        this.parent = parent;
    }

    @Override
    public boolean isRegular() {
        return true;
    }

    @Override
    public long getMagnitude() {
        return number;
    }

    public int getNumber() {
        return number;
    }

    public void increaseNumber(int number) {
        this.number += number;
    }
}
