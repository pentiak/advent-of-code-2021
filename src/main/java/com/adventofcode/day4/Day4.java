package com.adventofcode.day4;

import com.adventofcode.common.Day;

public class Day4 extends Day {

    private final BingoGame game;

    public Day4(String inputPath) {
        super(inputPath);
        game = new BingoGame(inputPath);
        game.play();
    }

    @Override
    public Object part1() {
        return game.getFinalScoreOfFirstWinningBoard();
    }

    @Override
    public Object part2() {
        return game.getFinalScoreOfLastWinningBoard();
    }
}
