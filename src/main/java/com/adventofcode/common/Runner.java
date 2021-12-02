package com.adventofcode.common;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class Runner {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Need to provide 1 parameter - day number");
        }
        String dayNumber = args[0];
        String dayClassName = "com.adventofcode.day" + dayNumber + ".Day" + dayNumber;
        String inputPath = "day" + dayNumber + "/input.txt";
        Day day = (Day) Class.forName(dayClassName).getDeclaredConstructor(String.class).newInstance(inputPath);
        log.info("Day {}, part {} - {}", dayNumber, 1, day.part1());
        log.info("Day {}, part {} - {}", dayNumber, 2, day.part2());
    }
}
