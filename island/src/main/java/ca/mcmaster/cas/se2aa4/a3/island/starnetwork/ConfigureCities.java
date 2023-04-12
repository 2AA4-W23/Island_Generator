package ca.mcmaster.cas.se2aa4.a3.island.starnetwork;

import adt.Edge;
import adt.Node;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.*;

public class ConfigureCities {
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Edge> edges = new ArrayList<>();

    public ArrayList<Node> initializeCities(ArrayList<String> type, Structs.Mesh mesh){
        HashSet<Integer> visited = new HashSet<>();

        for(Structs.Polygon p: mesh.getPolygonsList()) {
            if (type.get(mesh.getPolygonsList().indexOf(p)).equals("land")) {
                for (int s : p.getSegmentIdxsList()) {
                    Node node1 = new Node(new HashMap<>(), mesh.getSegmentsList().get(s).getV1Idx());
                    Node node2 = new Node(new HashMap<>(), mesh.getSegmentsList().get(s).getV2Idx());
                    if(!visited.contains(mesh.getSegmentsList().get(s).getV1Idx())){
                        visited.add(mesh.getSegmentsList().get(s).getV1Idx());
                        nodes.add(node1);
                    }
                    if(!visited.contains(mesh.getSegmentsList().get(s).getV2Idx())){
                        visited.add(mesh.getSegmentsList().get(s).getV2Idx());
                        nodes.add(node2);
                    }
//
                }
            }
        }
            for(Structs.Polygon p: mesh.getPolygonsList()){

                for(int i = 0; i < p.getSegmentIdxsCount(); i++){
                    int v1ID = mesh.getSegments(p.getSegmentIdxs(i)).getV1Idx();
                    int v2ID = mesh.getSegments(p.getSegmentIdxs(i)).getV2Idx();
                    for(Node n: nodes){
                        if (n.getId() == v1ID) {
                            for(Node n2: nodes){
                                if (n2.getId() == v2ID){
                                    edges.add(new Edge(n,n2, new HashMap<>()));
                                    edges.add(new Edge(n2, n, new HashMap<>()));
                                }
                            }
                        }
                    }
                }

            }

        return nodes;
    }
    public ArrayList<Edge> getEdges(){
        return this.edges;
    }
    public ArrayList<Node> getNodes(){
        return this.nodes;
    }

}
