package com.adventofcode.day7;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.util.Precision;

import java.util.Arrays;

@Log4j2
public class Day7 extends AbstractAdventDay {

    @Override
    public Object part1() {
        double[] input = InputUtils.inputIntStream(inputPath).mapToDouble(i -> i).toArray();
        int median = (int) StatUtils.percentile(input, 50);
        return (int) Arrays.stream(input).map(i -> Math.abs(i - median)).sum();
    }

    @Override
    public Object part2() {
        double[] input = InputUtils.inputIntStream(inputPath).mapToDouble(i -> i).toArray();
        double mean = StatUtils.mean(input);
        log.trace("Mean: {}", mean);
        return costOfCeilOrFloor(input, mean);
    }

    private int costOfCeilOrFloor(double[] input, double mean) {
        int ceilCost = calculateCost(input, Math.ceil(mean));
        int floorCost = calculateCost(input, Math.floor(mean));
        return Math.min(ceilCost, floorCost);
    }

    private int calculateCost(double[] input, double mean) {
        return Arrays.stream(input).mapToInt(i -> crabFuelCost(i, mean)).sum();
    }

    private int crabFuelCost(double crabPosition, double intendedPosition) {
        double distance = Math.abs(crabPosition - intendedPosition);
        return (int) Precision.round((distance * (1 + distance) / 2), 0);
    }
}
