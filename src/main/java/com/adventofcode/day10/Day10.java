package com.adventofcode.day10;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import it.unimi.dsi.fastutil.chars.*;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongComparators;
import it.unimi.dsi.fastutil.longs.LongList;

public class Day10 extends AbstractAdventDay {

    private final Char2CharMap chunkMap = new Char2CharArrayMap(new char[]{'(', '[', '{', '<'}, new char[]{')', ']', '}', '>'});
    private final Char2IntMap autocompleteScoreMap = new Char2IntArrayMap(new char[]{')', ']', '}', '>'}, new int[]{1, 2, 3, 4});
    private final Char2IntMap errorScoreMap = new Char2IntArrayMap(new char[]{')', ']', '}', '>'}, new int[]{3, 57, 1197, 25137});

    @Override
    public Object part1() {
        char[][] navigationSubsystem = InputUtils.inputCharArray(inputPath);
        int sum = 0;
        for (char[] navigationLine : navigationSubsystem) {
            CharStack stack = new CharArrayList(navigationLine.length);
            for (char c : navigationLine) {
                if (chunkMap.containsKey(c)) {
                    stack.push(chunkMap.get(c));
                } else if (c == stack.topChar()) {
                    stack.popChar();
                } else {
                    sum += errorScoreMap.get(c);
                    break;
                }
            }
        }
        return sum;
    }

    @Override
    public Object part2() {
        char[][] navigationSubsystem = InputUtils.inputCharArray(inputPath);
        LongList autoCompleteScores = new LongArrayList(navigationSubsystem.length);
        for (char[] navigationLine : navigationSubsystem) {
            CharStack stack = new CharArrayList(navigationLine.length);
            boolean error = false;
            for (char c : navigationLine) {
                if (chunkMap.containsKey(c)) {
                    stack.push(chunkMap.get(c));
                } else if (c == stack.topChar()) {
                    stack.popChar();
                } else {
                    error = true;
                    break;
                }
            }
            if (error) {
                continue;
            }
            long value = 0;
            while (!stack.isEmpty()) {
                value = value * 5 + autocompleteScoreMap.get(stack.popChar());
            }
            autoCompleteScores.add(value);
        }
        autoCompleteScores.sort(LongComparators.NATURAL_COMPARATOR);
        return autoCompleteScores.getLong(autoCompleteScores.size() / 2);
    }
}
