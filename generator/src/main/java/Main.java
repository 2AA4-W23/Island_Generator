import ca.mcmaster.cas.se2aa4.a2.customMesh.CustomMesh;
import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;


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
    }

}
