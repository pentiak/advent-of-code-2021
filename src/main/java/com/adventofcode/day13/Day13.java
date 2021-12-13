package com.adventofcode.day13;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class Day13 extends AbstractAdventDay {

    @Override
    public Object part1() {
        List<String> inputParts = InputUtils.readInputSplitByBlankLine(inputPath);
        List<MutablePoint> points = inputParts.get(0).lines().map(s -> s.split(",")).map(MutablePoint::new).collect(Collectors.toCollection(ArrayList::new));
        List<String[]> folds = inputParts.get(1).lines().limit(1).map(s -> s.split("fold along ")[1]).map(s -> s.split("=")).toList();
        fold(points, folds);
        return points.size();
    }

    @Override
    public Object part2() {
        List<String> inputParts = InputUtils.readInputSplitByBlankLine(inputPath);
        List<MutablePoint> points = inputParts.get(0).lines().map(s -> s.split(",")).map(MutablePoint::new).collect(Collectors.toCollection(ArrayList::new));
        List<String[]> folds = inputParts.get(1).lines().map(s -> s.split("fold along ")[1]).map(s -> s.split("=")).toList();
        fold(points, folds);
        return printAnswer(points);
    }

    private void fold(List<MutablePoint> points, List<String[]> folds) {

        for (String[] fold : folds) {
            int foldValue = Integer.parseInt(fold[1]);
            String foldAxis = fold[0];

            if (foldAxis.equals("y")) {
                for (Iterator<MutablePoint> iterator = points.iterator(); iterator.hasNext(); ) {
                    MutablePoint point = iterator.next();

                    if (point.getY() > foldValue) {
                        int newY = 2 * foldValue - point.getY();
                        if (newY >= 0 && !points.contains(new MutablePoint(point.getX(), newY))) {
                            point.setY(newY);
                        } else {
                            iterator.remove();
                        }
                    }
                }
            } else if (foldAxis.equals("x")) {
                for (Iterator<MutablePoint> iterator = points.iterator(); iterator.hasNext(); ) {
                    MutablePoint point = iterator.next();

                    if (point.getX() > foldValue) {
                        int newX = 2 * foldValue - point.getX();
                        if (newX >= 0 && !points.contains(new MutablePoint(newX, point.getY()))) {
                            point.setX(newX);
                        } else {
                            iterator.remove();
                        }
                    }
                }
            }
        }
    }

    private String printAnswer(List<MutablePoint> points) {
        StringBuilder sb = new StringBuilder();
        int maxX = 0;
        int maxY = 0;
        for (MutablePoint point : points) {
            maxX = Math.max(maxX, point.getX());
            maxY = Math.max(maxY, point.getY());
        }
        for (int y = 0; y <= maxY; y++) {
            StringBuilder lineSb = new StringBuilder();
            for (int x = 0; x <= maxX; x++) {
                if (points.contains(new MutablePoint(x, y))) {
                    lineSb.append('#');
                } else {
                    lineSb.append(' ');
                }
            }
            sb.append(lineSb.toString().stripTrailing());
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    @EqualsAndHashCode
    private static class MutablePoint implements Comparable<MutablePoint> {
        private static final Comparator<MutablePoint> NATURAL = Comparator.comparingInt(MutablePoint::getX).thenComparing(MutablePoint::getY);

        private int x;
        private int y;

        public MutablePoint(String[] arr) {
            this(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
        }

        public MutablePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(MutablePoint o) {
            return NATURAL.compare(this, o);
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }


    }
}
