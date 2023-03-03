package ca.mcmaster.cas.se2aa4.a2.customMesh;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import org.locationtech.jts.*;
import org.locationtech.jts.algorithm.ConvexHull;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import java.text.DecimalFormat;
import java.util.Arrays;
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
    private int lloydRelaxationCounter = 0;

    private int relaxationNum = 2;
    private int delaunayCounter = 0;

    private int polygon = 50;


    PrecisionModel p = new PrecisionModel();

    private static final DecimalFormat df = new DecimalFormat("0.00");


//constructor for the irregular mesh
    public CustomIrregularMesh(int relaxationNum, int polygon) {
        vertexList = new ArrayList<>();
        segmentList = new ArrayList<>();
        polygonList = new ArrayList<>();
        this.width = 500;
        this.height = 500;
        square_size = 20;
        this.relaxationNum=relaxationNum;
        this.polygon=polygon;
    }
//generates a random point, one for each polygon
    public void generateRandomPoint(int width, int height, int polygon) {
        this.width = width;
        this.height = height;
        float x, y;
        Random rd = new Random();
        Vertex random = null;
        for (int i = 0; i < polygon; i++) {
            do {
                random = Vertex.newBuilder().setX(Double.parseDouble(df.format(rd.nextFloat() * width))).setY(Double.parseDouble(df.format(rd.nextFloat() * height))).build();
            } while (vertexList.contains(random));
            vertexList.add(random);
        }
        createVoronoi();
    }

    public void lloydRelaxation(){
        ArrayList<Vertex> tempList = new ArrayList<>();
        double x = 0;
        double y = 0;

        lloydRelaxationCounter += 1;
        //moves initial point to polygon's centroid by getting average points.
        for(Polygon p: polygonList){
            x = 0;
            y = 0;
            for(int i =0;i < p.getSegmentIdxsCount();i++ ){
              x += vertexList.get(segmentList.get(p.getSegmentIdxs(i)).getV1Idx()).getX();
              y += vertexList.get(segmentList.get(p.getSegmentIdxs(i)).getV1Idx()).getY();
            }
            tempList.add(Vertex.newBuilder().setX(Double.parseDouble(df.format(x/p.getSegmentIdxsCount()))).setY(Double.parseDouble(df.format(y/p.getSegmentIdxsCount()))).build());
        }
        vertexList = tempList;
        addVertexColour();
        createVoronoi();
    }

    public void createVoronoi() {
        VoronoiDiagramBuilder voronoi = new VoronoiDiagramBuilder();
//create the necessary
        GeometryFactory factory = new GeometryFactory(p);
        Collection collection = new ArrayList<>();
        Collection collection1 = new ArrayList<>();
        Envelope envelope = new Envelope(0, this.width, 0, this.height);
        voronoi.setClipEnvelope(envelope);
        Coordinate[] coords;
        Coordinate cord;
        for (Vertex v : vertexList) {
            cord = new Coordinate();
            cord.setX(v.getX());
            cord.setY(v.getY());

            collection.add(cord);

        }
        voronoi.setSites(collection);

        Geometry gUncropped = voronoi.getDiagram(factory);
        Geometry g = gUncropped.intersection(new GeometryFactory().toGeometry(envelope));
        collection1.add(g);

        factory.buildGeometry(collection1);
        makeVertices(g);
    }
//creates the verticies and the segments and orders them based on the convex hull.
    public void makeVertices(Geometry g) {
        segmentList.clear();
        polygonList.clear();
        int counter = 0;
        Coordinate[] c = null;
        ConvexHull convexHull;
        ArrayList<Integer> polygonSegments = new ArrayList<>();
        for (int i = 0; i < g.getNumGeometries(); i++) {
            convexHull = new ConvexHull(g.getGeometryN(i));
            Geometry conHull = convexHull.getConvexHull();
            c = conHull.getGeometryN(i).getCoordinates();
            polygonSegments.clear();
            for (int j = 0; j < c.length; j++) {
                vertexList.add(Vertex.newBuilder().setX(c[j].getX()).setY(c[j].getY()).build());
                if (j > 0) {
                    segmentList.add(Segment.newBuilder().setV1Idx(vertexList.size() - 1).setV2Idx(vertexList.size() - 2).build());
                    polygonSegments.add(counter);
                    counter++;
                }
            }
            addPolygon(Polygon.newBuilder().addAllSegmentIdxs(polygonSegments).setCentroidIdx(i).build());
        }
        //calls lloyd relaxation based on relaxation number
        if(lloydRelaxationCounter < relaxationNum) {
            lloydRelaxation();
        }
        delaunayCounter++;
        if(delaunayCounter == 1){
            delaunay(g);
        }
    }

    public void delaunay(Geometry g){
        ArrayList<Polygon> neighbours = new ArrayList<>();
        GeometryFactory geo = new GeometryFactory(p);
        DelaunayTriangulationBuilder d = new DelaunayTriangulationBuilder();
        Collection centroids = new ArrayList<>();
        Coordinate cord;
        int num = 0;
        for(Polygon p: polygonList){
            cord = new Coordinate();
            cord.setX(vertexList.get(p.getCentroidIdx()).getX());
            cord.setY(vertexList.get(p.getCentroidIdx()).getY());
            centroids.add(cord);
            num++;
        }
        d.setSites(centroids);
        Geometry f = d.getTriangles(geo);
        ArrayList<ArrayList<Integer>> neighboursList = new ArrayList<>();
        int count = 0;
        for(Polygon p: polygonList){
            neighboursList.add(new ArrayList<>());
            for(int c = 0; c < f.getNumGeometries(); c++){
                Coordinate[] r = f.getGeometryN(c).getCoordinates();

                if(vertexList.get(p.getCentroidIdx()).getX() == r[0].getX() && vertexList.get(p.getCentroidIdx()).getY() == r[0].getY()){
                    for(Polygon p2: polygonList){
                        if(vertexList.get(p2.getCentroidIdx()).getX() == r[1].getX() && vertexList.get(p2.getCentroidIdx()).getY() == r[1].getY()){
                            if(!(neighboursList.get(count).contains(polygonList.indexOf(p2)))) {
                                neighboursList.get(count).add(polygonList.indexOf(p2));
                            }
                        }
                        if(vertexList.get(p2.getCentroidIdx()).getX() == r[2].getX() && vertexList.get(p2.getCentroidIdx()).getY() == r[2].getY()){
                            if(!(neighboursList.get(count).contains(polygonList.indexOf(p2)))) {
                                neighboursList.get(count).add(polygonList.indexOf(p2));
                            }
                        }
                    }
                }
                else if(vertexList.get(p.getCentroidIdx()).getX() == r[1].getX() && vertexList.get(p.getCentroidIdx()).getY() == r[1].getY()){
                    for(Polygon p2: polygonList){
                        if(vertexList.get(p2.getCentroidIdx()).getX() == r[0].getX() && vertexList.get(p2.getCentroidIdx()).getY() == r[0].getY()){
                            if(!(neighboursList.get(count).contains(polygonList.indexOf(p2)))) {
                                neighboursList.get(count).add(polygonList.indexOf(p2));
                            }
                        }
                        if(vertexList.get(p2.getCentroidIdx()).getX() == r[2].getX() && vertexList.get(p2.getCentroidIdx()).getY() == r[2].getY()){
                            if(!(neighboursList.get(count).contains(polygonList.indexOf(p2)))) {
                                neighboursList.get(count).add(polygonList.indexOf(p2));
                            }
                        }
                    }
                }
                else if(vertexList.get(p.getCentroidIdx()).getX() == r[2].getX() && vertexList.get(p.getCentroidIdx()).getY() == r[2].getY()){
                    for(Polygon p2: polygonList){
                        if(vertexList.get(p2.getCentroidIdx()).getX() == r[0].getX() && vertexList.get(p2.getCentroidIdx()).getY() == r[0].getY()){
                            if(!(neighboursList.get(count).contains(polygonList.indexOf(p2)))) {
                                neighboursList.get(count).add(polygonList.indexOf(p2));
                            }
                        }
                        if(vertexList.get(p2.getCentroidIdx()).getX() == r[1].getX() && vertexList.get(p2.getCentroidIdx()).getY() == r[1].getY()){
                            if(!(neighboursList.get(count).contains(polygonList.indexOf(p2)))) {
                                neighboursList.get(count).add(polygonList.indexOf(p2));
                            }
                        }
                    }
                }

            }
            count++;
        }

        ArrayList<Polygon> temp = new ArrayList<>();
        int polygonCounter = 0;

        for(Polygon p: polygonList){
            temp.add(Polygon.newBuilder(p).addAllNeighborIdxs(neighboursList.get(polygonCounter)).build());
            polygonCounter++;
        }
        polygonList = temp;
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
    public ArrayList<Polygon> addPolygonColour() {
        ArrayList<Polygon> finalPolygon = new ArrayList<>();
        for(Polygon p: polygonList){
            Random bag2 = new Random();
            int red = bag2.nextInt(255);
            int green = bag2.nextInt(255);
            int blue = bag2.nextInt(255);
            int alpha = bag2.nextInt(255);;
            String colorCode = red + "," + green + "," + blue + "," + alpha;
            Structs.Property color = Structs.Property.newBuilder().setKey("rgba_color").setValue(colorCode).build();
            Polygon colored = Polygon.newBuilder(p).addProperties(color).build();
            finalPolygon.add(colored);
        }
        return finalPolygon;
    }





    public void addAllVertices(ArrayList<Vertex> vertices) {

        this.vertexList = vertices;
    }
    public Mesh finalizeMesh() {
        return Mesh.newBuilder().addAllVertices(this.vertexList).addAllSegments(this.segmentList).addAllPolygons(this.polygonList).build();
    }
}
