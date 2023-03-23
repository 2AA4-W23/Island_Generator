package ca.mcmaster.cas.se2aa4.a3.island.shapes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.extentionpoints.Lakes;
import ca.mcmaster.cas.se2aa4.a3.island.extentionpoints.Rivers;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Beach;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Land;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Ocean;

import java.util.ArrayList;
import java.util.HashMap;

public class SquareIsland {
    private ArrayList<Structs.Polygon> temp;
    private ArrayList<String> type;
    private ArrayList<Structs.Segment> tempSeg;

    public SquareIsland(){
        this.temp = new ArrayList<>();
        this.type = new ArrayList<>();
        this.tempSeg = new ArrayList<>();
    }


    public void  generateSquareIsland(Structs.Mesh mesh, double xcenter, double ycenter, int numLakes, int numRivers, double minDimension) {

        for (Structs.Polygon p : mesh.getPolygonsList()) {

            double pCenterx = mesh.getVerticesList().get(p.getCentroidIdx()).getX();
            double pCentery = mesh.getVerticesList().get(p.getCentroidIdx()).getY();

            // Check if the point is within the square island
            if (Math.abs(pCenterx - xcenter) < (minDimension * 0.38) && Math.abs(pCentery - ycenter) < (minDimension * 0.38)) {
                type.add("land");
                Land land = new Land();
                temp.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(land.setColourCode()).build());
            }
            // Otherwise, it is water
            else {
                type.add("ocean");
                Ocean ocean = new Ocean();
                temp.add(Structs.Polygon.newBuilder(p).clearProperties().addProperties(ocean.setColourCode()).build());
            }
        }
        for (Structs.Polygon p : mesh.getPolygonsList()) {
            if (type.get(mesh.getPolygonsList().indexOf(p)).equals("land")) {
                for (int i : p.getNeighborIdxsList()) {
                    if (type.get(i).equals("ocean")) {
                        type.set(mesh.getPolygonsList().indexOf(p), "beach");
                        Beach beach = new Beach();
                        temp.set(mesh.getPolygonsList().indexOf(p), Structs.Polygon.newBuilder(p).clearProperties().addProperties(beach.setColourCode()).build());
                        break;
                    }
                }
            }
        }
        for(Structs.Segment s: mesh.getSegmentsList()){
            tempSeg.add(s);
        }

        if(numLakes != 0) {
            Lakes lakes = new Lakes(temp, type, numLakes);
            lakes.generateLakes(mesh);
            temp = lakes.getTempMeshProperties();
        }
        if (numRivers != 0){
            Rivers rivers = new Rivers(temp,type,numRivers, tempSeg);
            rivers.generateRivers(mesh);
            temp = rivers.getTempMeshProperties();
            tempSeg = rivers.getTempSeg();
            type = rivers.getType();
        }
    }
    public ArrayList<Structs.Polygon> getTempMeshProperties(){
        return this.temp;
    }
    public ArrayList<String> getType(){
        return this.type;
    }
    public ArrayList<Structs.Segment> getTempSeg(){
        return this.tempSeg;
    }
}
