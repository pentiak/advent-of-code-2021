package com.adventofcode.day12;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import lombok.extern.log4j.Log4j2;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.*;

@Log4j2
public class Day12 extends AbstractAdventDay {

    @Override
    public Object part1() {
        return countAllPaths(buildGraph());
    }

    @Override
    public Object part2() {
        return countAllPathsLenient(buildGraph());
    }

    private Graph<Cave, DefaultEdge> buildGraph() {
        Graph<Cave, DefaultEdge> graph = new SimpleDirectedGraph<>(DefaultEdge.class);
        InputUtils.inputLines(inputPath).map(s -> s.split("-")).forEach(a -> {
            Cave c1 = new Cave(a[0]);
            Cave c2 = new Cave(a[1]);
            graph.addVertex(c1);
            graph.addVertex(c2);
            graph.addEdge(c1, c2);
            graph.addEdge(c2, c1);
        });
        return graph;
    }

    private int countAllPaths(Graph<Cave, DefaultEdge> graph) {
        int completedPaths = 0;
        Cave startCave = new Cave("start");
        Cave endCave = new Cave("end");
        Deque<List<DefaultEdge>> incompletePaths = new LinkedList<>();

        for (DefaultEdge edge : graph.outgoingEdgesOf(startCave)) {
            if (graph.getEdgeTarget(edge).equals(endCave)) {
                completedPaths++;
                continue;
            }
            incompletePaths.add(Collections.singletonList(edge));
        }

        while (!incompletePaths.isEmpty()) {
            List<DefaultEdge> incompletePath = incompletePaths.poll();
            DefaultEdge lastEdge = incompletePath.get(incompletePath.size() - 1);
            Cave lastCave = graph.getEdgeTarget(lastEdge);

            Set<Cave> visitedCaves = new HashSet<>();
            for (DefaultEdge pathEdge : incompletePath) {
                visitedCaves.add(graph.getEdgeSource(pathEdge));
                visitedCaves.add(graph.getEdgeTarget(pathEdge));
            }

            for (DefaultEdge edge : graph.outgoingEdgesOf(lastCave)) {
                Cave edgeTarget = graph.getEdgeTarget(edge);
                if (edgeTarget.equals(endCave)) {
                    completedPaths++;
                } else if (edgeTarget.big() || !visitedCaves.contains(edgeTarget)) {
                    List<DefaultEdge> newPath = new LinkedList<>(incompletePath);
                    newPath.add(edge);
                    incompletePaths.addFirst(newPath);
                }
            }
        }


        return completedPaths;
    }

    private int countAllPathsLenient(Graph<Cave, DefaultEdge> graph) {
        int completedPaths = 0;
        Cave startCave = new Cave("start");
        Cave endCave = new Cave("end");
        Deque<CavePath> incompletePaths = new LinkedList<>();

        for (DefaultEdge edge : graph.outgoingEdgesOf(startCave)) {
            Cave targetCave = graph.getEdgeTarget(edge);
            if (targetCave.equals(endCave)) {
                completedPaths++;
                continue;
            }
            CavePath incompletePath = new CavePath();
            incompletePath.addCave(targetCave);
            incompletePaths.add(incompletePath);
        }

        while (!incompletePaths.isEmpty()) {
            CavePath incompletePath = incompletePaths.poll();
            Cave lastCave = incompletePath.getLastCave();

            for (DefaultEdge edge : graph.outgoingEdgesOf(lastCave)) {
                Cave edgeTarget = graph.getEdgeTarget(edge);
                if (edgeTarget.equals(endCave)) {
                    completedPaths++;
                } else if (!edgeTarget.equals(startCave) && incompletePath.canVisit(edgeTarget)) {
                    CavePath newPath = new CavePath(incompletePath);
                    newPath.addCave(edgeTarget);
                    incompletePaths.addFirst(newPath);
                }
            }
        }

        return completedPaths;
    }
}
