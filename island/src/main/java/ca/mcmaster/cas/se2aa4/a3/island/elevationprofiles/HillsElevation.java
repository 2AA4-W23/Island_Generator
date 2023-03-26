package ca.mcmaster.cas.se2aa4.a3.island.elevationprofiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Lake;

import java.util.ArrayList;

public class HillsElevation implements Elevations{
    private ArrayList<Double> elevations;

    public HillsElevation(){
        this.elevations = new ArrayList<>();
    }

    public void computeElevations(Structs.Mesh mesh, ArrayList<String> type, double xcenter, double ycenter, double minDimension){
        double xCurrentHillPeak, yCurrentHillPeak, xCurrentHillLand, yCurrentHillLand, distance;

        ArrayList<Structs.Polygon> visited = new ArrayList<>();

        for(int s = 0; s < mesh.getPolygonsList().size(); s++){
            elevations.add(0,0.0);
        }

        int numHills = (int)( 5 + Math.random() * (12-5));

        for(int l = 0; l < numHills; l++) {
            String tileType;
            int rand = 0;
            for(int r = 0; r < mesh.getPolygonsList().size(); r++) {
                rand = (int) (Math.random() * mesh.getPolygonsList().size());
                tileType = type.get(rand);
                if (tileType.equals("land")) {
                    break;
                }
            }
                Structs.Polygon p = mesh.getPolygonsList().get(rand);
                xCurrentHillPeak = mesh.getVerticesList().get(p.getCentroidIdx()).getX();
                yCurrentHillPeak = mesh.getVerticesList().get(p.getCentroidIdx()).getY();
                elevations.set(mesh.getPolygonsList().indexOf(p), 30 + Math.random() * (60-30));
                visited.add(p);

                int hillSize = (int) (Math.random() * p.getNeighborIdxsCount());
                int hillCounter = 0;

                do {
                    for (int i : p.getNeighborIdxsList()) {
                        if (type.get(i).equals("land")) {
                            xCurrentHillLand = mesh.getVerticesList().get(mesh.getPolygonsList().get(i).getCentroidIdx()).getX();
                            yCurrentHillLand = mesh.getVerticesList().get(mesh.getPolygonsList().get(i).getCentroidIdx()).getY();
                            distance = Math.sqrt(Math.pow(xCurrentHillLand - xCurrentHillPeak, 2) + Math.pow(yCurrentHillLand - yCurrentHillPeak, 2));

                            if(distance < (minDimension * 0.04)) {
                                elevations.set(i, elevations.get(mesh.getPolygonsList().indexOf(p)) * 0.7);
                            }
                           else if(distance < (minDimension * 0.08)) {
                                elevations.set(i, elevations.get(mesh.getPolygonsList().indexOf(p)) * 0.4);
                            }
                           else if(distance < (minDimension * 0.16)) {
                                elevations.set(i, elevations.get(mesh.getPolygonsList().indexOf(p)) * 0.2);
                            }
                           else if(distance < (minDimension * 0.28)) {
                                elevations.set(i, elevations.get(mesh.getPolygonsList().indexOf(p)) * 0.1);
                            }
                           visited.add(mesh.getPolygonsList().get(i));
                        }
                        }
                        hillCounter++;
                    }while (hillCounter < hillSize);
                }
            }

    public ArrayList<Double> getElevations(){
        return this.elevations;
    }
}
