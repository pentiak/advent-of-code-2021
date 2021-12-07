package com.adventofcode.utils;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InputUtils {

    private static final String READ_FAILED = "Can't read file: ";
    private static final Pattern INPUT_SPLIT = Pattern.compile("\\r?\\n\\r?\\n");

    public static Path resourcePath(String filePath) {
        try {
            return Paths.get(Objects.requireNonNull(InputUtils.class.getClassLoader().getResource(filePath)).toURI());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid filePath: " + filePath, e);
        }
    }

    public static Stream<String> inputSplitByBlankLine(String filePath) {
        try {
            return INPUT_SPLIT.splitAsStream(Files.readString(resourcePath(filePath)));
        } catch (IOException e) {
            throw new IllegalArgumentException(READ_FAILED + filePath, e);
        }
    }

    public static List<String> readInputSplitByBlankLine(String filePath) {
        return inputSplitByBlankLine(filePath).toList();
    }

    public static Stream<String> inputLines(String filePath) {
        try {
            return Files.lines(resourcePath(filePath));
        } catch (IOException e) {
            throw new IllegalArgumentException(READ_FAILED + filePath, e);
        }
    }

    public static List<String> readInputLines(String filePath) {
        try {
            return Files.readAllLines(resourcePath(filePath));
        } catch (IOException e) {
            throw new IllegalArgumentException(READ_FAILED + filePath, e);
        }
    }

    public static IntList inputIntList(String filePath) {
        return inputIntStream(filePath)
                .collect(IntArrayList::new, IntArrayList::add, IntArrayList::addAll);
    }

    public static IntStream inputIntStream(String filePath) {
        return inputLines(filePath)
                .map(l -> l.split(","))
                .flatMap(Arrays::stream)
                .mapToInt(Integer::parseInt);
    }

    public static LongList inputLongList(String filePath) {
        return inputLines(filePath)
                .mapToLong(Long::parseLong)
                .collect(LongArrayList::new, LongArrayList::add, LongArrayList::addAll);
    }

    public static char[][] inputCharArray(String filePath) {
        List<String> lines = readInputLines(filePath);
        var result = new char[lines.size()][];
        for (var i = 0; i < lines.size(); i++) {
            result[i] = lines.get(i).toCharArray();
        }
        return result;
    }

    public static <T> List<T> parseLines(String filePath, Function<String, T> parser) {
        return inputLines(filePath).map(parser).toList();
    }
}