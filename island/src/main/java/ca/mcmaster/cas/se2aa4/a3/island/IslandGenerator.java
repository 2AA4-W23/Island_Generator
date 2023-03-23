package ca.mcmaster.cas.se2aa4.a3.island;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a3.island.shapes.CircleIsland;
import ca.mcmaster.cas.se2aa4.a3.island.shapes.SquareIsland;

import java.util.ArrayList;
import java.util.HashMap;


public class IslandGenerator {

    public Mesh generateIsland(Mesh mesh, String shape, boolean lagoon, int lakes, int rivers){
        Mesh tempMesh = mesh;
        double xcenter = 0;
        double ycenter = 0;

        for (Structs.Vertex v : mesh.getVerticesList()) {
            xcenter += v.getX();
            ycenter += v.getY();
        }
        xcenter = xcenter / mesh.getVerticesCount();
        ycenter = ycenter / mesh.getVerticesCount();

        if(shape.equals("Circle") || shape.equals("circle")){
            CircleIsland circleIsland = new CircleIsland();
            circleIsland.generateCircleIsland(mesh, xcenter, ycenter, lagoon, lakes, rivers);
            return(finalizeMesh(mesh, circleIsland.getTempMeshProperties(), circleIsland.getTempSeg()));
        }
        else{
            SquareIsland squareIsland = new SquareIsland();
            squareIsland.generateSquareIsland(mesh, xcenter, ycenter, lakes, rivers);
            return(finalizeMesh(mesh, squareIsland.getTempMeshProperties(), squareIsland.getTempSeg()));
        }
    }
    public Mesh finalizeMesh(Mesh tempMesh, ArrayList<Polygon> temp, ArrayList<Segment> tempSeg) {
        System.out.println(tempSeg);
        System.out.println("______________");
        System.out.println(temp);
        return Mesh.newBuilder().addAllVertices(tempMesh.getVerticesList()).addAllSegments(tempSeg).addAllPolygons(temp).build();
    }
}
