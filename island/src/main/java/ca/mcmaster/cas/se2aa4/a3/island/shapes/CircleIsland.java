package ca.mcmaster.cas.se2aa4.a3.island.shapes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;

public class CircleIsland {

    public static ArrayList<Structs.Polygon> generateCircleIsland(Structs.Mesh mesh) {
        double xcenter = 0;
        double ycenter = 0;

        for (Structs.Vertex v : mesh.getVerticesList()) {
            xcenter += v.getX();
            ycenter += v.getY();
        }
        xcenter = xcenter / mesh.getVerticesCount();
        ycenter = ycenter / mesh.getVerticesCount();

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
                int red = 51;
                int green = 153;
                int blue = 51;
                type.add("land");
                String colorCode = red + "," + green + "," + blue;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

                temp.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());
            }
            //LAGOON (LIGHT BLUE)
            else if (distance < 300) {
                int red = 70;
                int green = 160;
                int blue = 180;
                type.add("lagoon");
                String colorCode = red + "," + green + "," + blue;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                temp.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(color).build());
            }
            //OCEAN (DARK BLUE)
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
            if (!(type.get(mesh.getPolygonsList().indexOf(p)).equals("lagoon") || type.get(mesh.getPolygonsList().indexOf(p)).equals("ocean"))) {
                for (int i : p.getNeighborIdxsList()) {
                    if (type.get(i).equals("lagoon") || type.get(i).equals("ocean")) {
                        System.out.println(type);
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
