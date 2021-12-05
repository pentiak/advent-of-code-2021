package com.adventofcode.day4;

import com.adventofcode.common.AbstractAdventDay;

public class Day4 extends AbstractAdventDay {

    private BingoGame game;

    @Override
    public void setInputPath(String inputPath) {
        super.setInputPath(inputPath);
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
