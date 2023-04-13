package ca.mcmaster.cas.se2aa4.a3.island.tiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class City {
    private final int red, green, blue;

    public City(){
        this.red = 227;
        this.green = 148;
        this.blue = 75;
    }

    public Structs.Property setColourCode(){
        String colorCode = red + "," + green + "," + blue;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
        return color;
    }
}
