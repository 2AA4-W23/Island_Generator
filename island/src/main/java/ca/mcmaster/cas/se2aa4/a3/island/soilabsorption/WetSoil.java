package ca.mcmaster.cas.se2aa4.a3.island.soilabsorption;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;

public class WetSoil implements Soil{
    private ArrayList<Double> humidity;

    public WetSoil(){
        this.humidity = new ArrayList<>();
    }

    public void computeHumidity(Structs.Mesh mesh, ArrayList<String> type, double minDimension){
        double xCurrentWater, yCurrentWater, xCurrentLand, yCurrentLand, distance;
        for(int s = 0; s < mesh.getPolygonsList().size(); s++){
            humidity.add(0,0.0);
        }
        for (Structs.Polygon p : mesh.getPolygonsList()) {
            if (type.get(mesh.getPolygonsList().indexOf(p)).equals("lake")) {
                xCurrentWater = mesh.getVerticesList().get(p.getCentroidIdx()).getX();
                yCurrentWater = mesh.getVerticesList().get(p.getCentroidIdx()).getY();
                for (Structs.Polygon i : mesh.getPolygonsList()) {
                    if (type.get(mesh.getPolygonsList().indexOf(i)).equals("land")) {
                        xCurrentLand = mesh.getVerticesList().get(i.getCentroidIdx()).getX();
                        yCurrentLand = mesh.getVerticesList().get(i.getCentroidIdx()).getY();

                        distance = Math.sqrt(Math.pow(xCurrentLand - xCurrentWater, 2) + Math.pow(yCurrentLand - yCurrentWater, 2));
                        if(distance < (minDimension * 0.01)){
                            humidity.set(mesh.getPolygonsList().indexOf(i), humidity.get(mesh.getPolygonsList().indexOf(i)) + 8.0);
                        }
                        else if(distance < (minDimension * 0.03)){
                            humidity.set(mesh.getPolygonsList().indexOf(i), humidity.get(mesh.getPolygonsList().indexOf(i)) + 6.0);
                        }
                        else if(distance < (minDimension * 0.05)){
                            humidity.set(mesh.getPolygonsList().indexOf(i), humidity.get(mesh.getPolygonsList().indexOf(i)) + 4.0);
                        }
                    }
                }
            }
        }
    }
    public ArrayList<Double> getHumidity(){
        return this.humidity;
    }
}