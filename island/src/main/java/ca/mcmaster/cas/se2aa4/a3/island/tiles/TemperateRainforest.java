package ca.mcmaster.cas.se2aa4.a3.island.tiles;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class TemperateRainforest {
    private final int red, green, blue;

    public TemperateRainforest(){
        this.red = 34;
        this.green = 83;
        this.blue = 53;
    }

    public Structs.Property setColourCode(){
        String colorCode = red + "," + green + "," + blue;
        Structs.Property color = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
        return color;
    }
}
