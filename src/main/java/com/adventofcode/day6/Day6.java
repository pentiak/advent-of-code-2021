package com.adventofcode.day6;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;

@Log4j2
public class Day6 extends AbstractAdventDay {

    @Override
    public Object part1() {
        long[] fishes = getInitialState();
        return countAfterDays(fishes, 80);
    }

    @Override
    public Object part2() {
        long[] fishes = getInitialState();
        return countAfterDays(fishes, 256);
    }

    private long countAfterDays(long[] fishes, int days) {
        evolve(fishes, days);
        return Arrays.stream(fishes).sum();
    }

    private void evolve(long[] fishes, int days) {
        log.trace("Initial state: {}", fishes);

        int reproductiveIndex = 0;
        for (int day = 0; day < days; day++) {
            long reproductiveFishes = fishes[reproductiveIndex];
            reproductiveIndex = (reproductiveIndex + 1) % 9;
            int newbornIndex = (reproductiveIndex + 8) % 9;
            fishes[newbornIndex] = reproductiveFishes;
            int newCycleIndex = (reproductiveIndex + 6) % 9;
            fishes[newCycleIndex] += reproductiveFishes;
            log.trace("After {} day: {}", day + 1, fishes);
        }
    }

    private long[] getInitialState() {
        long[] fishes = new long[9];
        InputUtils.inputLines(inputPath)
                .map(l -> l.split(","))
                .flatMap(Arrays::stream)
                .mapToInt(Integer::parseInt)
                .forEach(i -> fishes[i]++);
        return fishes;
    }
}
