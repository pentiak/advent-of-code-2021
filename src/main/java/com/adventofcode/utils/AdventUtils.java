package com.adventofcode.utils;

import com.adventofcode.common.AdventDay;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Constructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdventUtils {

    public static AdventDay newAdventDayInstance(int day) {
        try {
            return (AdventDay) getConstructor(day).newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not create new example Day" + day + " instance", e);
        }
    }

    public static String getInputPath(int day) {
        return "day" + day + "/input.txt";
    }

    public static String getExamplePath(int day) {
        return "day" + day + "/example.txt";
    }

    private static Constructor<?> getConstructor(int day) throws NoSuchMethodException, ClassNotFoundException {
        return Class.forName("com.adventofcode.day" + day + ".Day" + day).getConstructor();
    }
}
