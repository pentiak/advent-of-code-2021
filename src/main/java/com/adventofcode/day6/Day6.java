package com.adventofcode.day6;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import it.unimi.dsi.fastutil.ints.Int2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2LongMap;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;

@Log4j2
public class Day6 extends AbstractAdventDay {

    @Override
    public Object part1() {
        Int2LongMap fishes = getInitialState();
        return countAfterDays(fishes, 80);
    }

    @Override
    public Object part2() {
        Int2LongMap fishes = getInitialState();
        return countAfterDays(fishes, 256);
    }

    private long countAfterDays(Int2LongMap fishes, int days) {
        evolve(fishes, days);
        return fishes.values().longStream().sum();
    }

    private void evolve(Int2LongMap fishesMap, int days) {
        log.trace("Initial state: {}", fishesMap);

        for (int day = 0; day < days; day++) {
            long reproductiveFishes = fishesMap.get(0);
            for (int i = 1; i <= 8; i++) {
                fishesMap.put(i - 1, fishesMap.get(i));
            }
            fishesMap.put(8, reproductiveFishes);//new borns
            fishesMap.put(6, fishesMap.get(6) + reproductiveFishes);//new cycle
            log.trace("After {} day: {}", day + 1, fishesMap);
        }
    }

    private Int2LongMap getInitialState() {
        Int2LongMap fishes = new Int2LongLinkedOpenHashMap();
        InputUtils.inputLines(inputPath)
                .map(l -> l.split(","))
                .flatMap(Arrays::stream)
                .mapToInt(Integer::parseInt)
                .forEach(i -> fishes.mergeLong(i, 1, Long::sum));
        return fishes;
    }
}
