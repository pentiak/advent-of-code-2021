package com.adventofcode.day1;

import com.adventofcode.utils.InputUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import java.util.stream.Collector;

public class Day1 {
    public static void main(String[] args) {
        ResultContainer increases = InputUtils.inputIntStream("day1/input.txt")
                .collect(ResultContainer::new, ResultContainer::compareValue, (r1, r2) -> {
                    throw new IllegalStateException();
                });
        System.out.println(increases.increases);
        SlidingResultContainer slidingIncreases = InputUtils.inputIntStream("day1/input.txt")
                .collect(SlidingResultContainer::new, SlidingResultContainer::compareValue, (r1, r2) -> {
                    throw new IllegalStateException();
                });
        System.out.println(slidingIncreases.increases);

        int[] result = InputUtils.inputIntStream("day1/input.txt")
                .collect(() -> new int[]{Integer.MAX_VALUE, 0},
                        (r, v) -> { if (v > r[0]) r[1]++; r[0] = v; },
                        (r1, r2) -> { throw new IllegalStateException(); });
        System.out.println(result[1]);

        System.out.println(InputUtils.inputLines("day1/input.txt").map(Integer::valueOf).collect(Collector.<Integer, int[], Integer>of(
                () -> new int[]{Integer.MAX_VALUE, 0},(r, v) -> {if (v > r[0]) r[1]++;r[0] = v;},(r1, r2) -> {throw new IllegalStateException();},r -> r[1])));
    }

    private static class ResultContainer {
        private int previousValue = -1;
        private int increases = 0;

        public void compareValue(int value) {
            if (previousValue != -1 && value > previousValue) {
                increases++;
            }
            previousValue = value;
        }
    }

    private static class SlidingResultContainer {
        private final IntList window = new IntArrayList(4);
        private int increases = 0;

        public void compareValue(int value) {
            if (window.size() < 3) {
                window.add(value);
            } else if (window.size() == 3) {
                window.add(value);
                checkIncrease();
            } else {
                window.removeInt(0);
                window.add(value);
                checkIncrease();
            }
        }

        private void checkIncrease() {
            int firstWindow = 0;
            int secondWindow = 0;
            for (int i = 0; i < 3; i++) {
                firstWindow += window.getInt(i);
            }
            for (int i = 1; i < 4; i++) {
                secondWindow += window.getInt(i);
            }
            if (secondWindow > firstWindow) {
                increases++;
            }
        }
    }
}
