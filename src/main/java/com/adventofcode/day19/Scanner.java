package com.adventofcode.day19;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner {
    private static final Pattern ID_PATTERN = Pattern.compile("\\d+");

    private final int id;
    private final List<IntVector3D> vectors;
    private final ListMultimap<IntVector3D, Integer> vectorDistances = ArrayListMultimap.create();

    public Scanner(String scannerInput) {
        List<String> lines = new ArrayList<>(scannerInput.lines().toList());
        Matcher matcher = ID_PATTERN.matcher(lines.remove(0));
        if (!matcher.find()) {
            throw new IllegalArgumentException("Wrong input");
        }
        id = Integer.parseInt(matcher.group());

        vectors = lines.stream().map(IntVector3D::new).toList();
        calculateDistances();
    }

    public Scanner(int id, List<IntVector3D> vectors) {
        this.id = id;
        this.vectors = List.copyOf(vectors);
        calculateDistances();
    }

    private void calculateDistances() {
        for (IntVector3D vector : vectors) {
            for (IntVector3D neighbour : vectors) {
                if (vector != neighbour) {
                    vectorDistances.put(vector, vector.distanceTo(neighbour));
                }
            }
        }
    }

    public List<Pair<IntVector3D, IntVector3D>> getOverlappingBeacons(Scanner other) {
        List<Pair<IntVector3D, IntVector3D>> beaconPairs = new ArrayList<>();
        for (IntVector3D beacon : vectorDistances.keySet()) {
            for (IntVector3D otherBeacon : other.vectorDistances.keySet()) {
                List<Integer> distances = vectorDistances.get(beacon);
                List<Integer> otherDistances = other.vectorDistances.get(otherBeacon);
                long overlapCount = distances.stream().filter(otherDistances::contains).limit(11).count();
                if (overlapCount >= 11) {
                    beaconPairs.add(Pair.create(beacon, otherBeacon));
                }
            }
        }
        return beaconPairs;
    }

    public Scanner adjustUsing(Adjustment adjustment) {
        List<IntVector3D> rotatedVectors = vectors.stream().map(v -> {
            Rotations rot = new Rotations(v);
            IntVector3D vector = null;
            for (int i = 0; i <= adjustment.rotationCount(); i++) {
                vector = rot.next();
            }
            vector = vector.add(adjustment.distance());
            return vector;
        }).toList();
        return new Scanner(id, rotatedVectors);
    }

    public Adjustment calculateAdjustment(List<Pair<IntVector3D, IntVector3D>> overlappingBeacons) {
        if (overlappingBeacons.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Pair<IntVector3D, IntVector3D> firstPair = overlappingBeacons.get(0);
        Rotations firstRotation = new Rotations(firstPair.getSecond());
        Pair<IntVector3D, IntVector3D> secondPair = overlappingBeacons.get(1);
        Rotations secondRotation = new Rotations(secondPair.getSecond());
        int rotations = -1;
        IntVector3D firstOtherBeacon;
        IntVector3D secondOtherBeacon;
        int distanceX;
        int distanceY;
        int distanceZ;
        do {
            firstOtherBeacon = firstRotation.next();
            secondOtherBeacon = secondRotation.next();
            distanceX = firstOtherBeacon.distanceXTo(firstPair.getFirst());
            distanceY = firstOtherBeacon.distanceYTo(firstPair.getFirst());
            distanceZ = firstOtherBeacon.distanceZTo(firstPair.getFirst());
            rotations++;
        } while (distanceX != secondOtherBeacon.distanceXTo(secondPair.getFirst())
                || distanceY != secondOtherBeacon.distanceYTo(secondPair.getFirst())
                || distanceZ != secondOtherBeacon.distanceZTo(secondPair.getFirst()));

        IntVector3D distance = new IntVector3D(distanceX, distanceY, distanceZ);
        int rotationCount = rotations;
        return new Adjustment(distance, rotationCount);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Scanner.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }

    public List<IntVector3D> getBeacons() {
        return vectors;
    }
}
