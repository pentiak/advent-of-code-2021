package com.adventofcode.day4;

import com.adventofcode.utils.InputUtils;
import it.unimi.dsi.fastutil.ints.IntIterator;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class BingoInputParser {

    private final List<String> inputs;
    private final BingoBoardParser boardParser = new BingoBoardParser();

    public BingoInputParser(String inputPath) {
        inputs = InputUtils.readInputSplitByBlankLine(inputPath);
    }

    public IntIterator getChosenNumbers() {
        int[] ints = Arrays.stream(inputs.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
        return new IntIterator() {
            private int index = 0;

            @Override
            public int nextInt() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return ints[index++];
            }

            @Override
            public boolean hasNext() {
                return index < ints.length;
            }
        };
    }

    public List<BingoBoard> getBoards() {
        return IntStream.range(1, inputs.size())
                .mapToObj(inputs::get)
                .map(boardParser::parse)
                .toList();
    }
}
