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
            int seg = 0;
            for(int i = 0; i < p.getNeighborIdxsCount(); i++){
                 seg = (int) (Math.random() * p.getNeighborIdxsCount());

                if(mesh.getPolygonsList().get(p.getNeighborIdxsList().get(i)).getSegmentIdxsList().contains(p.getSegmentIdxs(seg))){
                            if(type.get(p.getNeighborIdxsList().get(i)).equals("land")){
                                break;
                            }
                }
            }
            Structs.Segment s = mesh.getSegmentsList().get(p.getSegmentIdxs(seg));

            River river = new River();
            tempSeg.set(mesh.getSegmentsList().indexOf(s), Structs.Segment.newBuilder(s).clearProperties().addProperties(river.setColourCode()).build());

       //     int riverSize = (int) (Math.random() * 14);

            Boolean land = true;
       //     int riverCounter = 0;
           Structs.Segment currentSegment = s;
           Structs.Polygon currentPolygon = p;
           int currentSegmentValue = 0;
            int counter = 0;
            System.out.println("hi");
            do{
                for(int t: p.getNeighborIdxsList()) {
                    System.out.println(t);
                    if (mesh.getPolygonsList().get(t).getSegmentIdxsList().contains(mesh.getSegmentsList().indexOf(currentSegment))) {
                        System.out.println("HELOOOOO");
                        currentPolygon = mesh.getPolygonsList().get(t);

                    }
                }
                for(int c = 0; c < currentPolygon.getSegmentIdxsCount(); c++){
                    if(currentPolygon.getSegmentIdxsList().get(c) == mesh.getSegmentsList().indexOf(currentSegment)){
                        System.out.println("YOLO");
                        currentSegmentValue = c;
                    }
                }
                for(int i = 0; i < currentPolygon.getSegmentIdxsCount(); i++) {
                    if (i != currentSegmentValue) {
                        if (Integer.valueOf(currentSegment.getV2Idx()).equals(Integer.valueOf(mesh.getSegmentsList().get(currentPolygon.getSegmentIdxsList().get(i)).getV1Idx())) || Integer.valueOf(currentSegment.getV2Idx()).equals(Integer.valueOf(mesh.getSegmentsList().get(currentPolygon.getSegmentIdxsList().get(i)).getV2Idx()))) {
                            System.out.println("HEHE");
                            Structs.Segment segment = mesh.getSegmentsList().get(currentPolygon.getSegmentIdxsList().get(i));
                            for (int f = 0; f < currentPolygon.getNeighborIdxsCount(); f++) {
                                if (mesh.getPolygonsList().get(currentPolygon.getNeighborIdxsList().get(f)).getSegmentIdxsList().contains(segment)) {
                                    System.out.println("HOHOHOHOH");
                                    if (type.get(currentPolygon.getNeighborIdxsList().get(f)).equals("land")) {
                                        tempSeg.set(mesh.getSegmentsList().indexOf(segment), Structs.Segment.newBuilder(segment).clearProperties().addProperties(river.setColourCode()).build());
                                        currentSegment = segment;
                                    } else {
                                        land = false;
                                    }
                                }
                            }
                        }
                    }
                }

            }while(land);
//            do {
//                for (Structs.Segment segment : mesh.getSegmentsList()) {
//                    if (currentSegment.getV2Idx() == segment.getV1Idx() && land == true) {
//
//                        tempSeg.set(mesh.getSegmentsList().indexOf(segment), Structs.Segment.newBuilder(segment).clearProperties().addProperties(river.setColourCode()).build());
//                        currentSegment = segment;
//                        for(Structs.Polygon t: mesh.getPolygonsList() ){
//                            if(t.getSegmentIdxsList().contains(mesh.getSegmentsList().indexOf(currentSegment))){
//                                System.out.println(type.get(mesh.getPolygonsList().indexOf(t)));
//                                if(type.get(mesh.getPolygonsList().indexOf(t)).equals("ocean") || type.get(mesh.getPolygonsList().indexOf(t)).equals("lake") ||type.get(mesh.getPolygonsList().indexOf(t)).equals("lagoon") ){
//                                    System.out.println("hi");
//                                    land = false;
//                                }
//                            }
//                        }
//                    }
//
//                }
//
//                counter++;
//            }while(counter < 1000);
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
