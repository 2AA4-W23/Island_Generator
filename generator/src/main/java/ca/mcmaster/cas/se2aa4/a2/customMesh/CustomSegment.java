package ca.mcmaster.cas.se2aa4.a2.customMesh;
import java.awt.Color;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.util.Random;
public class CustomSegment {

    private Segment segment;

    public CustomSegment(){
        this.segment = Segment.newBuilder().build();
    }
    public int getV1Idx(){
        return this.segment.getV1Idx();
    }
    public double getV2Idx(){
        return this.segment.getV2Idx();
    }
    public Property getColor(){
        return this.segment.getProperties(0);
    }
    public void setV1Idx(int newV1IDX){
        Segment.newBuilder(this.segment).setV1Idx(newV1IDX).build();
    }

    public void setV2IDX(int newV2IDX){
        Segment.newBuilder(this.segment).setV2Idx(newV2IDX).build();
    }

    public void setColor(Structs.Vertex vertexOne, Structs.Vertex vertexTwo){
        String[] v1 = vertexOne.getProperties(0).getValue().toString().split(",");
        String[] v2 = vertexTwo.getProperties(0).getValue().toString().split(",");
        int red = (Integer.valueOf(v1[0])+Integer.valueOf(v2[0]))/2;
        int green = (Integer.valueOf(v1[1])+Integer.valueOf(v2[1]))/2;
        int blue = (Integer.valueOf(v1[2])+Integer.valueOf(v2[2]))/2;
        int alpha = (Integer.valueOf(v1[3])+Integer.valueOf(v2[3]))/2;
        String colorCode = red + "," + green + "," + blue + "," + alpha;
        Property color = Property.newBuilder().setKey("rgba_color").setValue(colorCode).build();
    }




}




