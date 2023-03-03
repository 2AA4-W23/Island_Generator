package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.customMesh.CustomIrregularMesh;
import ca.mcmaster.cas.se2aa4.a2.customMesh.CustomMesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.customMesh.customVertex;

public class IrregularDotGen {
    private final int width = 500;
    private final int height = 500;
    private int relaxation = 2;
    private int polygon = 25;

    Mesh finalMesh;

    public IrregularDotGen(int relaxation, int polygon, boolean debug) {
        this.relaxation = relaxation;
        this.polygon = polygon;
    }

    public Mesh irregularGenerate() {
        CustomIrregularMesh newMesh = new CustomIrregularMesh(relaxation,polygon);
        newMesh.generateRandomPoint(width, height,polygon);
        newMesh.addVertexColour();
        this.finalMesh = newMesh.finalizeMesh();


        return this.finalMesh;
    }
}

