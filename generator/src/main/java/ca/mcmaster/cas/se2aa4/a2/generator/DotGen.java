package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;


public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
       // Set<Vertex> vertices = new HashSet<>();
        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Segment> segments = new ArrayList<>();
        // Create all the vertices
        for(int x = 0; x < width; x += square_size) {
            for (int y = 0; y < height; y += square_size) {
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x + square_size).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y + square_size).build());
                vertices.add(Vertex.newBuilder().setX((double) x + square_size).setY((double) y + square_size).build());
            }
        }
        for(int i = 0; i < vertices.size() - 1; i++) {
            segments.add(Segment.newBuilder().setV1Idx(i).setV2Idx(i+1).build());
        }
        // Distribute colors randomly. Vertices are immutable, need to enrich them
        ArrayList<Vertex> verticesWithColors = new ArrayList<>();
        Random bag = new Random();
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
        }

        ArrayList<Segment> segmentsWithColors = new ArrayList<>();

        for(Segment s: segments){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Segment colored = Segment.newBuilder(s).addProperties(color).build();
            segmentsWithColors.add(colored);
        }

        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segmentsWithColors).build();
    }
}
