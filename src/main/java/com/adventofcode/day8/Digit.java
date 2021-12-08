package com.adventofcode.day8;

import org.apache.commons.collections4.SetUtils;

import java.util.HashSet;
import java.util.Set;

public record Digit(Set<Character> characters) {

    public Digit(Set<Character> characters) {
        this.characters = Set.copyOf(characters);
    }

    public Digit(String digit) {
        this(getCharacters(digit));
    }

    private static Set<Character> getCharacters(String digit) {
        Set<Character> characters = new HashSet<>(digit.length());
        for (char c : digit.toCharArray()) {
            characters.add(c);
        }
        return characters;
    }

    public int charactersCount() {
        return characters.size();
    }

    public int intersectionsWith(Digit other) {
        return SetUtils.intersection(characters, other.characters()).size();
    }

    public boolean containsCharactersOf(Digit other) {
        return characters.containsAll(other.characters());
    }
}
