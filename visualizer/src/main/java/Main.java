import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.visualizer.GraphicRenderer;
import ca.mcmaster.cas.se2aa4.a2.visualizer.IrregularGraphicRenderer;
import ca.mcmaster.cas.se2aa4.a2.visualizer.MeshDump;
import ca.mcmaster.cas.se2aa4.a2.visualizer.SVGCanvas;

import org.apache.commons.cli.*;


import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        String input = args[0];
        String output = args[1];

        // Extracting command line parameters
        boolean debugMode = false;
        boolean irregular = true;

        Options options = new Options();
        options.addOption("t", false, "mesh type (grid or irregular)");
        options.addOption("d", false, "debug");

        CommandLineParser cml = new DefaultParser();
        CommandLine parser = cml.parse(options, args);

        boolean grid = false;

        // Getting width and height for the canvas
        Structs.Mesh aMesh = new MeshFactory().read(input);
        double max_x = Double.MIN_VALUE;
        double max_y = Double.MIN_VALUE;
        for (Structs.Vertex v: aMesh.getVerticesList()) {
            max_x = (Double.compare(max_x, v.getX()) < 0? v.getX(): max_x);
            max_y = (Double.compare(max_y, v.getY()) < 0? v.getY(): max_y);
        }
        // Creating the Canvas to draw the mesh
        Graphics2D canvas = SVGCanvas.build((int) Math.ceil(max_x), (int) Math.ceil(max_y));

        // Painting the mesh on the canvas
//        if(!irregular) {
//
//        }
//        else{
//            irregularRenderer.render(aMesh, canvas, debugMode);
//        }
        Structs.Mesh myMesh;
        if(parser.hasOption("t")) {
            grid = Boolean.parseBoolean(parser.getOptionValue("t"));
        }
        if (parser.hasOption("d")) {
            debugMode = true;
        }
        GraphicRenderer renderer = new GraphicRenderer();
        renderer.render(aMesh, canvas, debugMode,grid );

        // Storing the result in an SVG file
        SVGCanvas.write(canvas, output);
        // Dump the mesh to stdout
        MeshDump dumper = new MeshDump();
        dumper.dump(aMesh);
    }
}
