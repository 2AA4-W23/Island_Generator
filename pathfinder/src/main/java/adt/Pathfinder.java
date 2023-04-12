package adt;

import java.util.*;

public class Pathfinder implements Path {
    public List<Node> findShortestPath(Graph graph, Node startNode, Node endNode) {
        Map<Node, Node> predecessorMap = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();

        queue.add(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            if (currentNode.equals(endNode)) {
                return buildPath(predecessorMap, endNode);
            }
            List<Edge> edges = graph.getEdges(currentNode);

            for (Edge edge : edges) {
                Node neighborNode = edge.getDestinationNode();
                if (!visited.contains(neighborNode)) {
                    predecessorMap.put(neighborNode, currentNode);
                    visited.add(neighborNode);
                    queue.add(neighborNode);
                }
            }
        }
        return null;
    }
    public List<Node> buildPath(Map<Node, Node> predecessorMap, Node endNode) {
        LinkedList<Node> path = new LinkedList<>();
        Node currentNode = endNode;
        while (predecessorMap.containsKey(currentNode)) {
            path.addFirst(currentNode);
            currentNode = predecessorMap.get(currentNode);
        }
        path.addFirst(currentNode);
        return path;
    }
}

