package ca.mcmaster.cas.se2aa4.a2.customMesh;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import java.awt.Color;
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
        while(y <= this.height) {
            for (int x = 0; x <= this.width; x += this.square_size) {
                addVertex(Vertex.newBuilder().setX(Float.parseFloat(df.format(x))).setY(Float.parseFloat(df.format(y))).build());
            }
            y += this.square_size;
        }
        addVertexColour();
       // System.out.println(vertexList.size() + "@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

    @Override
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
        ArrayList<Integer> neighbours = new ArrayList<>();
        int counter = 0;
        int topLeft;
        int topRight;
        int bottomRight;
        int bottomLeft;

        int squareCounter = 0;
        int rowCounter = 0;
        ArrayList<Integer> polygonSegments = new ArrayList<>();

        for(int i = 0; i < vertexList.size() - ((width/square_size)+2); i++) {
            ArrayList<Segment> centroids = new ArrayList<>();
            if ((i + 1) % ((width / square_size)+1) == 0) {
                i +=1;
                firstSquare = true;
                firstRow = false;
                rowCounter+=1;
            }
            topLeft = i;
            topRight = i + 1;
            bottomRight = i + 1 + ((this.width / this.square_size)+1);
            bottomLeft = i + ((this.width / this.square_size)+1);

            polygonSegments.clear();
            if(rowCounter == 0) {
                // top left to top right
                segmentList.add(Structs.Segment.newBuilder().setV1Idx(topLeft).setV2Idx(topRight).build());
                polygonSegments.add(counter);
                counter++;
            }
            else if (rowCounter > 0) {
                polygonSegments.add(polygonList.get(squareCounter - (width/square_size)).getSegmentIdxs(2));
            }
            // top right to bottom right
            segmentList.add(Structs.Segment.newBuilder().setV1Idx(topRight).setV2Idx(bottomRight).build());
            polygonSegments.add(counter);
            counter++;
            // bottom right to bottom left
            segmentList.add(Structs.Segment.newBuilder().setV1Idx(bottomRight).setV2Idx(bottomLeft).build());
            polygonSegments.add(counter);
            counter++;
            if(firstSquare) {
                // bottom left to top left
                segmentList.add(Structs.Segment.newBuilder().setV1Idx(bottomLeft).setV2Idx(topLeft).build());
                polygonSegments.add(counter);
                counter++;
                firstSquare = false;
            }
            else if(!firstSquare){
                polygonSegments.add(polygonList.get(squareCounter - 1).getSegmentIdxs(1));
            }

            addPolygon(Polygon.newBuilder().addAllSegmentIdxs(polygonSegments).build());
                squareCounter++;
        }


        addSegmentColour();
        addNeighbours();
        int polygonCounter = 0;
        ArrayList<ArrayList<Integer>> neighboursList = addNeighbours();
        ArrayList<Polygon> temp = new ArrayList<>();
        for(Polygon p: polygonList){

           temp.add(Polygon.newBuilder(p).addAllNeighborIdxs(neighboursList.get(polygonCounter)).build());
            polygonCounter++;
        }
        polygonList = temp;
    }

    @Override
    public void addSegmentColour() {
        ArrayList<Segment> finalSegments = new ArrayList<>();
        for (Segment s: segmentList) {

                String[] vertexOne = vertexList.get(s.getV1Idx()).getProperties(0).getValue().toString().split(",");
                String[] vertexTwo = vertexList.get(s.getV2Idx()).getProperties(0).getValue().toString().split(",");
                int red = (Integer.valueOf(vertexOne[0]) + Integer.valueOf(vertexTwo[0])) / 2;
                int green = (Integer.valueOf(vertexOne[1]) + Integer.valueOf(vertexTwo[1])) / 2;
                int blue = (Integer.valueOf(vertexOne[2]) + Integer.valueOf(vertexTwo[2])) / 2;
                int alpha = (Integer.valueOf(vertexOne[3])+Integer.valueOf(vertexTwo[3]))/2;
                String colorCode = red + "," + green + "," + blue + "," + alpha;
                Structs.Property color = Structs.Property.newBuilder().setKey("rgba_color").setValue(colorCode).build();
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
    public  ArrayList<ArrayList<Integer>> addNeighbours() {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        int squareCounter = 0;
        int rowCounter = 0;
        ArrayList<Integer> neighbours;
        for(int i = 0; i < (polygonList.size()); i++){
            list.add(new ArrayList<>());
            neighbours = new ArrayList<>();


            if(rowCounter == 0){

                if(squareCounter == 0){
                    list.get(i).add(i+1);
                    neighbours.add(i+1);
                    list.get(i).add(i+((width/square_size)+1));
                    neighbours.add(i+((width/square_size)+1));
                    squareCounter++;
                }
                else if (squareCounter < (width/square_size)-1){
                    list.get(i).add(i-1);
                    neighbours.add(i-1);
                    list.get(i).add(i+1);
                    neighbours.add(i+1);
                    list.get(i).add(i+((width/square_size)+1));
                    neighbours.add(i+ ((width/square_size)+1));
                    squareCounter++;
                }
                else if(squareCounter == ((width/square_size)-1)){
                    list.get(i).add(i-1);
                    neighbours.add(i - 1);
                    list.get(i).add(i+((width/square_size)+1));
                    neighbours.add(i+ ((width/square_size)+1));
                    rowCounter++;
                    squareCounter = 0;

                }
            }

            else if(rowCounter != ((width/square_size)-1)){

                if(squareCounter == 0){
                    list.get(i).add(i- ((width/square_size)+1));
                    neighbours.add(i- ((width/square_size)+1));
                    list.get(i).add(i+1);
                    neighbours.add(i+1);
                    list.get(i).add(i+((width/square_size)+1));
                    neighbours.add(i+ ((width/square_size)+1));
                    squareCounter++;
                }
                else if(squareCounter < (width/square_size)-1){
                    list.get(i).add(i- ((width/square_size)+1));
                    neighbours.add(i- (width/square_size));
                    list.get(i).add(i-1);
                    neighbours.add(i-1);
                    list.get(i).add(i+1);
                    neighbours.add(i+1);
                    list.get(i).add(i+((width/square_size)+1));
                    neighbours.add(i+ ((width/square_size)+1));
                    squareCounter++;
                }
                else{
                    list.get(i).add(i- ((width/square_size)+1));
                    neighbours.add(i- ((width/square_size)+1));
                    list.get(i).add(i-1);
                    neighbours.add(i-1);
                    list.get(i).add(i+((width/square_size)+1));
                    neighbours.add(i+ ((width/square_size)+1));

                    squareCounter = 0;
                    rowCounter++;
                }
            }
            else{
                if(squareCounter == 0){
                    list.get(i).add(i- ((width/square_size)+1));
                    neighbours.add(i- ((width/square_size)+1));
                    list.get(i).add(i+1);
                    neighbours.add(i+1);
                    squareCounter++;
                }
                else if(squareCounter < ((width/square_size)-1)){
                    list.get(i).add(i- ((width/square_size)+1));
                    neighbours.add(i- ((width/square_size)+1));
                    list.get(i).add(i-1);
                    neighbours.add(i-1);
                    list.get(i).add(i+1);
                    neighbours.add(i+1);
                    squareCounter++;
                }
                else{
                    list.get(i).add(i- ((width/square_size)+1));
                    neighbours.add(i- ((width/square_size)+1));
                    list.get(i).add(i-1);
                    neighbours.add(i-1);
                    squareCounter = 0;
                }

            }



            neighbours.clear();
        }
        return list;
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
