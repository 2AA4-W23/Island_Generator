package ca.mcmaster.cas.se2aa4.a2.customMesh;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import org.locationtech.jts.*;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
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
    private int lloydRelaxationCounter = 0;
    private int delaunayCounter = 0;


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
        for (int i = 0; i < 25; i++) {
            do {
                random = Vertex.newBuilder().setX(rd.nextFloat() * width).setY(rd.nextFloat() * height).build();
            } while (vertexList.contains(random));
            vertexList.add(random);
        }
        createVoronoi();

    }
    public void lloydRelaxation(){
        ArrayList<Vertex> tempList = new ArrayList<>();
     //   vertexList.clear();
        double x = 0;
        double y = 0;

        lloydRelaxationCounter += 1;
        for(Polygon p: polygonList){
            x = 0;
            y = 0;
            for(int i =0;i < p.getSegmentIdxsCount();i++ ){
              x += vertexList.get(segmentList.get(p.getSegmentIdxs(i)).getV1Idx()).getX();
              y += vertexList.get(segmentList.get(p.getSegmentIdxs(i)).getV1Idx()).getY();
            }
            tempList.add(Vertex.newBuilder().setX(x/p.getSegmentIdxsCount()).setY(y/p.getSegmentIdxsCount()).build());
        }
     //   System.out.println(tempList.toString());
        System.out.println(tempList+"&&&&&&&&&&&&&&&&&&&&&&");
        vertexList = tempList;
        System.out.println(vertexList + "????????????");
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


        Geometry gUncropped = voronoi.getDiagram(factory);
        Geometry g = gUncropped.intersection(new GeometryFactory().toGeometry(envelope));
        collection1.add(g);



        factory.buildGeometry(collection1);
        System.out.println(g.getGeometryN(1).getCentroid() + "#########################");
        makeVertices(g);
        Coordinate[] c = g.getGeometryN(5).getCoordinates();

    }

    public void makeVertices(Geometry g) {
        segmentList.clear();
        polygonList.clear();
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
            addPolygon(Polygon.newBuilder().addAllSegmentIdxs(polygonSegments).setCentroidIdx(i).build());
           // System.out.println(vertexList.get(polygonList.get(i).getCentroidIdx()).getX() +", "+vertexList.get(polygonList.get(i).getCentroidIdx()).getY()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        }
//        int geoCounter = 0;
//        for(int k = 0; k < g.getNumGeometries(); k++){
//            if(vertexList.get(k).getX() == g.getGeometryN(geoCounter).getCentroid().getX() && vertexList.get(k).getY() == g.getGeometryN(geoCounter).getCentroid().getY()){
//                System.out.println("hiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
//                addPolygon(Polygon.newBuilder().addAllSegmentIdxs(polygonSegments).setCentroidIdx(k).build());
//                geoCounter++;
//            }
//        }
      //  System.out.println(vertexList);
   //     System.out.println(vertexList.get(polygonList.get(0).getCentroidIdx()).getX() + ", "+vertexList.get(polygonList.get(0).getCentroidIdx()).getY());
        if(lloydRelaxationCounter < 6) {
            System.out.println("hi");
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
        Collection centeroids = new ArrayList<>();
        Coordinate cord;
        for(int i = 0; i < g.getNumGeometries(); i++){
            cord = new Coordinate();
            cord.setX(g.getGeometryN(i).getCentroid().getX());
            cord.setY(g.getGeometryN(i).getCentroid().getY());
            centeroids.add(cord);
        }
        System.out.println(centeroids);
        d.setSites(centeroids);


         Geometry f = d.getTriangles(geo);

        Coordinate[] r = f.getGeometryN(3).getCoordinates();
        for(int i =0; i < r.length; i++){
            System.out.println(r[i]);
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
