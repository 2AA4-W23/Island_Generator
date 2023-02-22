package ca.mcmaster.cas.se2aa4.a2.customMesh;


import java.util.ArrayList;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
abstract class MeshADT {


    public abstract void createVertices(int width, int height, int square);
    public abstract void addVertexColour();

    public abstract void addAllVertices(ArrayList<Vertex> vertices);


    public abstract void createSegments();

    public abstract void addSegmentColour();
    public abstract void addAllSegments(ArrayList<Segment> segments);

    public abstract void createPolygons();
    public abstract void addAllPolygons(ArrayList<Polygon> list);
    public abstract void addVertex(Vertex vertex);
    public abstract void addSegment(Segment segment);
    public abstract void addPolygon(Polygon polygon);


    public abstract ArrayList<Segment> getSegments();

    public abstract ArrayList<Vertex> getVertices();
    public abstract ArrayList<Polygon> getPolygons();

    public abstract Mesh finalizeMesh();




}
