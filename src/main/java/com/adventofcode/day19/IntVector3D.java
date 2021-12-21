package com.adventofcode.day19;

import java.util.Arrays;

public record IntVector3D(int x, int y, int z) {

    public IntVector3D(int[] coords) {
        this(coords[0], coords[1], coords[2]);
    }

    public IntVector3D(String line) {
        this(Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray());
    }

    public IntVector3D rotateByZ() {
        return new IntVector3D(-y, x, z);
    }

    public IntVector3D rotateByY() {
        return new IntVector3D(z, y, -x);
    }

    public IntVector3D rotateByX() {
        return new IntVector3D(x, -z, y);
    }

    public int distanceTo(IntVector3D neighbour) {
        return Math.abs(x - neighbour.x) + Math.abs(y - neighbour.y) + Math.abs(z - neighbour.z);
    }

    public int distanceXTo(IntVector3D other) {
        return other.x - x;
    }

    public int distanceYTo(IntVector3D other) {
        return other.y - y;
    }

    public int distanceZTo(IntVector3D other) {
        return other.z - z;
    }

    public IntVector3D add(IntVector3D delta) {
        return new IntVector3D(x + delta.x, y + delta.y, z + delta.z);
    }
}
