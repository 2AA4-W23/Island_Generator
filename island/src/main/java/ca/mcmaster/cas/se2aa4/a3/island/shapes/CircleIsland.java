package ca.mcmaster.cas.se2aa4.a3.island.shapes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.IslandGenerator;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Beach;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Lagoon;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Land;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Ocean;

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
                Lagoon lagoon = new Lagoon();
                temp.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(lagoon.setColourCode()).build());
            }
            //OCEAN (DARK BLUE)
            else {
                type.add("ocean");
                Ocean ocean = new Ocean();
                temp.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(ocean.setColourCode()).build());

            }

        }
        for (Structs.Polygon p : mesh.getPolygonsList()) {
            if (!(type.get(mesh.getPolygonsList().indexOf(p)).equals("lagoon") || type.get(mesh.getPolygonsList().indexOf(p)).equals("ocean"))) {
                for (int i : p.getNeighborIdxsList()) {
                    if (type.get(i).equals("lagoon") || type.get(i).equals("ocean")) {
                        type.set(mesh.getPolygonsList().indexOf(p), "beach");
                        Beach beach = new Beach();
                        temp.set(mesh.getPolygonsList().indexOf(p), Structs.Polygon.newBuilder(p).clearProperties().addProperties(beach.setColourCode()).build());
                        break;
                    }
                }
            }
        }
        return temp;
    }
}
