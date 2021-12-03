package com.adventofcode.common

import spock.lang.Specification

class AdventTest extends Specification {

    def "Should give correct results"() {
        given:
        def exampleDay = Class.forName("com.adventofcode.day" + day + ".Day" + day)
                .getConstructor(String).newInstance("day" + day + "/example.txt") as Day
        def inputDay = Class.forName("com.adventofcode.day" + day + ".Day" + day)
                .getConstructor(String).newInstance("day" + day + "/input.txt") as Day

        expect:
        if (type == 'example') {
            assert exampleDay.part1() == part1
            assert exampleDay.part2() == part2
        }
        if (type == 'input') {
            assert inputDay.part1() == part1
            assert inputDay.part2() == part2
        }

        where:
        day | type      | part1   | part2
        1   | 'example' | 7       | 5
        1   | 'input'   | 1754    | 1789
        2   | 'example' | 150     | 900
        2   | 'input'   | 1488669 | 1176514794
        3   | 'example' | 198     | 230
        3   | 'input'   | 749376  | 2372923
    }
}
