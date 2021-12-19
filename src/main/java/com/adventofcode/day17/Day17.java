package com.adventofcode.day17;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import com.google.common.collect.Range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day17 extends AbstractAdventDay {

    private static final Pattern COORDINATE_RANGE = Pattern.compile("\\w=(-?\\d+)\\.\\.(-?\\d+)");

    @Override
    public Object part1() {
        Matcher matcher = COORDINATE_RANGE.matcher(InputUtils.readInputLines(inputPath).get(0));

        if (matcher.find(22)) {
            int lowestY = Integer.parseInt(matcher.group(1));
            return (lowestY + 1) * lowestY / 2;
        }
        return null;
    }

    @Override
    public Object part2() {
        Matcher matcher = COORDINATE_RANGE.matcher(InputUtils.readInputLines(inputPath).get(0));
        int regionXStart = 0;
        int regionXEnd = 0;
        int regionYStart = 0;
        int regionYEnd = 0;
        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;
        if (matcher.find()) {
            regionXStart = Integer.parseInt(matcher.group(1));
            minX = calculateMinX(regionXStart);
            regionXEnd = Integer.parseInt(matcher.group(2));
            maxX = regionXEnd;
        }
        if (matcher.find()) {
            regionYEnd = Integer.parseInt(matcher.group(1));
            minY = regionYEnd;
            regionYStart = Integer.parseInt(matcher.group(2));
            maxY = calculateMaxY(regionYEnd);
        }
        Map<Integer, Range<Integer>> velocitiesX = findXVelocities(minX, maxX, regionXStart, regionXEnd);
        Map<Integer, Range<Integer>> velocitiesY = findYVelocities(minY, maxY, regionYStart, regionYEnd);

        List<Map.Entry<Integer, Range<Integer>>> xEntries = velocitiesX.entrySet().stream().toList();
        List<Map.Entry<Integer, Range<Integer>>> yEntries = velocitiesY.entrySet().stream().toList();
        int values = 0;
        for (Map.Entry<Integer, Range<Integer>> xEntry : xEntries) {
            for (Map.Entry<Integer, Range<Integer>> yEntry : yEntries) {
                if (xEntry.getValue().isConnected(yEntry.getValue())) {
                    values++;
                }
            }
        }

        return values;
    }

    private Map<Integer, Range<Integer>> findYVelocities(int minY, int maxY, int regionStart, int regionEnd) {
        Map<Integer, Range<Integer>> velocities = new HashMap<>();
        for (int v = minY; v <= maxY; v++) {
            stepYCount(v, velocities, regionStart, regionEnd);
        }
        return velocities;
    }

    private void stepYCount(int velocity, Map<Integer, Range<Integer>> velocities, int regionStart, int regionEnd) {
        int pos = 0;
        int stepCount = 0;
        List<Integer> steps = new ArrayList<>();
        int v = velocity;
        while (pos + v >= regionEnd) {
            pos += v;
            v--;
            stepCount++;
            if (pos <= regionStart) {
                steps.add(stepCount);
            }
        }
        if (!steps.isEmpty()) {
            velocities.put(velocity, Range.closed(steps.get(0), steps.get(steps.size() - 1)));
        }
    }

    private Map<Integer, Range<Integer>> findXVelocities(int minX, int maxX, int regionStart, int regionEnd) {
        Map<Integer, Range<Integer>> velocities = new HashMap<>();
        for (int v = minX; v <= maxX; v++) {
            stepXCount(v, velocities, regionStart, regionEnd);
        }
        return velocities;
    }

    private void stepXCount(int velocity, Map<Integer, Range<Integer>> velocities, int regionStart, int regionEnd) {
        int pos = 0;
        int stepCount = 0;
        List<Integer> steps = new ArrayList<>();
        int v = velocity;
        boolean openEnded = false;
        while (pos + v <= regionEnd) {
            pos += v;
            v--;
            stepCount++;
            if (v == 0) {
                openEnded = true;
                break;
            }
            if (pos >= regionStart) {
                steps.add(stepCount);
            }
        }
        if (!steps.isEmpty()) {
            if (openEnded) {
                velocities.put(velocity, Range.atLeast(steps.get(0)));
            } else {
                velocities.put(velocity, Range.closed(steps.get(0), steps.get(steps.size() - 1)));
            }
        }
    }

    private int calculateMaxY(int regionEnd) {
        return Math.abs(regionEnd) - 1;
    }

    private int calculateMinX(int regionStart) {
        return (int) Math.ceil((Math.sqrt((double) regionStart * 8) - 1) / 2);
    }
}
