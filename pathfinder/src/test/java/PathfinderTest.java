import adt.Edge;
import adt.Graph;
import adt.Node;
import adt.Pathfinder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PathfinderTest {
    @Test
    public void testShortestPath() {
        // create nodes
        Node nodeA = new Node(new HashMap<>(), 1);
        Node nodeB = new Node(new HashMap<>(), 2);
        Node nodeC = new Node(new HashMap<>(), 3);
        Node nodeD = new Node(new HashMap<>(), 4);
        Node nodeE = new Node(new HashMap<>(), 5);
        Node nodeF = new Node(new HashMap<>(), 6);
        Node nodeG = new Node(new HashMap<>(), 7);

        // create edges
        Edge edge1 = new Edge(nodeA, nodeB, new HashMap<>());
        Edge edge2 = new Edge(nodeA, nodeC, new HashMap<>());
        Edge edge3 = new Edge(nodeB, nodeD, new HashMap<>());
        Edge edge4 = new Edge(nodeC, nodeE, new HashMap<>());
        Edge edge5 = new Edge(nodeD, nodeE, new HashMap<>());
        Edge edge6 = new Edge(nodeD, nodeF, new HashMap<>());
        Edge edge7 = new Edge(nodeE, nodeF, new HashMap<>());
        Edge edge8 = new Edge(nodeE, nodeG, new HashMap<>());
        Edge edge9 = new Edge(nodeB, nodeG, new HashMap<>());

        // create graph
        Graph graph = new Graph();
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);
        graph.addNode(nodeG);

        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.addEdge(edge3);
        graph.addEdge(edge4);
        graph.addEdge(edge5);
        graph.addEdge(edge6);
        graph.addEdge(edge7);
        graph.addEdge(edge8);
        graph.addEdge(edge9);

        // find shortest path from nodeA to nodeB (Base Case)
        Pathfinder pathfinderBase = new Pathfinder();
        List<Node> shortestPathBase = pathfinderBase.findShortestPath(graph, nodeA, nodeB);

        ArrayList<Node> expectedPathBase = new ArrayList<>();
        expectedPathBase.add(nodeA);
        expectedPathBase.add(nodeB);

        assertEquals(expectedPathBase, shortestPathBase);

        // find shortest path from nodeA to nodeE (Invariant)
        Pathfinder pathfinder = new Pathfinder();
        List<Node> shortestPath = pathfinder.findShortestPath(graph, nodeA, nodeG);

        // expected shortest path: [nodeA, nodeC, nodeD, nodeE]
        ArrayList<Node> expectedPath = new ArrayList<>();
        expectedPath.add(nodeA);
        expectedPath.add(nodeB);
        expectedPath.add(nodeG);

        assertEquals(expectedPath, shortestPath);
    }

    @Test
    public void testPathNotFound() {
        // create nodes
        Node nodeA = new Node(new HashMap<>(), 1);
        Node nodeB = new Node(new HashMap<>(), 2);
        Node nodeC = new Node(new HashMap<>(), 3);
        Node nodeD = new Node(new HashMap<>(), 4);
        Node nodeE = new Node(new HashMap<>(), 5);

        // create edges
        Edge edge1 = new Edge(nodeA, nodeB, new HashMap<>());
        Edge edge1b = new Edge(nodeB, nodeA, new HashMap<>());
        Edge edge2 = new Edge(nodeA, nodeC, new HashMap<>());
        Edge edge2b = new Edge(nodeC, nodeA, new HashMap<>());
        Edge edge3 = new Edge(nodeB, nodeD, new HashMap<>());
        Edge edge3b = new Edge(nodeD, nodeB, new HashMap<>());
        Edge edge4 = new Edge(nodeC, nodeD, new HashMap<>());
        Edge edge4b = new Edge(nodeD, nodeC, new HashMap<>());

        // create graph
        Graph graph = new Graph();
        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);

        graph.addEdge(edge1);
        graph.addEdge(edge1b);
        graph.addEdge(edge2);
        graph.addEdge(edge2b);
        graph.addEdge(edge3);
        graph.addEdge(edge3b);
        graph.addEdge(edge4);
        graph.addEdge(edge4b);

        // find shortest path from nodeA to nodeE
        Pathfinder pathfinder = new Pathfinder();
        List<Node> shortestPathNull = pathfinder.findShortestPath(graph, nodeA, nodeE);

        assertNull(shortestPathNull);
    }
}
