package adt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Path {
    List<Node> findShortestPath(Graph graph, Node startNode, Node endNode);
    List<Node> buildPath(Map<Node, Node> predecessorMap, Node endNode);
}
