package com.adventofcode.day20;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;

import java.util.List;

public class Day20 extends AbstractAdventDay {

    @Override
    public Object part1() {
        return enhance(2);
    }

    @Override
    public Object part2() {
        return enhance(50);
    }

    private int enhance(int times) {
        List<String> input = InputUtils.readInputSplitByBlankLine(inputPath);
        EnhanceAlgorithm algorithm = new EnhanceAlgorithm(input.get(0).toCharArray());
        Image image = new Image(input.get(1).lines().map(String::toCharArray).toArray(char[][]::new), '.');
        for (int i = 0; i < times; i++) {
            image = algorithm.enhance(image);
        }
        return image.pixelCount();
    }
}
