package ca.mcmaster.cas.se2aa4.a2.customMesh;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import org.locationtech.jts.*;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class CustomIrregularMesh {

    private ArrayList<Vertex> vertexList;
    private ArrayList<Segment> segmentList;
    private ArrayList<Polygon> polygonList;

    private Mesh finalMesh;
    private int width;
    private int height;
    private int square_size;

    private int numPoints = 50;

    double precision = 5.5;

    PrecisionModel p = new PrecisionModel(precision);

    public CustomIrregularMesh(){
        vertexList = new ArrayList<>();
        segmentList = new ArrayList<>();
        polygonList = new ArrayList<>();
        width = 500;
        height = 500;
        square_size = 20;
    }

    public void generateRandomPoint(int width, int height){
        this.width = width;
        this.height = height;
        float x,y;
        Random rd = new Random();
        Vertex random = null;
        for(int i = 0; i < 50; i++){
            do{
             random =  Vertex.newBuilder().setX(rd.nextFloat()*width).setY(rd.nextFloat()*height).build();
            }while(vertexList.contains(random));
            vertexList.add(random);
        }
        addVertexColour();
        createSegments();

    }
    public void createSegments(){
       VoronoiDiagramBuilder voronoi = new VoronoiDiagramBuilder();

        GeometryFactory factory = new GeometryFactory();
        Collection collection = new ArrayList<>();
        Coordinate cord;
        for(Vertex v: vertexList){
            cord = new Coordinate();
            cord.setX(v.getX());
            cord.setY(v.getY());


           collection.add(cord);

        }

        voronoi.setSites(collection);
        System.out.println(voronoi.getSubdivision().getVertices(false));



    }
    public void addVertexColour() {
        ArrayList<Vertex> finalVertices = new ArrayList<>();
        for(Vertex v: vertexList){
            Random bag = new Random();
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            int alpha = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue + "," + alpha;
            Vertex coloured = Vertex.newBuilder(v).addProperties(Structs.Property.newBuilder().setKey("rgba_color").setValue(colorCode).build()).build();
            finalVertices.add(coloured);
        }
        addAllVertices(finalVertices);
    }
    public void addAllVertices(ArrayList<Vertex> vertices) {
        this.vertexList = vertices;
    }

    public Mesh finalizeMesh() {
        return Mesh.newBuilder().addAllVertices(this.vertexList).addAllSegments(this.segmentList).addAllPolygons(this.polygonList).build();
    }

}
