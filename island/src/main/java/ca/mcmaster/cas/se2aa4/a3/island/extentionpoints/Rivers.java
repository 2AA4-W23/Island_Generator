package ca.mcmaster.cas.se2aa4.a3.island.extentionpoints;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Lake;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.River;

import java.util.ArrayList;

public class Rivers {
    private ArrayList<Structs.Polygon> temp;
    private ArrayList<String> type;
    private int numRivers;
    private ArrayList<Structs.Segment> tempSeg;

    public Rivers(ArrayList<Structs.Polygon> temp, ArrayList<String> type, int numRivers, ArrayList<Structs.Segment> tempSeg){
        this.temp = temp;
        this.type = type;
        this.numRivers = numRivers;
        this.tempSeg = tempSeg;
    }
    public ArrayList<Structs.Segment> generateRivers(Structs.Mesh mesh){
        for(int l = 0; l < numRivers; l++) {

            String tileType;

            int rand = 0;
            for(int r = 0; r < temp.size(); r++){
                rand = (int) (Math.random() * temp.size());
                tileType = type.get(rand);
                if(tileType.equals("land")){
                    break;
                }
            }

            Structs.Polygon p = mesh.getPolygonsList().get(rand);
            int seg = (int) (Math.random() * p.getNeighborIdxsCount());
            Structs.Segment s = mesh.getSegmentsList().get(p.getSegmentIdxs(seg));

            River river = new River();
            tempSeg.set(mesh.getSegmentsList().indexOf(s), Structs.Segment.newBuilder(s).clearProperties().addProperties(river.setColourCode()).build());

       //     int riverSize = (int) (Math.random() * 14);

            Boolean land = true;
       //     int riverCounter = 0;
           Structs.Segment currentSegment = s;
            int counter = 0;
            do {
                for (Structs.Segment segment : mesh.getSegmentsList()) {
                    if (currentSegment.getV2Idx() == segment.getV1Idx() && land == true) {
                        tempSeg.set(mesh.getSegmentsList().indexOf(segment), Structs.Segment.newBuilder(segment).clearProperties().addProperties(river.setColourCode()).build());
                        currentSegment = segment;
                        for(Structs.Polygon t: mesh.getPolygonsList() ){
                            if(t.getSegmentIdxsList().contains(mesh.getSegmentsList().indexOf(currentSegment))){
                                System.out.println(type.get(mesh.getPolygonsList().indexOf(t)));
                                if(type.get(mesh.getPolygonsList().indexOf(t)).equals("ocean") || type.get(mesh.getPolygonsList().indexOf(t)).equals("lake") ||type.get(mesh.getPolygonsList().indexOf(t)).equals("lagoon") ){
                                    System.out.println("hi");
                                    land = false;
                                }
                            }
                        }
                    }

                }

                counter++;
            }while(counter < 100);
        }
        return tempSeg;
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
