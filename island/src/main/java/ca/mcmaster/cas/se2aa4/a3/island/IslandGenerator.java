package ca.mcmaster.cas.se2aa4.a3.island;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a3.island.shapes.CircleIsland;

import java.util.ArrayList;


public class IslandGenerator {

    public Mesh generateIsland(Mesh mesh, String shape){
        Mesh tempMesh = mesh;


        if(shape.equals("Circle") || shape.equals("circle")){
            return(finalizeMesh(mesh, CircleIsland.generateCircleIsland(mesh)));
        }
        else{
            return generateSquareIsland(mesh);
        }
    }
//
//    public Mesh generateCircleIsland(Mesh mesh) {
//        System.out.println("hellooooo");
//        double xcenter = 0;
//        double ycenter = 0;
//
//
//        Mesh tempMesh = mesh;
//
//        for (Vertex v : mesh.getVerticesList()) {
//            xcenter += v.getX();
//            ycenter += v.getY();
//        }
//        xcenter = xcenter / mesh.getVerticesCount();
//        ycenter = ycenter / mesh.getVerticesCount();
//
//        double pCenterx = 0;
//        double pCentery = 0;
//        double distance = 0;
//
//        ArrayList<Polygon> temp = new ArrayList<>();
//        ArrayList<String> type = new ArrayList<>();
//
//        for (Polygon p : mesh.getPolygonsList()) {
//
//            pCenterx = mesh.getVerticesList().get(p.getCentroidIdx()).getX();
//            pCentery = mesh.getVerticesList().get(p.getCentroidIdx()).getY();
//
//            distance = Math.sqrt(Math.pow(pCenterx - xcenter, 2) + Math.pow(pCentery - ycenter, 2));
//
//            //LAND (GREEN)
//            if (distance < 500.0 && distance > 300) {
//                int red = 51;
//                int green = 153;
//                int blue = 51;
//                type.add("land");
//                String colorCode = red + "," + green + "," + blue;
//                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
//
//                temp.add(Polygon.newBuilder(p).clearProperties().addProperties(color).build());
//            }
//            //LAGOON (LIGHT BLUE)
//            else if (distance < 300) {
//                int red = 70;
//                int green = 160;
//                int blue = 180;
//                type.add("lagoon");
//                String colorCode = red + "," + green + "," + blue;
//                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
//                temp.add(Polygon.newBuilder(p).clearProperties().addProperties(color).build());
//            }
//            //OCEAN (DARK BLUE)
//            else {
//                int red = 70;
//                int green = 90;
//                int blue = 180;
//                type.add("ocean");
//                String colorCode = red + "," + green + "," + blue;
//                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
//                temp.add(Polygon.newBuilder(p).clearProperties().addProperties(color).build());
//
//            }
//
//        }
//        for (Polygon p : mesh.getPolygonsList()) {
//            if (!(type.get(mesh.getPolygonsList().indexOf(p)).equals("lagoon") || type.get(mesh.getPolygonsList().indexOf(p)).equals("ocean"))) {
//                for (int i : p.getNeighborIdxsList()) {
//                    if (type.get(i).equals("lagoon") || type.get(i).equals("ocean")) {
//                        System.out.println(type);
//                        int red = 180;
//                        int green = 156;
//                        int blue = 70;
//                        type.set(mesh.getPolygonsList().indexOf(p), "beach");
//                        String colorCode = red + "," + green + "," + blue;
//                        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
//                        temp.set(mesh.getPolygonsList().indexOf(p), Polygon.newBuilder(p).clearProperties().addProperties(color).build());
//                        break;
//                    }
//                }
//            }
//        }
//        return finalizeMesh(tempMesh, temp);
//    }





public Mesh generateSquareIsland(Mesh mesh){
    System.out.println("hellooooo");
    double xcenter = 0;
    double ycenter = 0;

    Mesh tempMesh = mesh;

    for(Vertex v: mesh.getVerticesList()){
        xcenter += v.getX();
        ycenter += v.getY();
    }
    xcenter = xcenter / mesh.getVerticesCount();
    ycenter = ycenter / mesh.getVerticesCount();

    ArrayList<Polygon> temp = new ArrayList<>();
    ArrayList<String> type = new ArrayList<>();

    for(Polygon p: mesh.getPolygonsList()){

        double pCenterx = mesh.getVerticesList().get(p.getCentroidIdx()).getX();
        double pCentery = mesh.getVerticesList().get(p.getCentroidIdx()).getY();

        // Check if the point is within the square island
        if(Math.abs(pCenterx - xcenter) < 500.0 && Math.abs(pCentery - ycenter) < 500.0){
            int red = 51;
            int green = 153;
            int blue = 51;
            type.add("land");
            String colorCode = red + "," + green + "," + blue;
            Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

            temp.add(Polygon.newBuilder(p).clearProperties().addProperties(color).build());
        }
        // Otherwise, it is water
        else{
            int red = 70;
            int green = 90;
            int blue = 180;
            type.add("ocean");
            String colorCode = red + "," + green + "," + blue;
            Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            temp.add(Polygon.newBuilder(p).clearProperties().addProperties(color).build());
        }
    }
    for(Polygon p: mesh.getPolygonsList()){
        if(type.get(mesh.getPolygonsList().indexOf(p)).equals("land")){
            for(int i: p.getNeighborIdxsList()){
                if(type.get(i).equals("ocean")){
                    int red = 180;
                    int green = 156;
                    int blue = 70;
                    type.set(mesh.getPolygonsList().indexOf(p), "beach");
                    String colorCode = red + "," + green + "," + blue;
                    Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                    temp.set(mesh.getPolygonsList().indexOf(p), Polygon.newBuilder(p).clearProperties().addProperties(color).build());
                    break;
                }
            }
        }
    }

        return finalizeMesh(tempMesh, temp);
    }
    public Mesh finalizeMesh(Mesh tempMesh, ArrayList<Polygon> temp) {
        return Mesh.newBuilder().addAllVertices(tempMesh.getVerticesList()).addAllSegments(tempMesh.getSegmentsList()).addAllPolygons(temp).build();
    }
}
