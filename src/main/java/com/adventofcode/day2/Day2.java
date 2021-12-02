package com.adventofcode.day2;

import com.adventofcode.utils.InputUtils;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class Day2 {

    public static void main(String[] args) {
        Instructions<Day2Instruction> instructions = new Instructions<>(l -> {
            String[] split = l.split(" ");
            return new Day2Instruction(split[0], Integer.parseInt(split[1]));
        });
        List<Day2Instruction> instructionList = instructions.parse(InputUtils.readInputLines("day2/input.txt"));
        Submarine sub = new SimpleSubmarine();
        executeInstructions(instructionList, sub);
        log.info(sub.getForward() * sub.getDepth());

        Submarine sub2 = new AimingSubmarine();
        executeInstructions(instructionList, sub2);
        log.info(sub2.getForward() * sub2.getDepth());
    }

    private static void executeInstructions(List<Day2Instruction> instructionList, Submarine sub) {
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
