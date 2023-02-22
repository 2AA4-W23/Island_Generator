package ca.mcmaster.cas.se2aa4.a2.customMesh;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class CustomMesh extends MeshADT{


    private ArrayList<Vertex> vertexList;
    private ArrayList<Segment> segmentList;
    private ArrayList<Polygon> polygonList;

    private Mesh finalMesh;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public CustomMesh(){
        vertexList = new ArrayList<>();
        segmentList = new ArrayList<>();
        polygonList = new ArrayList<>();
    }


    @Override
    public void createVertices(int width, int height, int square_size) {
        int y = 0;
        while(y < height) {
            for (int x = 0; x < width; x += square_size) {
                addVertex(Vertex.newBuilder().setX(Float.parseFloat(df.format(x))).setY(Float.parseFloat(df.format(y))).build());
            }
            y += square_size;
        }
        addVertexColour();
    }

    @Override
    public void addVertexColour() {
        ArrayList<Vertex> finalVertices = new ArrayList<>();
        for(Vertex v: vertexList){
        Random bag = new Random();
        int red = bag.nextInt(255);
        int green = bag.nextInt(255);
        int blue = bag.nextInt(255);
        String colorCode = red + "," + green + "," + blue;
        Vertex coloured = Vertex.newBuilder(v).addProperties(Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build()).build();
        finalVertices.add(coloured);
    //    System.out.println(coloured.getPropertiesList().toString());
        }

        addAllVertices(finalVertices);
        System.out.println(this.vertexList.toString());
    }

    @Override
    public void addAllVertices(ArrayList<Vertex> vertices) {
        this.vertexList = vertices;
    }

    @Override
    public void createSegments() {
        for(int i = 0; i < vertexList.size() - 2; i+=2){
            if(vertexList.get(i+2).getY() !=0) {
                segmentList.add(Structs.Segment.newBuilder().setV1Idx(i).setV2Idx(i + 2).build());
            }
        }
        addSegmentColour();
    }

    @Override
    public void addSegmentColour() {
        int counter = 0;
        ArrayList<Segment> finalSegments = new ArrayList<>();
        for(Segment s: segmentList){
            String[] vertexOne = vertexList.get(counter).getProperties(0).getValue().toString().split(",");
            String[] vertexTwo = vertexList.get(counter+1).getProperties(0).getValue().toString().split(",");
            int red = (Integer.valueOf(vertexOne[0])+Integer.valueOf(vertexTwo[0]))/2;
            int green = (Integer.valueOf(vertexOne[1])+Integer.valueOf(vertexTwo[1]))/2;
            int blue = (Integer.valueOf(vertexOne[2])+Integer.valueOf(vertexTwo[2]))/2;
            String colorCode = red + "," + green + "," + blue;
            Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Segment colored = Segment.newBuilder(s).addProperties(color).build();
            finalSegments.add(colored);
            counter++;
        }
        addAllSegments(finalSegments);
    }

    @Override
    public void addAllSegments(ArrayList<Segment> segments) {
        this.segmentList = segments;
    }

    @Override
    public void createPolygons() {

    }

    @Override
    public void addAllPolygons(ArrayList<Polygon> list) {

    }

    @Override
    public void addVertex(Vertex vertex) {
        this.vertexList.add(vertex);
    }

    @Override
    public void addSegment(Segment segment) {
        this.segmentList.add(segment);
    }

    @Override
    public void addPolygon(Polygon polygon) {
        this.polygonList.add(polygon);
    }

    @Override
    public ArrayList<Segment> getSegments() {
        return this.segmentList;
    }

    @Override
    public ArrayList<Vertex> getVertices() {
        return this.vertexList;
    }

    @Override
    public ArrayList<Polygon> getPolygons() {
        return this.polygonList;
    }

    @Override
    public Mesh finalizeMesh() {
        return Mesh.newBuilder().addAllVertices(this.vertexList).addAllSegments(this.segmentList).build();
    }
}
