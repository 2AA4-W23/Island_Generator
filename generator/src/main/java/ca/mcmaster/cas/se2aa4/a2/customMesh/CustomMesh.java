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
    private int width;
    private int height;
    private int square_size;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public CustomMesh(){
        vertexList = new ArrayList<>();
        segmentList = new ArrayList<>();
        polygonList = new ArrayList<>();
        width = 0;
        height = 0;
        square_size = 0;
    }


    @Override
    public void createVertices(int newWidth, int newHeight, int newSquare_size) {
        this.width = newWidth;
        this.height = newHeight;
        this.square_size = newSquare_size;
        int y = 0;
        while(y < this.height) {
            for (int x = 0; x < this.width; x += this.square_size) {
                addVertex(Vertex.newBuilder().setX(Float.parseFloat(df.format(x))).setY(Float.parseFloat(df.format(y))).build());
            }
            y += this.square_size;
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
        boolean firstSquare = true;
        boolean firstRow = true;
        int counter = 0;
        int topLeft;
        int topMiddle;
        int topRight;
        int middleRight;
        int bottomRight;
        int bottomMiddle;
        int bottomLeft;
        int middleLeft;

        int squareCounter = 0;
        int rowCounter = 0;
        ArrayList<Integer> polygonSegments = new ArrayList<>();

        System.out.println(vertexList.size() + "??????????????????");
        for(int i = 0; i < vertexList.size() - ((width/square_size)*2) -1; i+=2) {

            if ((i + 1) % (width / square_size) == 0) {
                i += width / square_size + 1;
                firstSquare = true;
                firstRow = false;
                squareCounter = 0;
                rowCounter+=1;
            }
            topLeft = i;
            topMiddle = i + 1;
            topRight = i+2;
            middleRight = i + 2 + (this.width / this.square_size);
            bottomRight = i + 2 + (2 * (this.width / this.square_size));
            bottomMiddle = i + 1 + (2 * (this.width / this.square_size));
            bottomLeft = i + (2 * (this.width / this.square_size));
            middleLeft = i + (this.width / this.square_size);

            polygonSegments.clear();
            if(firstRow) {
                //top left to top middle
                segmentList.add(Structs.Segment.newBuilder().setV1Idx(topLeft).setV2Idx(topMiddle).build());
                polygonSegments.add(counter);
                counter++;
                //top middle to top right
                segmentList.add(Structs.Segment.newBuilder().setV1Idx(topMiddle).setV2Idx(topRight).build());
                polygonSegments.add(counter);
                counter++;
            }
            // if we are in 2nd row, first square
            else if(rowCounter == 1 && firstSquare){
                System.out.println("password");
                polygonSegments.add((counter - (8 + (((((width/square_size)-1)/2)-1)*6)))+5);
                polygonSegments.add((counter - (8 + (((((width/square_size)-1)/2)-1)*6)))+4);
            }
            // 2nd row, 2nd square
            else if(rowCounter == 1 && squareCounter == 1){
                polygonSegments.add((counter - (8 + (((((width/square_size)-1)/2)-1)*6)))+7);
                polygonSegments.add((counter - (8 + (((((width/square_size)-1)/2)-1)*6)))+6);
            }
            // 2nd row, not 2nd square
            else if(rowCounter == 1 && squareCounter > 1){
                polygonSegments.add((counter - (8 + (((((width/square_size)-1)/2)-1)*6)))+9);
                polygonSegments.add((counter - (8 + (((((width/square_size)-1)/2)-1)*6)))+8);
            }

            else{
                polygonSegments.add((counter - (8 + (((((width/square_size)-1)/2)-1)*4))));
                polygonSegments.add((counter - (8 + (((((width/square_size)-1)/2)-1)*4)))+1);

            }

                //top right to right middle
                segmentList.add(Structs.Segment.newBuilder().setV1Idx(topRight).setV2Idx(middleRight).build());
                polygonSegments.add(counter);
                counter++;
                //right middle to right bottom
                segmentList.add(Structs.Segment.newBuilder().setV1Idx(middleRight).setV2Idx(bottomRight).build());
                polygonSegments.add(counter);
                counter++;
                //right bottom to middle bottom
                segmentList.add(Structs.Segment.newBuilder().setV1Idx(bottomRight).setV2Idx(bottomMiddle).build());
                 polygonSegments.add(counter);
                 counter++;
                //middle bottom to left bottom
                segmentList.add(Structs.Segment.newBuilder().setV1Idx(bottomMiddle).setV2Idx(bottomLeft).build());
                polygonSegments.add(counter);
                counter++;
                if (firstSquare) {
                //left bottom to left middle
                segmentList.add(Structs.Segment.newBuilder().setV1Idx(bottomLeft).setV2Idx(middleLeft).build());
                    polygonSegments.add(counter);
                    counter++;
                //left middle to top left
                segmentList.add(Structs.Segment.newBuilder().setV1Idx(middleLeft).setV2Idx(topLeft).build());
                    polygonSegments.add(counter);
                    counter++;
                firstSquare = false;
            }else if(squareCounter == 1){

                    polygonSegments.add(counter-12);
                    polygonSegments.add(counter-11);
                }
                else{
                    polygonSegments.add(counter-10);
                    polygonSegments.add(counter - 9);
                }
            addPolygon(Polygon.newBuilder().addAllSegmentIdxs(polygonSegments).build());
                squareCounter++;

        }
        System.out.println(polygonList.toString()+"   I LOVE HAMZAHAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
        addSegmentColour();
    }

    @Override
    public void addSegmentColour() {
       // int counter = 0;
        ArrayList<Segment> finalSegments = new ArrayList<>();
        for (Segment s: segmentList) {
            System.out.println(s.getV2Idx() + "!!!!!!!!!!!!!!!!!!!!");
                String[] vertexOne = vertexList.get(s.getV1Idx()).getProperties(0).getValue().toString().split(",");
                String[] vertexTwo = vertexList.get(s.getV2Idx()).getProperties(0).getValue().toString().split(",");
                int red = (Integer.valueOf(vertexOne[0]) + Integer.valueOf(vertexTwo[0])) / 2;
                int green = (Integer.valueOf(vertexOne[1]) + Integer.valueOf(vertexTwo[1])) / 2;
                int blue = (Integer.valueOf(vertexOne[2]) + Integer.valueOf(vertexTwo[2])) / 2;
                String colorCode = red + "," + green + "," + blue;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                Segment colored = Segment.newBuilder(s).addProperties(color).build();
                finalSegments.add(colored);
        }

        addAllSegments(finalSegments);
//        for (Segment s : segmentList) {
//            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!1");
//            System.out.println(vertexList.get(s.getV1Idx()).getX() + ", " + vertexList.get(s.getV1Idx()).getY());
//            System.out.println(vertexList.get(s.getV2Idx()).getX() + ", " + vertexList.get(s.getV2Idx()).getY());
//            System.out.println("??????????????????????????/");
//        }
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
