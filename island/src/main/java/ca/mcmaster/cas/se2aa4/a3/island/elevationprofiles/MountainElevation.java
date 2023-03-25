package ca.mcmaster.cas.se2aa4.a3.island.elevationprofiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Beach;

import java.util.ArrayList;

public class MountainElevation {

    private ArrayList<Double> elevations ;

    public MountainElevation(){
        this.elevations = new ArrayList<>();
    }

    public void computeElevations(Structs.Mesh mesh, ArrayList<String> type, double xcenter, double ycenter, double minDimension){
        double xCurrent, yCurrent, distance;
        int count = 0;
        for (Structs.Polygon p : mesh.getPolygonsList()) {
            if (!(type.get(mesh.getPolygonsList().indexOf(p)).equals("ocean"))) {
                xCurrent = mesh.getVerticesList().get(p.getCentroidIdx()).getX();
                yCurrent = mesh.getVerticesList().get(p.getCentroidIdx()).getY();

                distance = Math.sqrt(Math.pow(xCurrent - xcenter, 2) + Math.pow(yCurrent - ycenter, 2));

                if(distance < (minDimension * 0.03)){
                    elevations.set(count, 100.0);
                }
                else if(distance < (minDimension * 0.09)){
                    elevations.set(count, 90.0);
                }
                else if(distance < (minDimension * 0.15)){
                    elevations.set(count, 80.0);
                }
                else if(distance < (minDimension * 0.23)){
                    elevations.set(count, 70.0);
                }
                else if(distance < (minDimension * 0.30)){
                    elevations.set(count, 60.0);
                }


            }
            else{
                elevations.set(count, 0.0);
            }
            count++;
        }
    }
    public ArrayList<Double> getElevations(){
        return this.elevations;
    }

}
