package ca.mcmaster.cas.se2aa4.a2.generator.export.enricher;

import ca.mcmaster.cas.se2aa4.a3.MeshADT.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public interface Enricher {

    Structs.Mesh process(Structs.Mesh aMesh);

}
