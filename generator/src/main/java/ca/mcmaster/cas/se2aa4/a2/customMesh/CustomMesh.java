package ca.mcmaster.cas.se2aa4.a2.customMesh;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class CustomMesh extends MeshADT{


    private ArrayList<Vertex> vertexList;
    private ArrayList<Segment> segmentList;
    private ArrayList<customPolygon> polygonList;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public CustomMesh(){
        vertexList = new ArrayList<>();
        segmentList = new ArrayList<>();
        polygonList = new ArrayList<>();
    }


    @Override
    public void addAllVertices(int width, int height, int square_size) {
        for(int x = 0; x < width; x += square_size) {
            for (int y = 0; y < height; y += square_size) {

                addVertex(Vertex.newBuilder().setX(Float.parseFloat(df.format(x))).setY(Float.parseFloat(df.format(y))).build());
                addVertex(Vertex.newBuilder().setX(Float.parseFloat(df.format(x+square_size))).setY(Float.parseFloat(df.format(y))).build());
                addVertex(Vertex.newBuilder().setX(Float.parseFloat(df.format(x+square_size))).setY(Float.parseFloat(df.format(y+square_size))).build());
                addVertex(Vertex.newBuilder().setX(Float.parseFloat(df.format(x))).setY(Float.parseFloat(df.format(y+square_size))).build());
                }
            }
        addVertexColour();

    }

    @Override
    public void addVertexColour() {
        for(Vertex v: vertexList){
        Random bag = new Random();
        int red = bag.nextInt(255);
        int green = bag.nextInt(255);
        int blue = bag.nextInt(255);
        String colorCode = red + "," + green + "," + blue;
        Vertex.newBuilder(v).addProperties(Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build()).build();
        }

    }

    @Override
    public void addAllSegments() {
        for(int i = 0; i < vertexList.size(); i++){
            if(vertexList.get(i+1).getY() !=0) {
                segmentList.add(Structs.Segment.newBuilder().setV1Idx(i).setV2Idx(i + 1).build());
            }
        }
        addSegmentColour();
    }

    @Override
    public void addSegmentColour() {
        int counter = 0;
        for(Segment s: segmentList){
            String[] vertexOne = vertexList.get(counter).getProperties(0).getValue().toString().split(",");
            String[] vertexTwo = vertexList.get(counter+1).getProperties(0).getValue().toString().split(",");
            int red = (Integer.valueOf(vertexOne[0])+Integer.valueOf(vertexTwo[0]))/2;
            int green = (Integer.valueOf(vertexOne[1])+Integer.valueOf(vertexTwo[1]))/2;
            int blue = (Integer.valueOf(vertexOne[2])+Integer.valueOf(vertexTwo[2]))/2;
            String colorCode = red + "," + green + "," + blue;
            Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Segment colored = Segment.newBuilder(s).addProperties(color).build();
            segmentList.add(colored);
            counter++;
        }
    }

    @Override
    public void addAllPolygons(ArrayList<customPolygon> list) {
        this.polygonList = list;
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
    public void addPolygon(customPolygon polygon) {
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
    public ArrayList<customPolygon> getPolygons() {
        return this.polygonList;
    }
}
