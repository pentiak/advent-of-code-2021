package com.adventofcode.day1;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import java.util.stream.Collector;

public class Day1 extends AbstractAdventDay {

    @Override
    public Object part1() {
        return InputUtils.inputLines(inputPath).map(Integer::valueOf).collect(Collector.of(
                () -> new int[]{Integer.MAX_VALUE, 0}, (r, v) -> {
                    if (v > r[0]) r[1]++;
                    r[0] = v;
                }, (r1, r2) -> {
                    throw new IllegalStateException();
                }, r -> r[1]));
    }

    @Override
    public Object part2() {
        SlidingResultContainer slidingIncreases = InputUtils.inputIntStream(inputPath)
                .collect(SlidingResultContainer::new, SlidingResultContainer::compareValue, (r1, r2) -> {
                    throw new IllegalStateException();
                });
        return slidingIncreases.increases;
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
            var firstWindow = 0;
            var secondWindow = 0;
            for (var i = 0; i < 3; i++) {
                firstWindow += window.getInt(i);
            }
            for (var i = 1; i < 4; i++) {
                secondWindow += window.getInt(i);
            }
            if (secondWindow > firstWindow) {
                increases++;
            }
        }
    }
}
