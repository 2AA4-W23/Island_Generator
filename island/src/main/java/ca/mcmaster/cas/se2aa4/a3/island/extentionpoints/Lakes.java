package ca.mcmaster.cas.se2aa4.a3.island.extentionpoints;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Beach;
import ca.mcmaster.cas.se2aa4.a3.island.tiles.Lake;

import java.util.ArrayList;

public class Lakes {

    public static ArrayList<Structs.Polygon> generateLakes(Structs.Mesh mesh, ArrayList<Structs.Polygon> temp, ArrayList<String> type, int numLakes){
        for(int l = 0; l < numLakes; l++) {
            String tileType;
            int rand = 0;
            for(int r = 0; r < temp.size(); r++){
                rand = (int) (Math.random() * temp.size());
                tileType = type.get(rand);
                if(tileType.equals("land")){
                    break;
                }
            }

            Polygon p = temp.get(rand);
         //   type.set(mesh.getPolygonsList().indexOf(p), "lake");
          //  Lake lake = new Lake();
          //  temp.set(mesh.getPolygonsList().indexOf(p), Structs.Polygon.newBuilder(p).clearProperties().addProperties(lake.setColourCode()).build());
            int lakeSize = (int) (Math.random() * p.getNeighborIdxsCount());
            int lakeCounter = 0;
            do {
                for (int i : p.getNeighborIdxsList()) {
                    if (type.get(i).equals("land")) {
                        type.set(i, "lake");
                        Lake lakeN = new Lake();
                        temp.set(i, Structs.Polygon.newBuilder(temp.get(i)).clearProperties().addProperties(lakeN.setColourCode()).build());
                    }
                    lakeCounter++;
                }
            } while (lakeCounter < lakeSize);
        }
        return temp;
    }
}
