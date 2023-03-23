package ca.mcmaster.cas.se2aa4.a3.island.extentionpoints;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Lake;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.River;

import java.util.ArrayList;

public class Rivers {
    public static ArrayList<Structs.Segment> generateRivers(Structs.Mesh mesh, ArrayList<Structs.Polygon> temp, ArrayList<String> type, int numRivers, ArrayList<Structs.Segment> tempSeg){
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

            Structs.Segment s = mesh.getSegmentsList().get(p.getNeighborIdxsList().get( (int) (Math.random() * p.getNeighborIdxsCount())));

            River river = new River();

           // tempSeg.set(mesh.getSegmentsList().indexOf(s), Structs.Segment.newBuilder(s).clearProperties().addProperties(river.setColourCode()).build());

//            int riverSize = (int) (Math.random() * p.getNeighborIdxsCount());
//            int riverCounter = 0;
//            do {
//                for (int i : p.getNeighborIdxsList()) {
//                    if (type.get(i).equals("land")) {
//                        River riverN = new River();
//                        tempSeg.set(i, Structs.Segment.newBuilder(s).clearProperties().addProperties(riverN.setColourCode()).build());
//                    }
//                    riverCounter++;
//                }
//            } while (riverCounter < riverSize);
        }
        River newRiver = new River();
        for(Structs.Segment s: tempSeg){
            tempSeg.set(mesh.getSegmentsList().indexOf(s), Structs.Segment.newBuilder(s).clearProperties().addProperties(newRiver.setColourCode()).build());
        }
        return tempSeg;
    }
}
