package adt;

import java.util.Map;

public class Node {

    private Map<String, Object> attributes;
    private int id;

    public Node(Map<String, Object> attributes, int id){
        this.attributes = attributes;
        this.id = id;
    }
    public Map<String, Object> getAttributes(){
        return this.attributes;
    }
    public int getId(){
        return this.id;
    }

    public void setAttributes(Map<String, Object> attributes){
        this.attributes = attributes;
    }

}
