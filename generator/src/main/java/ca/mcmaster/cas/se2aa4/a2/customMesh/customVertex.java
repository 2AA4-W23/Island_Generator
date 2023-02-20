package ca.mcmaster.cas.se2aa4.a2.customMesh;



import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import java.text.DecimalFormat;

public class customVertex {

    private float x;
    private float y;
    private static final DecimalFormat df = new DecimalFormat("0.00");

   public void setX(float newX){
       this.x = Float.parseFloat(df.format(newX));
   }

    public void setY(float newY){
        this.y = Float.parseFloat(df.format(newY));
    }


}
