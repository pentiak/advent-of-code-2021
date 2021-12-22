package com.adventofcode.day21;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day21 extends AbstractAdventDay {
    private static final Pattern POSITION_PATTERN = Pattern.compile("(\\d+)$");

    @Override
    public Object part1() {
        List<String> input = InputUtils.readInputLines(inputPath);
        Matcher matcher = POSITION_PATTERN.matcher(input.get(0));
        int player1startingPos = 0;
        int player2startingPos = 0;
        if (matcher.find()) {
            player1startingPos = Integer.parseInt(matcher.group(1));
        }
        matcher = POSITION_PATTERN.matcher(input.get(1));
        if (matcher.find()) {
            player2startingPos = Integer.parseInt(matcher.group(1));
        }
        int player1Score = 0;
        int player1Pos = player1startingPos;
        int player2Score = 0;
        int player2Pos = player2startingPos;
        int player1MovementInv = 4;
        int player2MovementInv = 5;
        int diceThrows = 0;
        while (player2Score < 1000) {
            player1Pos = (player1Pos + ((10 - player1MovementInv) % 10)) % 10;
            player1Pos = player1Pos == 0 ? 10 : player1Pos;
            player1Score += player1Pos;
            player1MovementInv = (player1MovementInv + 2) % 10;
            diceThrows += 3;
            if (player1Score >= 1000) {
                break;
            }

            player2Pos = (player2Pos + ((10 - player2MovementInv) % 10)) % 10;
            player2Pos = player2Pos == 0 ? 10 : player2Pos;
            player2Score += player2Pos;
            player2MovementInv = (player2MovementInv + 2) % 10;
            diceThrows += 3;
        }
        return Math.min(player1Score, player2Score) * diceThrows;
    }
}
