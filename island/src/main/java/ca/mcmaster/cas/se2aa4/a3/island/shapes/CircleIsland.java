package ca.mcmaster.cas.se2aa4.a3.island.shapes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.IslandGenerator;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Land;

import java.util.ArrayList;

public class CircleIsland {

    public static ArrayList<Structs.Polygon> generateCircleIsland(Structs.Mesh mesh, double xcenter, double ycenter) {

        double pCenterx = 0;
        double pCentery = 0;
        double distance = 0;

        ArrayList<Structs.Polygon> temp = new ArrayList<>();
        ArrayList<String> type = new ArrayList<>();

        for (Structs.Polygon p : mesh.getPolygonsList()) {

            pCenterx = mesh.getVerticesList().get(p.getCentroidIdx()).getX();
            pCentery = mesh.getVerticesList().get(p.getCentroidIdx()).getY();

            distance = Math.sqrt(Math.pow(pCenterx - xcenter, 2) + Math.pow(pCentery - ycenter, 2));

            //LAND (GREEN)
            if (distance < 500.0 && distance > 300) {
                type.add("land");
                Land land = new Land();
                temp.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(land.setColourCode()).build());
            }
            //LAGOON (LIGHT BLUE)
            else if (distance < 300) {
                type.add("lagoon");
                String colorCode = IslandGenerator.setColourProperties("lagoon");
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                temp.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());
            }
            //OCEAN (DARK BLUE)
            else {
                type.add("ocean");
                String colorCode = IslandGenerator.setColourProperties("ocean");
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                temp.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());

            }

        }
        for (Structs.Polygon p : mesh.getPolygonsList()) {
            if (!(type.get(mesh.getPolygonsList().indexOf(p)).equals("lagoon") || type.get(mesh.getPolygonsList().indexOf(p)).equals("ocean"))) {
                for (int i : p.getNeighborIdxsList()) {
                    if (type.get(i).equals("lagoon") || type.get(i).equals("ocean")) {
                        type.set(mesh.getPolygonsList().indexOf(p), "beach");
                        String colorCode = IslandGenerator.setColourProperties("beach");
                        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                        temp.set(mesh.getPolygonsList().indexOf(p), Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());
                        break;
                    }
                }
            }
        }
        return temp;
    }
}
