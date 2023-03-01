package ca.mcmaster.cas.se2aa4.a2.customMesh;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import org.locationtech.jts.*;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory;
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

    private boolean lloydRelaxation = false;

    PrecisionModel p = new PrecisionModel();

    public CustomIrregularMesh() {
        vertexList = new ArrayList<>();
        segmentList = new ArrayList<>();
        polygonList = new ArrayList<>();
        this.width = 500;
        this.height = 500;
        square_size = 20;
    }

    public void generateRandomPoint(int width, int height) {
        this.width = width;
        this.height = height;
        float x, y;
        Random rd = new Random();
        Vertex random = null;
        for (int i = 0; i < 50; i++) {
            do {
                random = Vertex.newBuilder().setX(rd.nextFloat() * width).setY(rd.nextFloat() * height).build();
            } while (vertexList.contains(random));
            vertexList.add(random);
        }
        createVoronoi();

    }
    public void lloydRelaxation(){
        ArrayList<Vertex> tempList = new ArrayList<>();
        double x = 0;
        double y = 0;
        lloydRelaxation = true;

        for(Polygon p: polygonList){
            for(int i =0;i < p.getSegmentIdxsCount();i++ ){
              x += vertexList.get(segmentList.get(p.getSegmentIdxs(i)).getV1Idx()).getX();
              y += vertexList.get(segmentList.get(p.getSegmentIdxs(i)).getV1Idx()).getY();
            }
            tempList.add(Vertex.newBuilder().setX(x/p.getSegmentIdxsCount()).setY(y/p.getSegmentIdxsCount()).build());
        }
        vertexList = tempList;
        addVertexColour();
        createVoronoi();
    }

    public void createVoronoi() {
        VoronoiDiagramBuilder voronoi = new VoronoiDiagramBuilder();


        GeometryFactory factory = new GeometryFactory(p);
        Collection collection = new ArrayList<>();
        Collection collection1 = new ArrayList<>();
        Envelope envelope = new Envelope(0, this.width, 0, this.height);
        voronoi.setClipEnvelope(envelope);
        Coordinate[] coords;
        //collection.toArray();
        Coordinate cord;
        for (Vertex v : vertexList) {
            cord = new Coordinate();
            cord.setX(v.getX());
            cord.setY(v.getY());


            collection.add(cord);

            //  factory.createPoint(cord);
        }
        voronoi.setSites(collection);


        Geometry g = voronoi.getDiagram(factory);

        collection1.add(g);
        factory.buildGeometry(collection1);
        makeVertices(g);
        Coordinate[] c = g.getGeometryN(5).getCoordinates();

        //  System.out.println(factory.toString());
        //  System.out.println(voronoi.getSubdivision().getVertices(true));
    }

    public void makeVertices(Geometry g) {
        int counter = 0;
        Coordinate[] c = null;
        ArrayList<Integer> polygonSegments = new ArrayList<>();
        for (int i = 0; i < g.getNumGeometries(); i++) {
            c = g.getGeometryN(i).getCoordinates();
            polygonSegments.clear();
            for (int j = 0; j < c.length; j++) {
                vertexList.add(Vertex.newBuilder().setX(c[j].getX()).setY(c[j].getY()).build());
                if (j > 0) {
                    segmentList.add(Segment.newBuilder().setV1Idx(vertexList.size() - 1).setV2Idx(vertexList.size() - 2).build());
                    polygonSegments.add(counter);
                    counter++;


                }
            }

            addPolygon(Polygon.newBuilder().addAllSegmentIdxs(polygonSegments).build());
        }
        if(!lloydRelaxation) {
            lloydRelaxation();
        }

    }

    public void addPolygon(Polygon p) {
        polygonList.add(p);
    }

    public void addVertexColour() {
        ArrayList<Vertex> finalVertices = new ArrayList<>();
        for (Vertex v : vertexList) {
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
