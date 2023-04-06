package adt;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private ArrayList<Node> nodes;
    private HashMap<Node, ArrayList<Edge>> edges;

    public Graph(){
        this.nodes = new ArrayList<>();
        this.edges = new HashMap<>();
    }

    public void addNode(Node node){
        nodes.add(node);
    }
    public void addEdge(Edge edge){
        Node sourceNode = edge.getSourceNode();
        edges.computeIfAbsent(sourceNode, k -> new ArrayList<>()).add(edge);
    }
    public ArrayList<Edge> getEdges(Node node){
        return this.edges.get(node);
    }
    public ArrayList<Node> getNodes(){
        return this.nodes;
    }
}
