package com.adventofcode.common;

import com.adventofcode.utils.AdventUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Runner {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Need to provide 1 parameter - day number");
        }
        int dayNumber = Integer.parseInt(args[0]);
        AdventDay day = AdventUtils.newAdventDayInstance(dayNumber);
        day.setInputPath(AdventUtils.getInputPath(dayNumber));
        log.info("Day {}, part {} - {}", dayNumber, 1, day.part1());
        log.info("Day {}, part {} - {}", dayNumber, 2, day.part2());
    }
}
