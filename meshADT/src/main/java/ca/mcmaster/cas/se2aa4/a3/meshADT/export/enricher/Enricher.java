package ca.mcmaster.cas.se2aa4.a3.meshADT.export.enricher;

import ca.mcmaster.cas.se2aa4.a2.generator.adt.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public interface Enricher {

    Structs.Mesh process(Structs.Mesh aMesh);

}
