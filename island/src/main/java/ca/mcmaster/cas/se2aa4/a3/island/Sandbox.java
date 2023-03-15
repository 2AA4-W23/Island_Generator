package ca.mcmaster.cas.se2aa4.a3.island;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.Ellipse2D;



public class Sandbox {
//
    public Mesh generateIsland(Mesh mesh){
        System.out.println("hellooooo");
        double xcenter = 0;
        double ycenter = 0;

        List vertices = new List();

        Mesh tempMesh = mesh;

        for(Vertex v: mesh.getVerticesList()){
            xcenter += v.getX();
            ycenter += v.getY();
        }
        xcenter = xcenter / mesh.getVerticesCount();
        ycenter = ycenter / mesh.getVerticesCount();

        double pCenterx = 0;
        double pCentery = 0;
        double distance = 0;

        ArrayList<Polygon> temp = new ArrayList<>();

        for(Polygon p: mesh.getPolygonsList()){
           pCenterx = mesh.getVerticesList().get(p.getCentroidIdx()).getX();
           pCentery = mesh.getVerticesList().get(p.getCentroidIdx()).getY();

           distance = Math.sqrt(Math.pow(pCenterx - xcenter, 2) + Math.pow(pCentery - ycenter, 2));

           if(distance < 250.0){
               int red = 51;
               int green = 153;
               int blue = 51;
               String colorCode = red + "," + green + "," + blue;
               Structs.Property color = Structs.Property.newBuilder().setKey("rgba_color").setValue(colorCode).build();
               temp.add(Polygon.newBuilder(p).addProperties(color).build());
           }
           else{
               int red = 70;
               int green = 130;
               int blue = 180;
               String colorCode = red + "," + green + "," + blue;
               Structs.Property color = Structs.Property.newBuilder().setKey("rgba_color").setValue(colorCode).build();
               temp.add(Polygon.newBuilder(p).addProperties(color).build());
           }

        }
       return finalizeMesh(tempMesh, temp);
    }
    public Mesh finalizeMesh(Mesh tempMesh, ArrayList<Polygon> temp) {
        return Mesh.newBuilder().addAllVertices(tempMesh.getVerticesList()).addAllSegments(tempMesh.getSegmentsList()).addAllPolygons(temp).build();
    }



}
