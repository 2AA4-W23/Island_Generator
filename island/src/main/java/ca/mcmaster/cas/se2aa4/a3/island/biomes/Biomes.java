package ca.mcmaster.cas.se2aa4.a3.island.biomes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.ArrayList;

public class Biomes {
    private ArrayList<String> biomes;
    public Biomes(){
        this.biomes = new ArrayList<>();
    }


    public void FindBiomes(Structs.Mesh mesh, ArrayList<Double> elevations, ArrayList<Double> humidity, ArrayList<String> type) {


        for (int i = 0; i < mesh.getPolygonsList().size(); i++) {
            Structs.Polygon p = mesh.getPolygonsList().get(i);
            double elevation = elevations.get(i);
            double humid = humidity.get(i);

            // Determine biome based on elevation and humidity
            if (type.get(i).equals("land")) {
                if (elevation < 10) {
                    if (humid < 0.2) {
                        biomes.add("desert");
                    } else if (humid < 1.5) {
                        biomes.add("fields");
                    } else if (humid < 4.0) {
                        biomes.add("forest");
                    } else if (humid < 10) {
                        biomes.add("wetlands");
                    } else {
                        biomes.add("swamp");
                    }
                } else if (elevation < 30) {
                    if (humid < 0.2) {
                        biomes.add("desert");
                    } else if (humid < 4) {
                        biomes.add("fields");
                    } else if (humid < 10) {
                        biomes.add("wetlands");
                    } else {
                        biomes.add("swamp");
                    }
                } else if (elevation < 70) {
                    if (humid < 1) {
                        biomes.add("tundra");
                    } else if (humid < 5.0) {
                        biomes.add("snow");
                    } else {
                        biomes.add("iceland");
                    }
                } else if (elevation > 70) {
                    if (humid < 5) {
                        biomes.add("snow");
                    } else {
                        biomes.add("forest");
                    }
                }
            }
        }
    }

    public ArrayList<String> getBiomes() {
        return this.biomes;
    }

    public ArrayList<Structs.Polygon> assignColor(ArrayList<Structs.Polygon> temp){


        return temp;
    }
}
