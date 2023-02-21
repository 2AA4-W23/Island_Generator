package ca.mcmaster.cas.se2aa4.a2.customMesh;

import ca.mcmaster.cas.se2aa4.a2.customMesh.customVertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;

import java.util.List;

public class customPolygon {

    private Polygon polygon;

    public customPolygon(){
        this.polygon = Polygon.newBuilder().build();
    }

    public int getCentroidIDx(){
        return this.polygon.getCentroidIdx();
    }

    public List<Integer> getNeighbourIDxsList(){
        return this.polygon.getNeighborIdxsList();
    }

    public int getNeighbourIDxs(int index){
        return this.polygon.getNeighborIdxs(index);
    }

    public int getNeighbourIDxsCount(){
        return this.polygon.getNeighborIdxsCount();
    }

    public List<Integer> getSegmentIDxsList(){
        return this.polygon.getSegmentIdxsList();
    }

    public int getSegmentIDxs(int index){
        return this.polygon.getSegmentIdxs(index);
    }

    public int getSegmentIDxsCount(){
        return this.polygon.getSegmentIdxsCount();
    }


}
