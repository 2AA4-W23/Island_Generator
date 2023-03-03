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
//creating verticies using width, height and square size (one row at a time).
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
    }
//adds color to verticies using a randomizer and stored it into vertex properties.
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
        }
        addAllVertices(finalVertices);
    }

//adding all the verticies to the vertex array list.
    @Override
    public void addAllVertices(ArrayList<Vertex> vertices) {
        this.vertexList = vertices;
    }
//creates segments using all the 4 verticies.
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

            // top right to top left
            if(rowCounter == 0) {
                segmentList.add(Structs.Segment.newBuilder().setV1Idx(topRight).setV2Idx(topLeft).build());
                polygonSegments.add(counter);
                counter++;
            }
            else{
                polygonSegments.add(polygonList.get(squareCounter - (width/square_size)).getSegmentIdxs(2));
            }

            // top left to bottom left
            if(firstSquare) {
                // bottom left to top left
                segmentList.add(Structs.Segment.newBuilder().setV1Idx(topLeft).setV2Idx(bottomLeft).build());
                polygonSegments.add(counter);
                counter++;
                firstSquare = false;
            }
            else if(!firstSquare){
                polygonSegments.add(polygonList.get(squareCounter - 1).getSegmentIdxs(3));
            }
            // bottom left to bottom right
            segmentList.add(Structs.Segment.newBuilder().setV1Idx(bottomLeft).setV2Idx(bottomRight).build());
            polygonSegments.add(counter);
            counter++;

            // bottom right to top right
            segmentList.add(Structs.Segment.newBuilder().setV1Idx(bottomRight).setV2Idx(topRight).build());
            polygonSegments.add(counter);
            counter++;

            addPolygon(Polygon.newBuilder().addAllSegmentIdxs(polygonSegments).build());
                squareCounter++;
        }

        addSegmentColour();
        addAllPolygons();
    }
// gets color average values for verticies that the segment is connecting and stores it.
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
    }

    @Override
    public void addAllSegments(ArrayList<Segment> segments) {
        this.segmentList = segments;
    }

    @Override
    public void createPolygons() {

    }
//adds the neighbours and the polygon
    @Override
    public void addAllPolygons() {
        addNeighbours();
        this.polygonList = addPolygonColour();
        int polygonCounter = 0;
        ArrayList<ArrayList<Integer>> neighboursList = addNeighbours();
        ArrayList<Polygon> temp = new ArrayList<>();

        for(Polygon p: polygonList){
            temp.add(Polygon.newBuilder(p).addAllNeighborIdxs(neighboursList.get(polygonCounter)).build());
            polygonCounter++;
        }


        polygonList = temp;
        ArrayList<Polygon> temp1 = new ArrayList<>();

        ArrayList<Integer> currentSegments = new ArrayList<>();

        for(Polygon p: polygonList){
            for(int i =0; i < p.getSegmentIdxsCount(); i++){
                currentSegments.add(p.getSegmentIdxs(i));
            }
            vertexList.add(setCentroid(currentSegments));
            temp1.add(Polygon.newBuilder(p).setCentroidIdx(vertexList.size()-1).build());
            currentSegments.clear();
        }
        polygonList = temp1;
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
// cross refrenced the polygon list and checked the adjacent polygon
    @Override
    public  ArrayList<ArrayList<Integer>> addNeighbours() {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        int squareCounter = 0;
        int rowCounter = 0;
        for(int i = 0; i < (polygonList.size()); i++){
            list.add(new ArrayList<>());
//condition for first row
            if(rowCounter == 0){

                if(squareCounter == 0){
                    list.get(i).add(i+1);
                    list.get(i).add(i+((width/square_size)));
                    squareCounter++;
                }
                else if (squareCounter < (width/square_size)-1){
                    list.get(i).add(i-1);
                    list.get(i).add(i+1);
                    list.get(i).add(i+((width/square_size)));
                    squareCounter++;
                }
                else if(squareCounter == ((width/square_size)-1)){
                    list.get(i).add(i-1);
                    list.get(i).add(i+((width/square_size)));
                    rowCounter++;
                    squareCounter = 0;

                }
            }//condition for middle rows
            else if(rowCounter != ((width/square_size)-1)){
                if(squareCounter == 0){
                    list.get(i).add(i- ((width/square_size)));
                    list.get(i).add(i+1);
                    list.get(i).add(i+((width/square_size)));
                    squareCounter++;
                }
                else if(squareCounter < (width/square_size)-1){
                    list.get(i).add(i- ((width/square_size)));
                    list.get(i).add(i-1);
                    list.get(i).add(i+1);
                    list.get(i).add(i+((width/square_size)));
                    squareCounter++;
                }
                else{
                    list.get(i).add(i- ((width/square_size)));
                    list.get(i).add(i-1);
                    list.get(i).add(i+((width/square_size)));

                    squareCounter = 0;
                    rowCounter++;
                }
            }//condition for last row
            else{
                if(squareCounter == 0){
                    list.get(i).add(i- ((width/square_size)));
                    list.get(i).add(i+1);
                    squareCounter++;
                }
                else if(squareCounter < ((width/square_size)-1)){
                    list.get(i).add(i- ((width/square_size)));
                    list.get(i).add(i-1);
                    list.get(i).add(i+1);
                    squareCounter++;
                }
                else{
                    list.get(i).add(i- ((width/square_size)));
                    list.get(i).add(i-1);
                    squareCounter = 0;
                }

            }

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
//adds polygon color using a randomizer
    @Override
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
    //sets centroid verticies of each polygon by getting the avg cooordinate of all the segments
    @Override
    public Vertex setCentroid(ArrayList<Integer> segments) {
        double x0 = (vertexList.get(segmentList.get(segments.get(0)).getV1Idx()).getX() + vertexList.get(segmentList.get(segments.get(0)).getV2Idx()).getX())/2.0;
        double x1 = (vertexList.get(segmentList.get(segments.get(1)).getV1Idx()).getX() + vertexList.get(segmentList.get(segments.get(1)).getV2Idx()).getX())/2.0;
        double x2 = (vertexList.get(segmentList.get(segments.get(2)).getV1Idx()).getX() + vertexList.get(segmentList.get(segments.get(2)).getV2Idx()).getX())/2.0;
        double x3 = (vertexList.get(segmentList.get(segments.get(3)).getV1Idx()).getX() + vertexList.get(segmentList.get(segments.get(3)).getV2Idx()).getX())/2.0;

        double y0 = (vertexList.get(segmentList.get(segments.get(0)).getV1Idx()).getY() + vertexList.get(segmentList.get(segments.get(0)).getV2Idx()).getY())/2.0;
        double y1 = (vertexList.get(segmentList.get(segments.get(1)).getV1Idx()).getY() + vertexList.get(segmentList.get(segments.get(1)).getV2Idx()).getY())/2.0;
        double y2 = (vertexList.get(segmentList.get(segments.get(2)).getV1Idx()).getY() + vertexList.get(segmentList.get(segments.get(2)).getV2Idx()).getY())/2.0;
        double y3 = (vertexList.get(segmentList.get(segments.get(3)).getV1Idx()).getY() + vertexList.get(segmentList.get(segments.get(3)).getV2Idx()).getY())/2.0;

        double x = (x0 + x1 + x2 + x3) / 4;
        double y = (y0 + y1 + y2 + y3) / 4;

        return Vertex.newBuilder().setX(x).setY(y).build();
    }

    @Override
    public Mesh finalizeMesh() {
        return Mesh.newBuilder().addAllVertices(this.vertexList).addAllSegments(this.segmentList).addAllPolygons(this.polygonList).build();
    }
}
