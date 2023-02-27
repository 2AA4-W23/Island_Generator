package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.customMesh.CustomIrregularMesh;
import ca.mcmaster.cas.se2aa4.a2.customMesh.CustomMesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.customMesh.customVertex;


public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;
    private boolean irregular = false;
    private boolean debug = false;
    Mesh finalMesh;

    public DotGen(boolean newIrregular, boolean newDebug){
        this.irregular = newIrregular;
        this.debug = newDebug;
    }

    public Mesh generate() {
        if(!irregular){
            CustomMesh newMesh = new CustomMesh();
        newMesh.createVertices(width, height, square_size);
        newMesh.createSegments();
        this.finalMesh = newMesh.finalizeMesh();
        }
        else{
            CustomIrregularMesh newMesh = new CustomIrregularMesh();
            newMesh.generateRandomPoint(width,height);
            this.finalMesh = newMesh.finalizeMesh();
        }




        
        return this.finalMesh;



    }
}
