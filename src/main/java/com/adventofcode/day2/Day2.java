package com.adventofcode.day2;

import com.adventofcode.common.Day;
import com.adventofcode.utils.InputUtils;

import java.util.List;

public class Day2 extends Day {

    private final List<Day2Instruction> instructionList;

    public Day2(String inputPath) {
        super(inputPath);
        instructionList = InputUtils.parseLines(inputPath, l -> {
            String[] split = l.split(" ");
            return new Day2Instruction(split[0], Integer.parseInt(split[1]));
        });
    }

    @Override
    public Object part1() {
        Submarine sub = new SimpleSubmarine();
        executeInstructions(instructionList, sub);
        return sub.getForward() * sub.getDepth();
    }

    @Override
    public Object part2() {
        Submarine sub2 = new AimingSubmarine();
        executeInstructions(instructionList, sub2);
        return sub2.getForward() * sub2.getDepth();
    }

    private void executeInstructions(List<Day2Instruction> instructionList, Submarine sub) {
        for (Day2Instruction instruction : instructionList) {
            final int amount = instruction.amount();
            switch (instruction.direction()) {
                case "forward" -> sub.forward(amount);
                case "up" -> sub.up(amount);
                case "down" -> sub.down(amount);
                default -> throw new IllegalStateException("Unexpected value: " + instruction.direction());
            }
        }
    }
}
