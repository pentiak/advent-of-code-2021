package com.adventofcode.day12;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public class CavePath {

    private final Deque<Cave> caves = new LinkedList<>();
    private final Set<Cave> smallCaveVisits = new HashSet<>();
    private boolean smallCaveVisitedTwice = false;

    public CavePath() {
    }

    public CavePath(CavePath other) {
        this();
        caves.addAll(other.caves);
        smallCaveVisits.addAll(other.smallCaveVisits);
        smallCaveVisitedTwice = other.smallCaveVisitedTwice;
    }

    public void addCave(Cave cave) {
        caves.add(cave);
        if (!cave.big()) {
            if (smallCaveVisits.contains(cave)) {
                smallCaveVisitedTwice = true;
            } else {
                smallCaveVisits.add(cave);
            }
        }
    }

    public Cave getLastCave() {
        return caves.peekLast();
    }

    public boolean canVisit(Cave cave) {
        return cave.big() || !smallCaveVisits.contains(cave) || !smallCaveVisitedTwice;
    }

    @Override
    public String toString() {
        return caves.stream().map(Cave::name).collect(Collectors.joining("->"));
    }
}
