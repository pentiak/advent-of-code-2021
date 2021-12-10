package com.adventofcode.day10;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharStack;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongComparators;
import it.unimi.dsi.fastutil.longs.LongList;

public class Day10 extends AbstractAdventDay {

    @Override
    public Object part1() {
        char[][] navigationSubsystem = InputUtils.inputCharArray(inputPath);
        int sum = 0;
        for (char[] navigationLine : navigationSubsystem) {
            sum += getSyntaxErrorScore(navigationLine);
        }
        return sum;
    }

    @Override
    public Object part2() {
        char[][] navigationSubsystem = InputUtils.inputCharArray(inputPath);
        LongList autoCompleteScores = new LongArrayList(navigationSubsystem.length);
        for (char[] navigationLine : navigationSubsystem) {
            if (getSyntaxErrorScore(navigationLine) != 0) {
                continue;
            }
            autoCompleteScores.add(getAutoCompleteScore(navigationLine));
        }
        return pickWinner(autoCompleteScores);
    }

    private long pickWinner(LongList autoCompleteScores) {
        autoCompleteScores.sort(LongComparators.NATURAL_COMPARATOR);
        return autoCompleteScores.getLong(autoCompleteScores.size() / 2);
    }

    private long getAutoCompleteScore(char[] line) {
        long score = 0;
        CharStack stack = new CharArrayList(line.length);
        for (char c : line) {
            if (isChunkBeginning(c)) {
                stack.push(c);
            } else {
                stack.popChar();
            }
        }
        while (!stack.isEmpty()) {
            score = score * 5 + getAutoCompleteValue(stack.popChar());
        }
        return score;
    }

    private int getAutoCompleteValue(char c) {
        return switch (c) {
            case '(' -> 1;
            case '[' -> 2;
            case '{' -> 3;
            case '<' -> 4;
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }

    private int getSyntaxErrorScore(char[] line) {
        CharStack stack = new CharArrayList(line.length);
        for (char c : line) {
            if (!isLegal(c, stack)) {
                return getErrorValue(c);
            }
        }
        return 0;
    }

    private int getErrorValue(char c) {
        return switch (c) {
            case ')' -> 3;
            case ']' -> 57;
            case '}' -> 1197;
            case '>' -> 25137;
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }

    private boolean isLegal(char c, CharStack stack) {
        if (isChunkBeginning(c)) {
            stack.push(c);
            return true;
        } else if (isExpectedChunkEnd(c, stack)) {
            stack.popChar();
            return true;
        }

        return false;
    }

    private boolean isExpectedChunkEnd(char c, CharStack stack) {
        return c == switch (stack.topChar()) {
            case '(' -> ')';
            case '[' -> ']';
            case '{' -> '}';
            case '<' -> '>';
            default -> throw new IllegalStateException("Unexpected value: " + stack.topChar());
        };
    }

    private boolean isChunkBeginning(char c) {
        return c == '(' || c == '[' || c == '{' || c == '<';
    }
}
