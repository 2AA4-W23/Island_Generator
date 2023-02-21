package ca.mcmaster.cas.se2aa4.a2.customMesh;


import java.util.ArrayList;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
abstract class MeshADT {


    public abstract void addAllVertices(int width, int height, int square);
    public abstract void addVertexColour();


    public abstract void addAllSegments();

    public abstract void addSegmentColour();

    public abstract void addAllPolygons(ArrayList<customPolygon> list);
    public abstract void addVertex(Vertex vertex);
    public abstract void addSegment(Segment segment);
    public abstract void addPolygon(customPolygon polygon);


    public abstract ArrayList<Segment> getSegments();

    public abstract ArrayList<Vertex> getVertices();
    public abstract ArrayList<customPolygon> getPolygons();




}
