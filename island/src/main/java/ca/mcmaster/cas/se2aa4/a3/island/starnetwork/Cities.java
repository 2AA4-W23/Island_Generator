package ca.mcmaster.cas.se2aa4.a3.island.starnetwork;

import adt.Edge;
import adt.Graph;
import adt.Node;
import adt.Pathfinder;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Desert;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Lake;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Road;

import java.util.*;

public class Cities {
    private ArrayList<Structs.Vertex> tempVertex;
    private ArrayList<Structs.Segment> tempSeg;
    private ArrayList<Node> nodes;
   private ArrayList<Edge> edges;
   private Graph graph;
   private ArrayList<LinkedList<Node>> paths;

    public Cities(ArrayList<Structs.Vertex> tempVertex, ArrayList<Structs.Segment> tempSeg){
        this.tempVertex = tempVertex;
        this.tempSeg = tempSeg;
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.graph = new Graph();
        this.paths = new ArrayList<>();
    }
    public ArrayList<Structs.Vertex> generateCities(ArrayList<String> type, Structs.Mesh mesh, ArrayList<Structs.Vertex> vertices){
        ConfigureCities an = new ConfigureCities();
        Random random = new Random();
        HashMap<Integer, String> cityType = new HashMap<>();
        cityType.put(0,"Hamlet");
        cityType.put(1, "Town");
        cityType.put(2, "City");

        this.nodes = an.initializeCities(type,mesh,vertices);
        this.edges = an.getEdges();
        for(int i = 0; i < 5; i++) {
            int rand = random.nextInt(nodes.size());

            int currentCityID = nodes.get(rand).getId();
            int citySize = random.nextInt(3);
            nodes.get(rand).setAttributes(Collections.singletonMap("City",cityType.get(citySize)));
            setCity(currentCityID, cityType.get(citySize));
        }
        setGraph();
        boolean captialFound = false;
        int capital;
        do {
            capital = random.nextInt(nodes.size());
            if(nodes.get(capital).getAttributes().containsKey("City")) {
                captialFound = true;
            }

        }while(!captialFound);

        return tempVertex;
    }
    private void setCity(int id, String cityType){
        Road road = new Road();
        Structs.Property city = Structs.Property.newBuilder().setKey("city").setValue(cityType).build();
        tempVertex.set(id, Structs.Vertex.newBuilder(tempVertex.get(id)).addProperties(road.setColourCode()).addProperties(city).build());
    }
    private void setGraph(){
        for(Node n: nodes){
            if(!graph.getNodes().contains(n)) {
                graph.addNode(n);
            }
        }
        for(Edge e: edges){
            graph.addEdge(e);
        }
        for(Node n: graph.getNodes()){
           // System.out.println(graph.getEdges(n) + " sighhhhhhhhhhhhhhhh");
        }
    }

    public ArrayList<Structs.Segment> getTempSeg(){
        return this.tempSeg;
    }

}
