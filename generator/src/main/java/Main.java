import ca.mcmaster.cas.se2aa4.a2.customMesh.CustomMesh;
import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.generator.IrregularDotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import org.apache.commons.cli.*;



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
        MeshFactory factory = new MeshFactory();
        factory.write(myMesh, args[0]);
    }
}
