package com.adventofcode.day15;

import com.adventofcode.common.AbstractAdventDay;
import com.adventofcode.utils.InputUtils;
import lombok.extern.log4j.Log4j2;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

@Log4j2
public class Day15 extends AbstractAdventDay {

    @Override
    public Object part1() {
        int[][] input = InputUtils.inputIntArray(inputPath);
        SimpleDirectedWeightedGraph<Point, DefaultWeightedEdge> graph = buildGraph(input);

        int maxI = input.length - 1;
        int maxJ = input[maxI].length - 1;
        DijkstraShortestPath<Point, DefaultWeightedEdge> shortestPath = new DijkstraShortestPath<>(graph);
        return (int) shortestPath.getPathWeight(new Point(0, 0), new Point(maxI, maxJ));
    }

    @Override
    public Object part2() {
        int[][] input = InputUtils.inputIntArray(inputPath);
        SimpleDirectedWeightedGraph<Point, DefaultWeightedEdge> graph = buildBiggerGraph(input);

        int maxI = input.length * 5 - 1;
        int maxJ = input[0].length * 5 - 1;
        DijkstraShortestPath<Point, DefaultWeightedEdge> shortestPath = new DijkstraShortestPath<>(graph);
        return (int) shortestPath.getPathWeight(new Point(0, 0), new Point(maxI, maxJ));
    }

    private SimpleDirectedWeightedGraph<Point, DefaultWeightedEdge> buildBiggerGraph(int[][] input) {
        SimpleDirectedWeightedGraph<Point, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        for (int i = 0; i < input.length * 5; i++) {
            for (int j = 0; j < input[0].length * 5; j++) {

                int inputI = i % input.length;
                int inputJ = j % input[inputI].length;

                Point point = new Point(i, j);
                Point neighbour;
                graph.addVertex(point);
                int backWeight = wrap(input[inputI][inputJ] + calculateExpanse(i, j, input));
                if (j < input[0].length * 5 - 1) {//east
                    neighbour = new Point(i, j + 1);
                    graph.addVertex(neighbour);
                    int neighbourExpanse = calculateExpanse(i, j + 1, input);
                    graph.setEdgeWeight(graph.addEdge(point, neighbour), wrap(input[inputI][(inputJ + 1) % input[inputI].length] + neighbourExpanse));
                    graph.setEdgeWeight(graph.addEdge(neighbour, point), backWeight);
                }
                if (i < input.length * 5 - 1) {//south
                    neighbour = new Point(i + 1, j);
                    graph.addVertex(neighbour);
                    int neighbourExpanse = calculateExpanse(i + 1, j, input);
                    graph.setEdgeWeight(graph.addEdge(point, neighbour), wrap(input[(inputI + 1) % input.length][inputJ] + neighbourExpanse));
                    graph.setEdgeWeight(graph.addEdge(neighbour, point), backWeight);
                }
            }
        }
        return graph;
    }

    private int calculateExpanse(int i, int j, int[][] input) {
        int expanseI = i / input.length;
        int expanseJ = j / input[0].length;
        return expanseI + expanseJ;
    }

    private int wrap(int value) {
        return (value % 10) + (value / 10);
    }

    private SimpleDirectedWeightedGraph<Point, DefaultWeightedEdge> buildGraph(int[][] input) {
        SimpleDirectedWeightedGraph<Point, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                Point point = new Point(i, j);
                Point neighbour;
                graph.addVertex(point);
                if (j < input[i].length - 1) {//east
                    neighbour = new Point(i, j + 1);
                    graph.addVertex(neighbour);
                    graph.setEdgeWeight(graph.addEdge(point, neighbour), input[i][j + 1]);
                    graph.setEdgeWeight(graph.addEdge(neighbour, point), input[i][j]);
                }
                if (i < input.length - 1) {//south
                    neighbour = new Point(i + 1, j);
                    graph.addVertex(neighbour);
                    graph.setEdgeWeight(graph.addEdge(point, neighbour), input[i + 1][j]);
                    graph.setEdgeWeight(graph.addEdge(neighbour, point), input[i][j]);
                }
            }
        }
        return graph;
    }

    private record Point(int i, int j) {

    }
}
