package ca.mcmaster.cas.se2aa4.a3.island.biomes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.*;

import java.util.ArrayList;

public class Biomes {
    private ArrayList<String> biomes;

    public Biomes() {
        this.biomes = new ArrayList<>();
    }
    public void FindBiomes(Structs.Mesh mesh, ArrayList<Double> elevations, ArrayList<Double> humidity, ArrayList<String> type) {

        for (int i = 0; i < mesh.getPolygonsList().size(); i++) {
            Structs.Polygon p = mesh.getPolygonsList().get(i);
            double elevation = elevations.get(i);
            double humid = humidity.get(i);


            // Determine biome based on elevation and humidity
            if (biomes.equals("Arctic")) {
                if (type.get(i).equals("land")) {
                    if (elevation < 10 && humid < 2) {
                        biomes.set(i, Arctic.ArcticTundra.toString());
                        biomes.add("snow");
                    } else if (elevation < 20 && humid < 15 || humid > 0.2) {
                        biomes.set(i, Arctic.Taiga.toString());
                        biomes.add("snow");

                    } else if (elevation < 30 && humid > 1 || humid < 10) {
                        biomes.set(i, Arctic.TemperateRainforest.toString());
                        biomes.add("snow");

                    } else if (elevation < 10 && humid < 0.2 || humid > 2) {
                        biomes.set(i, Arctic.ArcticDesert.toString());
                        biomes.add("snow");

                    } else {
                        biomes.set(i, Arctic.BorealForest.toString());
                    }
                }
            } else if (biomes.equals("Tropical")) {
                if (type.get(i).equals("land")) {
                    if (elevation < 40 && humid < 10 || humid > 5) {
                        biomes.set(i, Tropical.TropicalRainforest.toString());
                    } else if (humid < 4) {
                        biomes.add("field");
                    } else if (humid < 10) {
                        biomes.add("wetlands");
                    } else {
                        biomes.add("swamp");
                    }
                }
            } else if (biomes.equals("Grassland")) {
                if (humid < 1) {
                    biomes.add("tundra");
                } else if (humid < 5.0) {
                    biomes.add("snow");
                } else {
                    biomes.add("iceland");
                }
            } else {
                biomes.add("water");
            }
        }
    }

    public ArrayList<String> getBiomes() {
        return this.biomes;
    }

    public ArrayList<Structs.Polygon> assignColor(ArrayList<Structs.Polygon> temp, ArrayList<String> type ){

        for(int f = 0; f < temp.size(); f++){
            if(type.get(f).equals("land")){
                if(biomes.get(f).equals("desert")){
                    Desert desert = new Desert();
                    temp.set(f, Structs.Polygon.newBuilder(temp.get(f)).clearProperties().addProperties(desert.setColourCode()).build());
                }
                if(biomes.get(f).equals("field")){
                    Field field = new Field();
                    temp.set(f, Structs.Polygon.newBuilder(temp.get(f)).clearProperties().addProperties(field.setColourCode()).build());
                }
                if(biomes.get(f).equals("forest")){
                    Forest forest = new Forest();
                    temp.set(f, Structs.Polygon.newBuilder(temp.get(f)).clearProperties().addProperties(forest.setColourCode()).build());
                }
                if(biomes.get(f).equals( Arctic.Taiga.toString())){
                    Taiga taiga = new Taiga();
                    temp.set(f, Structs.Polygon.newBuilder(temp.get(f)).clearProperties().addProperties(taiga.setColourCode()).build());
                }
                if(biomes.get(f).equals(Arctic.TemperateRainforest.toString())){
                    Snow snow = new Snow();
                    temp.set(f, Structs.Polygon.newBuilder(temp.get(f)).clearProperties().addProperties(snow.setColourCode()).build());
                }
                if(biomes.get(f).equals("swamp")){
                    Swamp swamp = new Swamp();
                    temp.set(f, Structs.Polygon.newBuilder(temp.get(f)).clearProperties().addProperties(swamp.setColourCode()).build());
                }
                if(biomes.get(f).equals(Arctic.ArcticTundra.toString())){
                    Tundra tundra = new Tundra();
                    temp.set(f, Structs.Polygon.newBuilder(temp.get(f)).clearProperties().addProperties(tundra.setColourCode()).build());
                }
                if(biomes.get(f).equals("wetlands")){
                    Wetlands wetlands= new Wetlands();
                    temp.set(f, Structs.Polygon.newBuilder(temp.get(f)).clearProperties().addProperties(wetlands.setColourCode()).build());
                }
            }
        }
        return temp;
    }
}
