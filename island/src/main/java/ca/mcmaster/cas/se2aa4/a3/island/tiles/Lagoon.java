package ca.mcmaster.cas.se2aa4.a3.island.tiles;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class Lagoon {
    private final int red, green, blue;

    public Lagoon(){
        this.red = 70;
        this.green = 160;
        this.blue = 180;
    }

    public Structs.Property setColourCode(){
        String colorCode = red + "," + green + "," + blue;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
        return color;
    }
}
