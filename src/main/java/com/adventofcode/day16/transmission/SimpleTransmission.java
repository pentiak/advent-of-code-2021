package com.adventofcode.day16.transmission;

import java.util.HexFormat;

public class SimpleTransmission implements Transmission {

    private final char[] hex;

    public SimpleTransmission(String hexInput) {
        this.hex = hexInput.toCharArray();
    }

    @Override
    public boolean testBit(int n) {
        return getBit(n) > 0;
    }

    private int getBit(int n) {
        return HexFormat.fromHexDigit(hex[(n - 1) / 4]) >> ((4 - n % 4) % 4) & 1;
    }

    @Override
    public int getValue(int beginIndex, int endIndex) {
        int value = 0;
        for (int i = beginIndex; i <= endIndex; i++) {
            value = (value << 1) | getBit(i);
        }
        return value;
    }
}
