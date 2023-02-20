package ca.mcmaster.cas.se2aa4.a2.customMesh;



import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import java.text.DecimalFormat;
import java.util.Random;

public class customVertex {

    private float x;
    private float y;
    private Property color;
    private static final DecimalFormat df = new DecimalFormat("0.00");


    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }
    public Property getColor(){
        return this.color;
    }
   public void setX(float newX){
       this.x = Float.parseFloat(df.format(newX));
   }

    public void setY(float newY){
        this.y = Float.parseFloat(df.format(newY));
    }
    public void setColor(){
        Random bag = new Random();
        int red = bag.nextInt(255);
        int green = bag.nextInt(255);
        int blue = bag.nextInt(255);
        String colorCode = red + "," + green + "," + blue;
        this.color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
    }



}
