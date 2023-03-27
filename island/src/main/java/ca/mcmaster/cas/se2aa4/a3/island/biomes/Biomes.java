package ca.mcmaster.cas.se2aa4.a3.island.biomes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.*;

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
                        biomes.add("field");
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
                        biomes.add("field");
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
            }else{
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
                if(biomes.get(f).equals("iceland")){
                    Iceland iceland = new Iceland();
                    temp.set(f, Structs.Polygon.newBuilder(temp.get(f)).clearProperties().addProperties(iceland.setColourCode()).build());
                }
                if(biomes.get(f).equals("snow")){
                    Snow snow = new Snow();
                    temp.set(f, Structs.Polygon.newBuilder(temp.get(f)).clearProperties().addProperties(snow.setColourCode()).build());
                }
                if(biomes.get(f).equals("swamp")){
                    Swamp swamp = new Swamp();
                    temp.set(f, Structs.Polygon.newBuilder(temp.get(f)).clearProperties().addProperties(swamp.setColourCode()).build());
                }
                if(biomes.get(f).equals("tundra")){
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
