package com.adventofcode.day4;

public class BingoBoard {
    private final int[][] board;
    private final boolean[][] marks;
    private boolean winning = false;

    public BingoBoard(int[][] board) {
        this.board = board;
        this.marks = new boolean[board.length][board[0].length];
    }

    public void mark(int chosenNumber) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == chosenNumber) {
                    marks[i][j] = true;
                    if (isRowMarked(i) || isColumnMarked(j)) {
                        winning = true;
                    }
                }
            }
        }
    }

    private boolean isColumnMarked(int column) {
        for (int i = 0; i < marks.length; i++) {
            if (!marks[i][column]) {
                return false;
            }
        }
        return true;
    }

    private boolean isRowMarked(int row) {
        for (int i = 0; i < marks[0].length; i++) {
            if (!marks[row][i]) {
                return false;
            }
        }
        return true;
    }

    public boolean isWinning() {
        return winning;
    }

    public int getSumOfUnmarkedNumbers() {
        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!marks[i][j]) {
                    sum += board[i][j];
                }
            }
        }
        return sum;
    }
}
