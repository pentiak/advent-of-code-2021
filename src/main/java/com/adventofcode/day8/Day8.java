package com.adventofcode.day8;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Log4j2
public class Day8 extends AbstractAdventDay {

    @Override
    public Object part1() {
        List<Input> inputs = InputUtils.parseLines(inputPath, this::parseInput);
        return inputs.stream()
                .map(Input::output)
                .flatMap(List::stream)
                .mapToInt(Digit::charactersCount)
                .filter(i -> i == 2 || i == 4 || i == 3 || i == 7)
                .count();
    }

    @Override
    public Object part2() {
        List<Input> inputs = InputUtils.parseLines(inputPath, this::parseInput);

        return inputs.stream().mapToInt(this::deduceSignals).sum();
    }

    private int deduceSignals(Input input) {
        List<Digit> digitsToFind = new ArrayList<>(input.digits());
        Digit[] knownDigits = new Digit[10];

        findSimpleDigits(digitsToFind, knownDigits);
        findDigitSix(digitsToFind, knownDigits);
        findDigitsNineZero(digitsToFind, knownDigits);
        findDigitsTwoThreeFive(digitsToFind, knownDigits);

        return reconstructNumber(knownDigits, input.output());
    }

    private int reconstructNumber(Digit[] knownDigits, List<Digit> output) {
        int number = 0;
        for (int i = 0; i < output.size(); i++) {
            Digit outputDigit = output.get(output.size() - 1 - i);
            int digitValue = getDigitValue(knownDigits, outputDigit);
            number += (int) Math.pow(10, i) * digitValue;
        }
        return number;
    }

    private void findDigitsTwoThreeFive(List<Digit> digitsToFind, Digit[] knownDigits) {
        Iterator<Digit> iterator = digitsToFind.iterator();
        while (iterator.hasNext()) {
            Digit digit = iterator.next();
            if (digit.containsCharactersOf(knownDigits[1])) {
                knownDigits[3] = digit;
                iterator.remove();
            } else if (knownDigits[6].containsCharactersOf(digit)) {
                knownDigits[5] = digit;
                iterator.remove();
            } else {
                knownDigits[2] = digit;
                iterator.remove();
            }
        }
    }

    private void findDigitsNineZero(List<Digit> digitsToFind, Digit[] knownDigits) {
        Iterator<Digit> iterator = digitsToFind.iterator();
        while (iterator.hasNext()) {
            Digit digit = iterator.next();
            if (digit.charactersCount() == 6) {
                if (digit.containsCharactersOf(knownDigits[4])) {
                    knownDigits[9] = digit;
                    iterator.remove();
                } else {
                    knownDigits[0] = digit;
                    iterator.remove();
                }
            }
        }
    }

    private int getDigitValue(Digit[] knownDigits, Digit digit) {
        for (int i = 0; i < knownDigits.length; i++) {
            if (knownDigits[i].equals(digit)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Digit not found");
    }

    private void findDigitSix(List<Digit> digitsToFind, Digit[] knownDigits) {
        Iterator<Digit> iterator = digitsToFind.iterator();
        while (iterator.hasNext()) {
            Digit digit = iterator.next();
            if (digit.charactersCount() == 6 && digit.intersectionsWith(knownDigits[1]) == 1) {
                knownDigits[6] = digit;
                iterator.remove();
                return;
            }
        }
        throw new IllegalStateException("Digit 6 not found");
    }

    private void findSimpleDigits(List<Digit> digitsToFind, Digit[] knownDigits) {
        Iterator<Digit> iterator = digitsToFind.iterator();
        while (iterator.hasNext()) {
            Digit digit = iterator.next();
            if (digit.charactersCount() == 2) {
                knownDigits[1] = digit;
                iterator.remove();
            } else if (digit.charactersCount() == 3) {
                knownDigits[7] = digit;
                iterator.remove();
            } else if (digit.charactersCount() == 4) {
                knownDigits[4] = digit;
                iterator.remove();
            } else if (digit.charactersCount() == 7) {
                knownDigits[8] = digit;
                iterator.remove();
            }
        }
    }

    private Input parseInput(String line) {
        String[] split = line.split(" \\| ");
        List<Digit> digits = getDigits(split[0]);
        List<Digit> output = getDigits(split[1]);

        return new Input(digits, output);
    }

    private List<Digit> getDigits(String digitSequence) {
        return Arrays.stream(digitSequence.split(" ")).map(Digit::new).toList();
    }

    private record Input(List<Digit> digits, List<Digit> output) {

    }
}
