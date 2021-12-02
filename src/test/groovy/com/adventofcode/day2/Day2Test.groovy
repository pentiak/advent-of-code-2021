package com.adventofcode.day2

import spock.lang.Shared
import spock.lang.Specification

class Day2Test extends Specification {

    @Shared
    final def examplePath = "day2/example.txt"

    @Shared
    final def inputPath = "day2/input.txt"

    def "Example1"() {
        given:
        def day = new Day2(examplePath)

        expect:
        day.part1() == 150
    }

    def "Example2"() {
        given:
        def day = new Day2(examplePath)

        expect:
        day.part2() == 900
    }

    def "Part1"() {
        given:
        def day = new Day2(inputPath)

        expect:
        day.part1() == 1488669
    }

    def "Part2"() {
        given:
        def day = new Day2(inputPath)

        expect:
        day.part2() == 1176514794
    }
}
