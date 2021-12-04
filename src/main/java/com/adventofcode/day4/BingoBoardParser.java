package com.adventofcode.day4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BingoBoardParser {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

    public BingoBoard parse(String input) {
        Matcher matcher = NUMBER_PATTERN.matcher(input);
        int[][] board = new int[5][5];
        int numberIndex = 0;
        while (matcher.find()) {
            int number = Integer.parseInt(matcher.group());
            int row = numberIndex / 5;
            int column = numberIndex % 5;
            board[row][column] = number;
            numberIndex++;
        }
        return new BingoBoard(board);
    }
}
