package com.adventofcode.day12;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.extern.log4j.Log4j2;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.GraphWalk;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.*;

@Log4j2
public class Day12 extends AbstractAdventDay {

    @Override
    public Object part1() {
        Graph<Cave, DefaultEdge> graph = new SimpleDirectedGraph<>(DefaultEdge.class);
        InputUtils.inputLines(inputPath).map(s -> s.split("-")).forEach(a -> {
            Cave c1 = new Cave(a[0]);
            Cave c2 = new Cave(a[1]);
            graph.addVertex(c1);
            graph.addVertex(c2);
            graph.addEdge(c1, c2);
            graph.addEdge(c2, c1);
        });

        List<GraphPath<Cave, DefaultEdge>> allPaths = findAllPaths(graph);
        return allPaths.size();
    }

    @Override
    public Object part2() {
        Graph<Cave, DefaultEdge> graph = new SimpleDirectedGraph<>(DefaultEdge.class);
        InputUtils.inputLines(inputPath).map(s -> s.split("-")).forEach(a -> {
            Cave c1 = new Cave(a[0]);
            Cave c2 = new Cave(a[1]);
            graph.addVertex(c1);
            graph.addVertex(c2);
            graph.addEdge(c1, c2);
            graph.addEdge(c2, c1);
        });

        return countAllPathsV2(graph);
    }

    private List<GraphPath<Cave, DefaultEdge>> findAllPaths(Graph<Cave, DefaultEdge> graph) {
        Set<GraphPath<Cave, DefaultEdge>> completedPaths = new HashSet<>();
        Cave startCave = new Cave("start");
        Cave endCave = new Cave("end");
        Deque<List<DefaultEdge>> incompletePaths = new LinkedList<>();

        for (DefaultEdge edge : graph.outgoingEdgesOf(startCave)) {
            if (graph.getEdgeTarget(edge).equals(endCave)) {
                completedPaths.add(new GraphWalk<>(graph, startCave, endCave, Collections.singletonList(edge), 0));
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
                List<DefaultEdge> newPath = new ArrayList<>(incompletePath);
                newPath.add(edge);
                Cave edgeTarget = graph.getEdgeTarget(edge);
                if (edgeTarget.equals(endCave)) {
                    completedPaths.add(new GraphWalk<>(graph, startCave, endCave, newPath, 0));
                } else if (edgeTarget.big() || !visitedCaves.contains(edgeTarget)) {
                    incompletePaths.addFirst(newPath);
                }
            }
        }


        return List.copyOf(completedPaths);
    }

    private int countAllPathsV2(Graph<Cave, DefaultEdge> graph) {
        Set<GraphPath<Cave, DefaultEdge>> completedPaths = new HashSet<>();
        Cave startCave = new Cave("start");
        Cave endCave = new Cave("end");
        Deque<List<DefaultEdge>> incompletePaths = new LinkedList<>();

        for (DefaultEdge edge : graph.outgoingEdgesOf(startCave)) {
            if (graph.getEdgeTarget(edge).equals(endCave)) {
                completedPaths.add(new GraphWalk<>(graph, startCave, endCave, Collections.singletonList(edge), 0));
                continue;
            }
            List<DefaultEdge> incompletePath = new LinkedList<>();
            incompletePath.add(edge);
            incompletePaths.add(incompletePath);
        }

        while (!incompletePaths.isEmpty()) {
            List<DefaultEdge> incompletePath = incompletePaths.poll();
            DefaultEdge lastEdge = incompletePath.get(incompletePath.size() - 1);
            Cave lastCave = graph.getEdgeTarget(lastEdge);

            Object2IntMap<Cave> visitedCaves = new Object2IntOpenHashMap<>();
            boolean smallVisitedTwice = false;
            for (DefaultEdge pathEdge : incompletePath) {
                Cave targetCave = graph.getEdgeTarget(pathEdge);
                if (targetCave.big()) {
                    continue;
                }
                if (visitedCaves.containsKey(targetCave)) {
                    visitedCaves.put(targetCave, 2);
                    smallVisitedTwice = true;
                } else {
                    visitedCaves.put(targetCave, 1);
                }
            }

            for (DefaultEdge edge : graph.outgoingEdgesOf(lastCave)) {
                Cave edgeTarget = graph.getEdgeTarget(edge);
                if (edgeTarget.equals(endCave)) {
                    LinkedList<DefaultEdge> newPath = new LinkedList<>(incompletePath);
                    newPath.add(edge);
                    completedPaths.add(new GraphWalk<>(graph, startCave, endCave, newPath, 0));
                } else if (!edgeTarget.equals(startCave) && (edgeTarget.big() || (!visitedCaves.containsKey(edgeTarget) || !smallVisitedTwice))) {
                    LinkedList<DefaultEdge> newPath = new LinkedList<>(incompletePath);
                    newPath.add(edge);
                    incompletePaths.add(newPath);
                }
            }
        }


        return completedPaths.size();
    }

    private record Cave(String name, boolean big) {
        public Cave(String name) {
            this(name, Character.isUpperCase(name.charAt(0)));
        }
    }
}
