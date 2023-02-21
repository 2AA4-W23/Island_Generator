package ca.mcmaster.cas.se2aa4.a2.customMesh;


import java.util.ArrayList;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
abstract class MeshADT {


    public abstract void addAllVertices(ArrayList<customVertex> list);

    public abstract void addAllSegments(ArrayList<CustomSegment> list);

    public abstract void addAllPolygons(ArrayList<customPolygon> list);
    public abstract void addVertex(customVertex vertex);
    public abstract void addSegment(CustomSegment segment);
    public abstract void addPolygon(customPolygon polygon);


    public abstract ArrayList<CustomSegment> getSegments();

    public abstract ArrayList<customVertex> getVertices();
    public abstract ArrayList<customPolygon> getPolygons();




}
