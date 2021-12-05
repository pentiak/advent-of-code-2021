package com.adventofcode.day5;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day5 extends AbstractAdventDay {

    @Override
    public Object part1() {
        return InputUtils.inputLines(inputPath)
                .map(l -> l.split(",| -> "))
                .map(as -> Arrays.stream(as).mapToInt(Integer::parseInt).toArray())
                .map(Line::new)
                .filter(Line::isLinear)
                .map(Line::getPoints)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() >= 2)
                .count();

    }

    @Override
    public Object part2() {
        return InputUtils.inputLines(inputPath)
                .map(l -> l.split(",| -> "))
                .map(as -> Arrays.stream(as).mapToInt(Integer::parseInt).toArray())
                .map(Line::new)
                .map(Line::getPoints)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() >= 2)
                .count();
    }

    private record Point(int x, int y) {

    }

    private record Line(Point startPoint, Point endPoint, boolean isDiagonal) {

        public Line(int[] points) {
            this(new Point(points[0], points[1]), new Point(points[2], points[3]),
                    !(points[0] == points[2] || points[1] == points[3]));
        }

        public boolean isLinear() {
            return !isDiagonal();
        }

        public List<Point> getPoints() {
            if (isDiagonal) {
                return getDiagonalPoints();
            } else {
                return getLinearPoints();
            }
        }

        private List<Point> getLinearPoints() {
            List<Point> points = new LinkedList<>();

            int minX = Math.min(startPoint.x(), endPoint().x());
            int minY = Math.min(startPoint.y(), endPoint().y());
            int maxX = Math.max(startPoint.x(), endPoint().x());
            int maxY = Math.max(startPoint.y(), endPoint().y());

            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    points.add(new Point(x, y));
                }
            }
            return points;
        }

        private List<Point> getDiagonalPoints() {
            List<Point> points = new LinkedList<>();

            int dirX = startPoint().x() <= endPoint().x() ? 1 : -1;
            int dirY = startPoint().y() <= endPoint().y() ? 1 : -1;

            for (int x = startPoint().x(), y = startPoint().y();
                 x != endPoint().x() + dirX && y != endPoint().y() + dirY;
                 x += dirX, y += dirY) {
                points.add(new Point(x, y));
            }

            return points;
        }
    }
}
