import ca.mcmaster.cas.se2aa4.a2.customMesh.CustomMesh;
import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;


import java.io.FileWriter;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        // Extracting command line parameters
        boolean debugMode = false;
        boolean irregular = true;
        if(args.length >= 3) {
            if (args[2].equals("-X")) {
                debugMode = true;
            } else {
                debugMode = false;
            }
            if(args.length == 4){
                if(args[3].equals("Irregular")){
                    irregular = true;
                }
            }
        }
        DotGen generator = new DotGen(irregular, debugMode);
        Mesh myMesh = generator.generate();
        MeshFactory factory = new MeshFactory();
        factory.write(myMesh, args[0]);


        //Created .obj file
        FileWriter writer = new FileWriter("newobj.obj");

        //added vertices to file using "v"
        for(int i = 0; i < myMesh.getVerticesCount(); i++){
            writer.write("v "+ myMesh.getVertices(i).getX()+" "+myMesh.getVertices(i).getY()+"\n");
        }

        //added segments to file using "l"
        for(int i = 0; i < myMesh.getSegmentsList().size(); i++){
            writer.write("l "+ myMesh.getSegments(i).getV1Idx()+" "+myMesh.getSegments(i).getV2Idx()+"\n");
        }

        //added polygons to file using "f"
        for(int i =0; i < myMesh.getPolygonsList().size();i++) {
            writer.write("f ");
            for (int j = 0; j < myMesh.getPolygons(i).getSegmentIdxsCount(); j++) {
                writer.write(myMesh.getPolygons(i).getSegmentIdxs(j) + " ");
            }
            writer.write("\n");
        }
        //closed file
        writer.close();
    }

}
