package com.adventofcode.day4;

import it.unimi.dsi.fastutil.ints.IntIterator;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class BingoGame {
    private final IntIterator chosenNumbers;
    private final List<BingoBoard> boards;
    private BingoBoard firstWinningBoard = null;
    private BingoBoard lastWinningBoard = null;
    private int firstWinningNumber = -1;
    private int chosenNumber = -1;

    public BingoGame(String inputPath) {
        BingoInputParser inputParser = new BingoInputParser(inputPath);
        chosenNumbers = inputParser.getChosenNumbers();
        boards = inputParser.getBoards();
    }

    public void play() {
        while (chosenNumbers.hasNext() && notAllBoardsWon()) {
            chosenNumber = chosenNumbers.nextInt();
            log.trace("Chosen number: '{}'", chosenNumber);
            for (BingoBoard board : boards) {
                if (board.isWinning()) {
                    continue;
                }
                mark(board);
            }
        }
    }

    private void mark(BingoBoard board) {
        board.mark(chosenNumber);
        if (board.isWinning()) {
            if (firstWinningBoard == null) {
                firstWinningBoard = board;
                firstWinningNumber = chosenNumber;
            }
            lastWinningBoard = board;
        }
    }

    private boolean notAllBoardsWon() {
        return boards.stream().anyMatch(b -> !b.isWinning());
    }

    public int getFinalScoreOfFirstWinningBoard() {
        if (firstWinningBoard == null) {
            throw new IllegalStateException("No board has won yet");
        }

        return firstWinningBoard.getSumOfUnmarkedNumbers() * firstWinningNumber;
    }

    public int getFinalScoreOfLastWinningBoard() {
        if (lastWinningBoard == null) {
            throw new IllegalStateException("No board has won yet");
        }

        return lastWinningBoard.getSumOfUnmarkedNumbers() * chosenNumber;
    }
}
