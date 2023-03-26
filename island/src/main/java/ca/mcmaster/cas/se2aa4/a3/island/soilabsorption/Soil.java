package ca.mcmaster.cas.se2aa4.a3.island.soilabsorption;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;

public interface Soil {
    void computeHumidity(Structs.Mesh mesh, ArrayList<String> type, double minDimension);
}
