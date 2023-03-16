package ca.mcmaster.cas.se2aa4.a3.island.shapes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;

public class SquareIsland {


    public static ArrayList<Structs.Polygon> generateSquareIsland(Structs.Mesh mesh, double xcenter, double ycenter) {

        ArrayList<Structs.Polygon> temp = new ArrayList<>();
        ArrayList<String> type = new ArrayList<>();

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

        return temp;
    }
}
