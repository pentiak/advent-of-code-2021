package com.adventofcode.day2;

public class AimingSubmarine implements Submarine {

    private final SimpleSubmarine simpleSubmarine;
    private int aim = 0;

    public AimingSubmarine() {
        simpleSubmarine = new SimpleSubmarine();
    }

    @Override
    public void forward(int amount) {
        simpleSubmarine.forward(amount);
        simpleSubmarine.down(amount * aim);
    }

    @Override
    public int getForward() {
        return simpleSubmarine.getForward();
    }

    @Override
    public int getDepth() {
        return simpleSubmarine.getDepth();
    }

    public void up(int amount) {
        aim -= amount;
    }

    public void down(int amount) {
        aim += amount;
    }
}
