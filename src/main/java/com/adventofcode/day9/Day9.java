package com.adventofcode.day9;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
public class Day9 extends AbstractAdventDay {

    @Override
    public Object part1() {
        int[][] heightMap = InputUtils.inputIntArray(inputPath);
        int riskLevelSum = 0;
        for (int i = 0; i < heightMap.length; i++) {
            for (int j = 0; j < heightMap[i].length; j++) {
                if (testAllAdjacentBigger(heightMap, i, j)) {
                    riskLevelSum += (heightMap[i][j] + 1);
                }
            }
        }
        return riskLevelSum;
    }

    @Override
    public Object part2() {
        int[][] heightMap = InputUtils.inputIntArray(inputPath);
        IntList basinSizes = new IntArrayList();
        for (int i = 0; i < heightMap.length; i++) {
            for (int j = 0; j < heightMap[i].length; j++) {
                if (testAllAdjacentBigger(heightMap, i, j)) {
                    Set<Point> basin = new HashSet<>();
                    Point lowPoint = new Point(i, j);
                    basin.add(lowPoint);
                    collectHigherPoints(heightMap, lowPoint, basin);
                    log.info("Basin '{}': {}", lowPoint, basin);
                    basinSizes.add(basin.size());
                }
            }
        }
        basinSizes.sort(IntComparators.OPPOSITE_COMPARATOR);
        return basinSizes.intStream().limit(3).reduce(1, (i1, i2) -> i1 * i2);
    }

    private void collectHigherPoints(int[][] heightMap, Point startingPoint, Set<Point> higherPoints) {
        getAdjacentPoints(heightMap, startingPoint.i(), startingPoint.j()).stream()
                .filter(p -> {
                    int val = heightMap[p.i()][p.j()];
                    return val > (heightMap[startingPoint.i()][startingPoint.j()]) && val < 9;
                }).forEach(p -> {
                    higherPoints.add(p);
                    collectHigherPoints(heightMap, p, higherPoints);
                });
    }

    private record Point(int i, int j) {

    }

    private List<Point> getAdjacentPoints(int[][] heightMap, int i, int j) {
        List<Point> points = new ArrayList<>(4);
        if (i - 1 >= 0) {
            points.add(new Point(i - 1, j));
        }
        if (j + 1 < heightMap[i].length) {
            points.add(new Point(i, j + 1));
        }
        if (i + 1 < heightMap.length) {
            points.add(new Point(i + 1, j));
        }
        if (j - 1 >= 0) {
            points.add(new Point(i, j - 1));
        }
        return points;
    }

    private boolean testAllAdjacentBigger(int[][] heightMap, int i, int j) {
        return getAdjacentPoints(heightMap, i, j).stream().allMatch(p -> heightMap[p.i()][p.j()] > heightMap[i][j]);
    }
}
