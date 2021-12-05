package com.adventofcode.common


import spock.lang.Specification

import static com.adventofcode.utils.AdventUtils.*

class AdventTest extends Specification {

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
    }
}
