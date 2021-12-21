package com.adventofcode.day19;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Rotations implements Iterator<IntVector3D> {

    int rotation = 0;
    private IntVector3D vector;

    public Rotations(IntVector3D vector) {
        this.vector = vector;
    }

    public static Stream<IntVector3D> stream(IntVector3D vector) {
        return StreamSupport.stream(Spliterators.spliterator(new Rotations(vector), 24, Spliterator.SIZED), false);
    }

    @Override
    public boolean hasNext() {
        return rotation <= 23;
    }

    @Override
    public IntVector3D next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        if (rotation == 0) {
            rotation++;
            return vector;
        }
        vector = vector.rotateByZ();
        if (rotation >= 4 && rotation <= 16 && rotation % 4 == 0) {
            vector = vector.rotateByY();
        }
        if (rotation == 16) {
            vector = vector.rotateByX();
        }
        if (rotation == 20) {
            vector = vector.rotateByX().rotateByX();
        }
        rotation++;
        return vector;
    }
}
