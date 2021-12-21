package com.adventofcode.day19;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import org.apache.commons.math3.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Day19 extends AbstractAdventDay {

    @Override
    public Object part1() {
        List<String> scannerInputs = InputUtils.readInputSplitByBlankLine(inputPath);
        List<Scanner> scannersToOrient = scannerInputs.stream().map(Scanner::new).collect(Collectors.toCollection(LinkedList::new));
        List<Scanner> orientedScanners = new LinkedList<>();
        orientedScanners.add(scannersToOrient.remove(0));
        Set<IntVector3D> beacons = new HashSet<>(orientedScanners.get(0).getBeacons());
        while (!scannersToOrient.isEmpty()) {
            for (Iterator<Scanner> iterator = scannersToOrient.iterator(); iterator.hasNext(); ) {
                Scanner scannerToOrient = iterator.next();
                for (Scanner orientedScanner : orientedScanners) {
                    List<Pair<IntVector3D, IntVector3D>> overlappingBeacons = orientedScanner.getOverlappingBeacons(scannerToOrient);
                    if (!overlappingBeacons.isEmpty()) {
                        Scanner newOrientedScanner = scannerToOrient.adjustUsing(scannerToOrient.calculateAdjustment(overlappingBeacons));
                        beacons.addAll(newOrientedScanner.getBeacons());
                        iterator.remove();
                        orientedScanners.add(newOrientedScanner);
                        break;
                    }
                }
            }
        }

        return beacons.size();
    }

    @Override
    public Object part2() {
        List<String> scannerInputs = InputUtils.readInputSplitByBlankLine(inputPath);
        List<Scanner> scannersToOrient = scannerInputs.stream().map(Scanner::new).collect(Collectors.toCollection(LinkedList::new));
        List<Scanner> orientedScanners = new LinkedList<>();
        List<IntVector3D> scannerDistances = new ArrayList<>();
        scannerDistances.add(new IntVector3D(0, 0, 0));
        orientedScanners.add(scannersToOrient.remove(0));
        while (!scannersToOrient.isEmpty()) {
            for (Iterator<Scanner> iterator = scannersToOrient.iterator(); iterator.hasNext(); ) {
                Scanner scannerToOrient = iterator.next();
                for (Scanner orientedScanner : orientedScanners) {
                    List<Pair<IntVector3D, IntVector3D>> overlappingBeacons = orientedScanner.getOverlappingBeacons(scannerToOrient);
                    if (!overlappingBeacons.isEmpty()) {
                        Adjustment adjustment = scannerToOrient.calculateAdjustment(overlappingBeacons);
                        Scanner newOrientedScanner = scannerToOrient.adjustUsing(adjustment);
                        scannerDistances.add(adjustment.distance());
                        iterator.remove();
                        orientedScanners.add(newOrientedScanner);
                        break;
                    }
                }
            }
        }

        int maxDistance = Integer.MIN_VALUE;
        for (int i = 0; i < scannerDistances.size(); i++) {
            for (int j = 0; j < scannerDistances.size(); j++) {
                if (i != j) {
                    int distance = scannerDistances.get(i).distanceTo(scannerDistances.get(j));
                    if (distance > maxDistance) {
                        maxDistance = distance;
                    }
                }
            }
        }
        return maxDistance;
    }
}
