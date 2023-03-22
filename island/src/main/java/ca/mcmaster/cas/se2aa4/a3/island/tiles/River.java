package ca.mcmaster.cas.se2aa4.a3.island.tiles;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class River {
    private final int red, green, blue;

    public River(){
        this.red = 0;
        this.green = 190;
        this.blue = 247;
    }
    public Structs.Property setColourCode(){
        String colorCode = red + "," + green + "," + blue;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
        return color;
    }
}
