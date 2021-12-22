package com.adventofcode.day20;

import java.io.PrintStream;

public class Image {

    private final char[][] input;
    private final char filler;
    private final int minI;
    private final int minJ;
    private final int maxI;
    private final int maxJ;

    public Image(char[][] input, char filler) {
        this.input = input;
        this.filler = filler;
        minI = 0;
        minJ = 0;
        maxI = input.length - 1;
        maxJ = input[0].length - 1;
    }

    public char getPixel(int i, int j) {
        if (i < minI || i > maxI || j < minJ || j > maxJ) {
            return filler;
        } else {
            return input[i][j];
        }
    }

    public int getMinI() {
        return minI;
    }

    public int getMinJ() {
        return minJ;
    }

    public int getMaxI() {
        return maxI;
    }

    public int getMaxJ() {
        return maxJ;
    }

    public int pixelCount() {
        int count = 0;
        for (int i = 0; i <= maxI; i++) {
            for (int j = 0; j <= maxJ; j++) {
                if (input[i][j] == '#') {
                    count++;
                }
            }
        }
        return count;
    }

    public void print(PrintStream out) {
        for (int i = minI; i <= maxI; i++) {
            for (int j = minJ; j <= maxJ; j++) {
                out.print(input[i][j]);
            }
            out.println();
        }
    }
}
