package com.adventofcode.day18;

public class SnailfishNumberNesting implements SnailfishNumber {
    private SnailfishNumberNesting parent;
    private SnailfishNumber left;
    private SnailfishNumber right;

    @Override
    public void addSnailfishNumber(SnailfishNumber snailfishNumber) {
        if (left == null) {
            left = snailfishNumber;
        } else if (right == null) {
            right = snailfishNumber;
        } else {
            throw new IllegalStateException();
        }
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
        return false;
    }

    @Override
    public long getMagnitude() {
        return (3 * left.getMagnitude()) + (2 * right.getMagnitude());
    }

    public SnailfishNumber getLeft() {
        return left;
    }

    public void setLeft(SnailfishNumber left) {
        this.left = left;
    }

    public SnailfishNumber getRight() {
        return right;
    }

    public void setRight(SnailfishNumber right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "[" + (left != null ? left.toString() : "") + "," + (right != null ? right.toString() : "") + "]";
    }
}
