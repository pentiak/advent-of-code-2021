package com.adventofcode.day1;

import com.adventofcode.utils.InputUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.extern.log4j.Log4j2;

import java.util.stream.Collector;

@Log4j2
public class Day1 {
    public static void main(String[] args) {
        log.info(InputUtils.inputLines("day1/input.txt").map(Integer::valueOf).collect(Collector.<Integer, int[], Integer>of(
                () -> new int[]{Integer.MAX_VALUE, 0}, (r, v) -> {
                    if (v > r[0]) r[1]++;
                    r[0] = v;
                }, (r1, r2) -> {
                    throw new IllegalStateException();
                }, r -> r[1])));

        SlidingResultContainer slidingIncreases = InputUtils.inputIntStream("day1/input.txt")
                .collect(SlidingResultContainer::new, SlidingResultContainer::compareValue, (r1, r2) -> {
                    throw new IllegalStateException();
                });
        log.info(slidingIncreases.increases);
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
