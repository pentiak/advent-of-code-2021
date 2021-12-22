package com.adventofcode.common

import com.adventofcode.utils.InputUtils
import spock.lang.Execution
import spock.lang.Specification

import java.nio.file.Files

import static com.adventofcode.utils.AdventUtils.*
import static org.spockframework.runtime.model.parallel.ExecutionMode.CONCURRENT

class AdventTest extends Specification {

    @Execution(CONCURRENT)
    def "Should give correct results"() {
        given:
        def adventDay = newAdventDayInstance(day)
        type == 'input' ? adventDay.setInputPath(getInputPath(day)) : adventDay.setInputPath(getExamplePath(day))

        expect:
        adventDay.part1() == part1
        adventDay.part2() == part2

        where:
        day | type      | part1   | part2
        1   | 'example' | 7       | 5
        1   | 'input'   | 1754    | 1789
        2   | 'example' | 150     | 900
        2   | 'input'   | 1488669 | 1176514794
        3   | 'example' | 198     | 230
        3   | 'input'   | 749376  | 2372923
        4   | 'example' | 4512    | 1924
        4   | 'input'   | 49686   | 26878
        5   | 'example' | 5       | 12
        5   | 'input'   | 7318    | 19939
        6   | 'example' | 5934    | 26984457539
        6   | 'input'   | 352872  | 1604361182149
        7   | 'example' | 37      | 168
        7   | 'input'   | 336131  | 92676646
        8   | 'example' | 26      | 61229
        8   | 'input'   | 288     | 940724
        9   | 'example' | 15      | 1134
        9   | 'input'   | 554     | 1017792
        10  | 'example' | 26397   | 288957
        10  | 'input'   | 311895  | 2904180541
        11  | 'example' | 1656    | 195
        11  | 'input'   | 1749    | 285
        12  | 'example' | 226     | 3509
        12  | 'input'   | 5874    | 153592
        13  | 'example' | 17      | Files.readString(InputUtils.resourcePath("day13Part2ExampleAnswer.txt"))
        13  | 'input'   | 607     | Files.readString(InputUtils.resourcePath("day13Part2InputAnswer.txt"))
        14  | 'example' | 1588    | 2188189693529
        14  | 'input'   | 3408    | 3724343376942
        15  | 'example' | 40      | 315
        15  | 'input'   | 523     | 2876
        16  | 'example' | 31      | 54
        16  | 'input'   | 923     | 258888628940
        17  | 'example' | 45      | 112
        17  | 'input'   | 4278    | 1994
        18  | 'example' | 4140    | 3993
        18  | 'input'   | 3654    | 4578
        19  | 'example' | 79      | 3621
        19  | 'input'   | 357     | 12317
        20  | 'example' | 35      | 3351
        20  | 'input'   | 5461    | 18226
    }
}
