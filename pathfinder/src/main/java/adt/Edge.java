package adt;

import java.util.Map;

public class Edge {

    private Node sourceNode;
    private Node destinationNode;
    private Map<String, Object> attributes;


    public Edge(Node sourceNode, Node destinationNode, Map<String, Object> attributes){
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        this.attributes = attributes;
    }
    public Node getSourceNode(){
        return this.sourceNode;
    }
    public Node getDestinationNode(){
        return this.destinationNode;
    }
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
    public void setAttributes(Map<String, Object> attributes){
        this.attributes = attributes;
    }
}
