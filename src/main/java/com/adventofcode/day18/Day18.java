package com.adventofcode.day18;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import lombok.extern.log4j.Log4j2;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@Log4j2
public class Day18 extends AbstractAdventDay {

    @Override
    public Object part1() {
        List<SnailfishNumber> numbers = new LinkedList<>(InputUtils.parseLines(inputPath, this::parseSnailfishNumber));
        SnailfishNumber sum = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            sum = add(sum, numbers.get(i));
        }
        return sum.getMagnitude();
    }

    @Override
    public Object part2() {
        //TODO: Make numbers immutable, store tree structure elsewhere to get rid of this ugly input re-read
        long largestMagnitude = Integer.MIN_VALUE;
        List<SnailfishNumber> numbers = new LinkedList<>(InputUtils.parseLines(inputPath, this::parseSnailfishNumber));

        log.info(add(numbers.get(8), numbers.get(0)));
        log.info(add(numbers.get(8), numbers.get(0)).getMagnitude());

        for (int i = 0; i < numbers.size(); i++) {
            for (int j = 0; j < numbers.size(); j++) {
                if (i != j) {
                    long magnitude = add(numbers.get(i), numbers.get(j)).getMagnitude();
                    if (magnitude > largestMagnitude) {
                        log.info("{} <--> {} <--> {}", numbers.get(i), numbers.get(j), magnitude);
                        largestMagnitude = magnitude;
                    }
                    numbers = new LinkedList<>(InputUtils.parseLines(inputPath, this::parseSnailfishNumber));
                    magnitude = add(numbers.get(j), numbers.get(i)).getMagnitude();
                    if (magnitude > largestMagnitude) {
                        log.info("{} <--> {} <--> {}", numbers.get(j), numbers.get(i), magnitude);
                        largestMagnitude = magnitude;
                    }
                    numbers = new LinkedList<>(InputUtils.parseLines(inputPath, this::parseSnailfishNumber));
                }
            }
        }
        return largestMagnitude;
    }

    private SnailfishNumber parseSnailfishNumber(String line) {
        SnailfishNumber outerNumber = null;
        Deque<SnailfishNumber> stack = new LinkedList<>();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '[') {
                SnailfishNumberNesting newSnailfishNumber = new SnailfishNumberNesting();
                if (!stack.isEmpty()) {
                    stack.peek().addSnailfishNumber(newSnailfishNumber);
                    newSnailfishNumber.setParent((SnailfishNumberNesting) stack.peek());
                }
                stack.push(newSnailfishNumber);
            } else if (Character.isDigit(c)) {
                if (!stack.isEmpty()) {
                    SnailfishNumberRegular regular = new SnailfishNumberRegular(c - '0');
                    stack.peek().addSnailfishNumber(regular);
                    regular.setParent((SnailfishNumberNesting) stack.peek());
                }
            } else if (c == ']') {
                outerNumber = stack.pop();
            }
        }
        return outerNumber;
    }

    private boolean reduceExplode(SnailfishNumber number, int level) {
        if (number instanceof SnailfishNumberNesting parent) {
            if (level >= 5 && parent.getLeft().isRegular() && parent.getRight().isRegular()) {
                explode(parent);
                return true;
            } else {
                return reduceExplode(parent.getLeft(), level + 1) || reduceExplode(parent.getRight(), level + 1);
            }
        }
        return false;
    }

    private void split(SnailfishNumberRegular regular) {
        SnailfishNumberNesting parent = regular.getParent();
        regular.setParent(null);

        SnailfishNumberNesting split = new SnailfishNumberNesting();
        SnailfishNumberRegular floorRegular = new SnailfishNumberRegular((int) (Math.floor((double) regular.getNumber() / 2)));
        floorRegular.setParent(split);
        split.addSnailfishNumber(floorRegular);
        SnailfishNumberRegular ceilRegular = new SnailfishNumberRegular((int) (Math.ceil((double) regular.getNumber() / 2)));
        ceilRegular.setParent(split);
        split.addSnailfishNumber(ceilRegular);
        split.setParent(parent);

        if (parent.getLeft() == regular) {
            parent.setLeft(split);
        } else if (parent.getRight() == regular) {
            parent.setRight(split);
        } else {
            throw new IllegalStateException();
        }
    }

    private void explode(SnailfishNumberNesting number) {
        SnailfishNumberRegular toIncrease;
        SnailfishNumber parentLeft = findLeftParent(number);
        if (parentLeft != null) {
            if (parentLeft.isRegular()) {
                toIncrease = (SnailfishNumberRegular) parentLeft;
            } else {
                toIncrease = findRightChild((SnailfishNumberNesting) parentLeft);
            }
            toIncrease.increaseNumber(((SnailfishNumberRegular) number.getLeft()).getNumber());
        }
        //right
        SnailfishNumber parentRight = findRightParent(number);
        if (parentRight != null) {
            if (parentRight.isRegular()) {
                toIncrease = (SnailfishNumberRegular) parentRight;
            } else {
                toIncrease = findLeftChild((SnailfishNumberNesting) parentRight);
            }
            toIncrease.increaseNumber(((SnailfishNumberRegular) number.getRight()).getNumber());
        }


        SnailfishNumberNesting parent = number.getParent();
        SnailfishNumberRegular regular = new SnailfishNumberRegular(0);
        regular.setParent(parent);

        if (parent.getLeft() == number) {
            parent.setLeft(regular);
        } else if (parent.getRight() == number) {
            parent.setRight(regular);
        } else {
            throw new IllegalStateException();
        }
    }

    private SnailfishNumberRegular findLeftChild(SnailfishNumberNesting number) {
        if (number.getLeft().isRegular()) {
            return (SnailfishNumberRegular) number.getLeft();
        } else {
            return findLeftChild((SnailfishNumberNesting) number.getLeft());
        }
    }

    private SnailfishNumber findRightParent(SnailfishNumberNesting number) {
        SnailfishNumberNesting parent = number.getParent();

        if (parent == null) {
            return null;
        }

        SnailfishNumber parentsRight = parent.getRight();
        if (parentsRight != number) {
            return parentsRight;
        } else {
            return findRightParent(parent);
        }
    }

    private SnailfishNumberRegular findRightChild(SnailfishNumberNesting number) {
        if (number.getRight().isRegular()) {
            return (SnailfishNumberRegular) number.getRight();
        } else {
            return findRightChild((SnailfishNumberNesting) number.getRight());
        }
    }

    private SnailfishNumber findLeftParent(SnailfishNumberNesting number) {
        SnailfishNumberNesting parent = number.getParent();

        if (parent == null) {
            return null;
        }

        SnailfishNumber parentsLeft = parent.getLeft();
        if (parentsLeft != number) {
            return parentsLeft;
        } else {
            return findLeftParent(parent);
        }

    }

    private SnailfishNumber add(SnailfishNumber number1, SnailfishNumber number2) {
        SnailfishNumberNesting newParent = new SnailfishNumberNesting();
        newParent.addSnailfishNumber(number1);
        newParent.addSnailfishNumber(number2);
        number1.setParent(newParent);
        number2.setParent(newParent);
        while (reduceExplode(newParent, 1) || reduceSplit(newParent)) ;
        return newParent;
    }

    private boolean reduceSplit(SnailfishNumber number) {
        if (number instanceof SnailfishNumberRegular regular) {
            if (regular.getNumber() >= 10) {
                split(regular);
                return true;
            } else {
                return false;
            }
        } else {
            return reduceSplit(((SnailfishNumberNesting) number).getLeft()) || reduceSplit(((SnailfishNumberNesting) number).getRight());
        }
    }
}
