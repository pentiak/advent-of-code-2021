package com.adventofcode.day17;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;

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

}
