package ca.mcmaster.cas.se2aa4.a2.customMesh;

import java.util.ArrayList;

public class CustomMesh extends MeshADT{


    ArrayList<customVertex> vertexList;
    ArrayList<CustomSegment> segmentList;
    ArrayList<customPolygon> polygonList;

    public CustomMesh(){
        vertexList = new ArrayList<>();
        segmentList = new ArrayList<>();
        polygonList = new ArrayList<>();
    }

    @Override
    public void addAllVertices(ArrayList<customVertex> list) {
        this.vertexList = list;
    }

    @Override
    public void addAllSegments(ArrayList<CustomSegment> list) {
        this.segmentList = list;
    }

    @Override
    public void addAllPolygons(ArrayList<customPolygon> list) {
        this.polygonList = list;
    }

    @Override
    public void addVertex(customVertex vertex) {
        this.vertexList.add(vertex);
    }

    @Override
    public void addSegment(CustomSegment segment) {
        this.segmentList.add(segment);
    }

    @Override
    public void addPolygon(customPolygon polygon) {
        this.polygonList.add(polygon);
    }

    @Override
    public ArrayList<CustomSegment> getSegments() {
        return this.segmentList;
    }

    @Override
    public ArrayList<customVertex> getVertices() {
        return this.vertexList;
    }

    @Override
    public ArrayList<customPolygon> getPolygons() {
        return this.polygonList;
    }
}
