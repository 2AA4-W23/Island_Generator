import ca.mcmaster.cas.se2aa4.a2.customMesh.CustomMesh;
import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.generator.IrregularDotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import org.apache.commons.cli.*;
import java.io.FileWriter;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        // Extracting command line parameters
        Options options = new Options();
        options.addOption("t", false, "mesh type (grid or irregular)");

        options.addOption("d", false, "debug");
        options.addOption("p", true, "polygon");
        options.addOption("r", true, "relaxation");
        options.addOption("dim", true, "dimension");
        options.addOption("help", false, "help");

        CommandLineParser cml = new DefaultParser();
        CommandLine parser = cml.parse(options, args);

        int defDimen = 500;
        int poly = 25;
        boolean type = false;
        int relax = 2;
        boolean debug = false;

        Mesh myMesh;
        if(parser.hasOption("t")) {
            type = true;

            if (parser.hasOption("r")) {
                relax = Integer.parseInt(parser.getOptionValue("r"));
            }

            if (parser.hasOption("p")) {
                poly = Integer.parseInt(parser.getOptionValue("p"));
            }

            if (parser.hasOption("d")) {
                debug = Boolean.parseBoolean(parser.getOptionValue("p"));
            }
        }
        else{
            if (parser.hasOption("dim")) {
                defDimen = Integer.parseInt(parser.getOptionValue("dim"));
            }
        }
        if (type) {
            IrregularDotGen irregularGen = new IrregularDotGen(relax, poly,debug);
            myMesh= irregularGen.irregularGenerate();
        } else {
            DotGen generator = new DotGen(debug,defDimen);
            myMesh = generator.generate();
        }

        if(parser.hasOption("help")){
            System.out.println("-t irregular grid" );
            System.out.println("-p the number of polygons / if you dont use it just uses default values ");
            System.out.println("-r the number of relaxation / if you dont use it just uses default relaxation");
            System.out.println("-dim the dimensions(what you want) / if you dont use it just uses default dimensions");
        }

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
