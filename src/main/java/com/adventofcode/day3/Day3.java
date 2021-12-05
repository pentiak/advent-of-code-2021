package com.adventofcode.day3;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class Day3 extends AbstractAdventDay {

    @Override
    public Object part1() {
        List<char[]> report = InputUtils.parseLines(inputPath, String::toCharArray);
        char[] gammaRate = new char[report.get(0).length];
        for (int i = 0; i < gammaRate.length; i++) {
            int[] bitFrequencies = getBitFrequencies(report, i);
            gammaRate[i] = bitFrequencies[0] > bitFrequencies[1] ? '0' : '1';
        }
        int gammaRateDecimal = Integer.parseInt(new String(gammaRate), 2);
        int epislonRateDecimal = ~gammaRateDecimal & ((1 << gammaRate.length) - 1);
        return gammaRateDecimal * epislonRateDecimal;
    }

    @Override
    public Object part2() {
        List<char[]> report = InputUtils.parseLines(inputPath, String::toCharArray);
        List<char[]> oxygenReport = report;
        List<char[]> co2Report = report;

        int oxygenBitIndex = 0;
        while (oxygenReport.size() != 1) {
            int bitIndex = oxygenBitIndex;
            char bitToKeep = pickMostFrequentBit(getBitFrequencies(oxygenReport, bitIndex));
            oxygenReport = oxygenReport.stream().filter(b -> b[bitIndex] == bitToKeep).toList();
            log.trace("OxygenReport = {}", oxygenReport);
            oxygenBitIndex++;
        }

        int co2BitIndex = 0;
        while (co2Report.size() != 1) {
            int bitIndex = co2BitIndex;
            char bitToKeep = pickLeastFrequentBit(getBitFrequencies(co2Report, bitIndex));
            co2Report = co2Report.stream().filter(b -> b[bitIndex] == bitToKeep).toList();
            log.trace("Co2Report = {}", co2Report);
            co2BitIndex++;
        }

        return Integer.parseInt(new String(oxygenReport.get(0)), 2) * Integer.parseInt(new String(co2Report.get(0)), 2);
    }

    private char pickMostFrequentBit(int[] bitFrequencies) {
        if (bitFrequencies[0] > bitFrequencies[1]) {
            return '0';
        } else if (bitFrequencies[0] == bitFrequencies[1]) {
            return '1';
        } else {
            return '1';
        }
    }

    private char pickLeastFrequentBit(int[] bitFrequencies) {
        if (bitFrequencies[0] < bitFrequencies[1]) {
            return '0';
        } else if (bitFrequencies[0] == bitFrequencies[1]) {
            return '0';
        } else {
            return '1';
        }
    }

    private int[] getBitFrequencies(List<char[]> report, int position) {
        int zeroCount = 0;
        int oneCount = 0;
        for (char[] chars : report) {
            if (chars[position] == '0') {
                zeroCount++;
            } else {
                oneCount++;
            }
        }
        return new int[]{zeroCount, oneCount};
    }
}
