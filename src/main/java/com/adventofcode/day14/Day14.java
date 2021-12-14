package com.adventofcode.day14;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;

import java.util.*;
import java.util.stream.Collectors;

public class Day14 extends AbstractAdventDay {

    @Override
    public Object part1() {
        return createPolymer(10);
    }

    @Override
    public Object part2() {
        return createPolymer(40);
    }

    private long createPolymer(int steps) {
        List<String> input = InputUtils.readInputSplitByBlankLine(inputPath);
        Object2LongMap<Character> charCounts = new Object2LongOpenHashMap<>();
        Object2LongMap<String> pairCounts = new Object2LongOpenHashMap<>();

        initCounts(input, charCounts, pairCounts);
        Map<String, Character> insertions = getInsertions(input);
        runSteps(charCounts, pairCounts, insertions, steps);
        return calculateAnswer(charCounts);
    }

    private Map<String, Character> getInsertions(List<String> input) {
        return input.get(1).lines().map(s -> s.split(" -> ")).collect(Collectors.toMap(a -> a[0], a -> a[1].charAt(0)));
    }

    private void initCounts(List<String> input, Object2LongMap<Character> charCounts, Object2LongMap<String> pairCounts) {
        char[] charArray = input.get(0).toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            charCounts.compute(c, (ch, count) -> count != null ? count + 1 : 1);
            if (i < charArray.length - 1) {
                pairCounts.compute(new String(Arrays.copyOfRange(charArray, i, i + 2)), (p, count) -> count != null ? count + 1 : 1);
            }
        }
    }

    private long calculateAnswer(Object2LongMap<Character> charCounts) {
        LongList counts = new LongArrayList(charCounts.values());
        Collections.sort(counts);
        return counts.getLong(counts.size() - 1) - counts.getLong(0);
    }

    private void runSteps(Object2LongMap<Character> charCounts, Object2LongMap<String> pairCounts, Map<String, Character> insertions, int steps) {
        for (int step = 1; step <= steps; step++) {
            pairCounts = runStep(charCounts, pairCounts, insertions);
        }
    }

    private Object2LongMap<String> runStep(Object2LongMap<Character> charCounts, Object2LongMap<String> pairCounts, Map<String, Character> insertions) {
        Object2LongMap<String> pairCountAdditions = new Object2LongOpenHashMap<>();
        Object2LongMap<Character> charCountAdditions = new Object2LongOpenHashMap<>();
        List<String> pairs = new ArrayList<>(pairCounts.keySet());
        for (String pair : pairs) {
            Character insert = insertions.get(pair);
            if (insert != null) {
                long pairCount = pairCounts.getLong(pair);
                pairCountAdditions.compute(new String(new char[]{pair.charAt(0), insert}), (p, count) -> count != null ? count + pairCount : pairCount);
                pairCountAdditions.compute(new String(new char[]{insert, pair.charAt(1)}), (p, count) -> count != null ? count + pairCount : pairCount);
                charCountAdditions.compute(insert, (ch, count) -> count != null ? count + pairCount : pairCount);
            }
        }
        pairCounts = pairCountAdditions;
        merge(charCounts, charCountAdditions);
        return pairCounts;
    }

    private <T> void merge(Object2LongMap<T> base, Object2LongMap<T> addition) {
        addition.object2LongEntrySet().forEach(e -> base.compute(e.getKey(), (k, v) -> v != null ? v + e.getLongValue() : e.getLongValue()));
    }
}
