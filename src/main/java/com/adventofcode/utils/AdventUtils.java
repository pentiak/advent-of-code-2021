package com.adventofcode.utils;

import com.adventofcode.common.Day;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Constructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdventUtils {

    public static Day newExampleDayInstance(int day) {
        try {
            return (Day) getConstructor(day).newInstance("day" + day + "/example.txt");
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not create new example Day" + day + " instance", e);
        }
    }

    public static Day newInputDayInstance(int day) {
        try {
            return (Day) getConstructor(day).newInstance("day" + day + "/input.txt");
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not create new input Day" + day + " instance", e);
        }
    }

    private static Constructor<?> getConstructor(int day) throws NoSuchMethodException, ClassNotFoundException {
        return Class.forName("com.adventofcode.day" + day + ".Day" + day).getConstructor(String.class);
    }
}
