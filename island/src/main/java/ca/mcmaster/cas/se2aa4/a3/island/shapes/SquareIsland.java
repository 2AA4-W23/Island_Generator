package ca.mcmaster.cas.se2aa4.a3.island.shapes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.extentionpoints.Lakes;
import ca.mcmaster.cas.se2aa4.a3.island.extentionpoints.Rivers;

import java.util.ArrayList;
import java.util.HashMap;

public class SquareIsland {


    public static HashMap<ArrayList<Structs.Polygon>,ArrayList<Structs.Segment>>  generateSquareIsland(Structs.Mesh mesh, double xcenter, double ycenter, int lakes, int rivers) {

        ArrayList<Structs.Polygon> temp = new ArrayList<>();
        ArrayList<Structs.Segment> tempSeg = new ArrayList<>();
        ArrayList<String> type = new ArrayList<>();

        HashMap<ArrayList<Structs.Polygon>,ArrayList<Structs.Segment>> values = new HashMap<>();


        for (Structs.Polygon p : mesh.getPolygonsList()) {

            double pCenterx = mesh.getVerticesList().get(p.getCentroidIdx()).getX();
            double pCentery = mesh.getVerticesList().get(p.getCentroidIdx()).getY();

            // Check if the point is within the square island
            if (Math.abs(pCenterx - xcenter) < 500.0 && Math.abs(pCentery - ycenter) < 500.0) {
                int red = 51;
                int green = 153;
                int blue = 51;
                type.add("land");
                String colorCode = red + "," + green + "," + blue;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

                temp.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());
            }
            // Otherwise, it is water
            else {
                int red = 70;
                int green = 90;
                int blue = 180;
                type.add("ocean");
                String colorCode = red + "," + green + "," + blue;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                temp.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());
            }
        }
        for (Structs.Polygon p : mesh.getPolygonsList()) {
            if (type.get(mesh.getPolygonsList().indexOf(p)).equals("land")) {
                for (int i : p.getNeighborIdxsList()) {
                    if (type.get(i).equals("ocean")) {
                        int red = 180;
                        int green = 156;
                        int blue = 70;
                        type.set(mesh.getPolygonsList().indexOf(p), "beach");
                        String colorCode = red + "," + green + "," + blue;
                        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                        temp.set(mesh.getPolygonsList().indexOf(p), Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());
                        break;
                    }
                }
            }
        }
        for(Structs.Segment s: mesh.getSegmentsList()){
            tempSeg.add(s);
        }

        if(lakes != 0) {
            temp = Lakes.generateLakes(mesh, temp, type, lakes);
        }
        if (rivers != 0){
            tempSeg = Rivers.generateRivers(mesh, temp, type, rivers, tempSeg);
        }
        values.put(temp, tempSeg);
        return values;
    }
}
