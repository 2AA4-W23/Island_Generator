package ca.mcmaster.cas.se2aa4.a2.customMesh;



import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import java.text.DecimalFormat;
import java.util.Random;

public class customVertex {

    private Vertex vertex;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public customVertex(){
        this.vertex =Vertex.newBuilder().build();
    }

    public double getX(){
        return this.vertex.getX();
    }
    public double getY(){
        return this.vertex.getY();
    }
    public Property getColor(){
        return this.vertex.getProperties(0);
    }
   public void setX(float newX){
        Vertex.newBuilder(this.vertex).setX(Float.parseFloat(df.format(newX))).build();
   }

    public void setY(float newY){
        Vertex.newBuilder(this.vertex).setY(Float.parseFloat(df.format(newY))).build();
    }
    public void setColor(){
        Random bag = new Random();
        int red = bag.nextInt(255);
        int green = bag.nextInt(255);
        int blue = bag.nextInt(255);
        String colorCode = red + "," + green + "," + blue;
        Vertex.newBuilder(this.vertex).addProperties(Property.newBuilder().setKey("rgb_color").setValue(colorCode).build()).build();
    }



}
