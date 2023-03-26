package ca.mcmaster.cas.se2aa4.a3.island.moisture;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Beach;

import java.util.ArrayList;

public class LandHumidity {
    private ArrayList<Double> humidity;

    public LandHumidity(Structs.Mesh mesh){
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
                            humidity.set(mesh.getPolygonsList().indexOf(i), humidity.get(mesh.getPolygonsList().indexOf(i)) + 3.0);
                        }
                        else if(distance < (minDimension * 0.04)){
                            humidity.set(mesh.getPolygonsList().indexOf(i), humidity.get(mesh.getPolygonsList().indexOf(i)) + 2.0);
                        }
                       else if(distance < (minDimension * 0.07)){
                            humidity.set(mesh.getPolygonsList().indexOf(i), humidity.get(mesh.getPolygonsList().indexOf(i)) + 1.0);
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