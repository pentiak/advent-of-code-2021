package com.adventofcode.day11;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;

import java.util.HashMap;
import java.util.Map;

public class Day11 extends AbstractAdventDay {

    @Override
    public Object part1() {
        int[][] input = InputUtils.inputIntArray(inputPath);
        Map<Position, Octopussy> octopi = new HashMap<>();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                octopi.put(new Position(i, j), new Octopussy(input[i][j]));
            }
        }

        int sum = 0;
        for (int s = 0; s < 100; s++) {
            sum += nextStep(octopi);
        }
        return sum;
    }

    @Override
    public Object part2() {
        int[][] input = InputUtils.inputIntArray(inputPath);
        Map<Position, Octopussy> octopi = new HashMap<>();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                octopi.put(new Position(i, j), new Octopussy(input[i][j]));
            }
        }

        int step = 1;
        while (nextStep(octopi) != 100) {
            step++;
        }
        return step;
    }

    private int nextStep(Map<Position, Octopussy> octopi) {
        increaseAllEnergies(octopi);
        int flashCount = flashEnergeticOnes(octopi);
        drainFlashedEnergy(octopi);
        return flashCount;
    }

    private void drainFlashedEnergy(Map<Position, Octopussy> octopi) {
        octopi.values().stream().filter(Octopussy::hasFlashed).forEach(Octopussy::drain);
    }

    private int flashEnergeticOnes(Map<Position, Octopussy> octopi) {
        int flashedSum = 0;
        int flashedInWave;
        while ((flashedInWave = flashWave(octopi)) > 0) {
            flashedSum += flashedInWave;
        }
        return flashedSum;
    }

    private int flashWave(Map<Position, Octopussy> octopi) {
        int flashes = 0;
        for (Map.Entry<Position, Octopussy> entry : octopi.entrySet()) {
            if (entry.getValue().flash()) {
                increaseNeighboursEnergy(octopi, entry.getKey());
                flashes++;
            }
        }
        return flashes;
    }

    private void increaseNeighboursEnergy(Map<Position, Octopussy> octopi, Position position) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                Octopussy octopussy = octopi.get(new Position(position.i() + i, position.j() + j));
                if (octopussy != null) {
                    octopussy.increaseEnergy();
                }
            }
        }
    }

    private void increaseAllEnergies(Map<Position, Octopussy> octopi) {
        octopi.values().forEach(Octopussy::increaseEnergy);
    }

    private record Position(int i, int j) {

    }

    private static class Octopussy {
        private int energyLevel = 0;
        private boolean flashed = false;

        public Octopussy(int energyLevel) {
            this.energyLevel = energyLevel;
        }

        public void increaseEnergy() {
            energyLevel += 1;
        }

        public boolean hasFlashed() {
            return flashed;
        }

        public void drain() {
            flashed = false;
            energyLevel = 0;
        }

        public boolean flash() {
            if (energyLevel > 9 && !flashed) {
                flashed = true;
                return true;
            }
            return false;
        }
    }
}
