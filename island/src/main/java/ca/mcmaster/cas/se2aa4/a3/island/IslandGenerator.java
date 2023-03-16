package ca.mcmaster.cas.se2aa4.a3.island;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a3.island.shapes.CircleIsland;
import ca.mcmaster.cas.se2aa4.a3.island.shapes.SquareIsland;

import java.util.ArrayList;


public class IslandGenerator {

    public Mesh generateIsland(Mesh mesh, String shape){
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
            return(finalizeMesh(mesh, CircleIsland.generateCircleIsland(mesh, xcenter, ycenter)));
        }
        else{
            return(finalizeMesh(mesh, SquareIsland.generateSquareIsland(mesh, xcenter, ycenter)));
        }
    }
    public static String setColourProperties(String type){
        int red = 0, green = 0, blue = 0;
        if(type.equals("land")){
            red = 51;
            green = 153;
            blue = 51;
        }
        else if(type.equals("lagoon")){
            red = 70;
            green = 160;
            blue = 180;
        }
        else if(type.equals("ocean")){
            red = 70;
            green = 90;
            blue = 180;
        }
        else if(type.equals("beach")){
            red = 180;
            green = 156;
            blue = 70;
        }
        String colorCode = red + "," + green + "," + blue;
        return colorCode;
    }
    public Mesh finalizeMesh(Mesh tempMesh, ArrayList<Polygon> temp) {
        return Mesh.newBuilder().addAllVertices(tempMesh.getVerticesList()).addAllSegments(tempMesh.getSegmentsList()).addAllPolygons(temp).build();
    }
}
