package ca.mcmaster.cas.se2aa4.a3.island.starnetwork;

import adt.Edge;
import adt.Graph;
import adt.Node;
import adt.Pathfinder;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.City;
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
   private int numCities;

    public Cities(ArrayList<Structs.Vertex> tempVertex, ArrayList<Structs.Segment> tempSeg, int numCities){
        this.tempVertex = tempVertex;
        this.tempSeg = tempSeg;
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.graph = new Graph();
        this.paths = new ArrayList<>();
        this.numCities = numCities;
    }
    public ArrayList<Structs.Vertex> generateCities(ArrayList<String> type, Structs.Mesh mesh, long seed){
        ConfigureCities an = new ConfigureCities();
        Random random = new Random(seed);
        HashMap<Integer, String> cityType = new HashMap<>();
        cityType.put(0,"Hamlet");
        cityType.put(1, "Town");
        cityType.put(2, "City");

        this.nodes = an.initializeCities(type,mesh);
        this.edges = an.getEdges();
        for(int i = 0; i < numCities; i++) {
            int rand = random.nextInt(nodes.size());

            int currentCityID = nodes.get(rand).getId();
            int citySize = random.nextInt(3);
            nodes.get(rand).setAttributes(Collections.singletonMap("City",cityType.get(citySize)));
            setCity(currentCityID, cityType.get(citySize));
        }
        setGraph();
        boolean captialFound = false;
        int capital;
        // choose a random node that is a city to be the capital city, which will be the source of the shortest paths
        do {
            capital = random.nextInt(nodes.size());
            if(nodes.get(capital).getAttributes().containsKey("City")) {
                captialFound = true;
            }

        }while(!captialFound);

        paths(capital);
        return tempVertex;
    }
    private void setCity(int id, String cityType){
        City cityTile = new City();
        Structs.Property city = Structs.Property.newBuilder().setKey("city").setValue(cityType).build();
        tempVertex.set(id, Structs.Vertex.newBuilder(tempVertex.get(id)).addProperties(cityTile.setColourCode()).addProperties(city).build());
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
    }
    private void paths(int capital) {
        Pathfinder p = new Pathfinder();
        Node current, next;
        int pathlength = 0;
        for (Node n : graph.getNodes()) {
            if(!n.equals(nodes.get(capital)) && n.getAttributes().containsKey("City")) {
                LinkedList<Node> shortestPath = (LinkedList<Node>) p.findShortestPath(graph,nodes.get(capital),n);
                paths.add(shortestPath);
                pathlength = shortestPath.size();
                current = shortestPath.removeFirst();
                next = shortestPath.removeFirst();
                int counter = 0;
                while(counter < pathlength) {
                    for (Structs.Segment s : tempSeg) {
                        if ((Integer.valueOf(s.getV1Idx()).equals(Integer.valueOf(current.getId())) && (Integer.valueOf(s.getV2Idx()).equals(Integer.valueOf(next.getId())))) || (((Integer.valueOf(s.getV1Idx()).equals(Integer.valueOf(next.getId())))) && (Integer.valueOf(s.getV2Idx()).equals(Integer.valueOf(current.getId()))))) {
                            Road road = new Road();
                            tempSeg.set(tempSeg.indexOf(s), Structs.Segment.newBuilder(s).clearProperties().addProperties(road.setColourCode()).build());
                            Node temp = next;
                            current = temp;
                            if (!shortestPath.isEmpty()) {
                                next = shortestPath.removeFirst();
                                break;
                            }
                        }
                    }
                    counter++;
                }

            }
        }
    }
    public ArrayList<Structs.Segment> getTempSeg(){
        return this.tempSeg;
    }
    public ArrayList<LinkedList<Node>> getPaths(){
        return this.paths;
    }
    public Graph getGraph(){
        return this.graph;
    }

}
