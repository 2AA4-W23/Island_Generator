import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a3.island.Sandbox;
import ca.mcmaster.cas.se2aa4.a3.island.configuration.Configuration;

import java.awt.*;


public class Main {
    public static void main(String[] args) throws Exception {
        Configuration config = new Configuration(args);
        Structs.Mesh aMesh = new MeshFactory().read(config.input());
       // System.out.println(aMesh.getPolygonsList() + " oooooooooooo");
        Sandbox sandbox = new Sandbox();
        Structs.Mesh islandMesh = sandbox.generateIsland(aMesh);
      //  System.out.println(islandMesh.getPolygonsList() + "iiiiiiiiiii");
        new MeshFactory().write(islandMesh, config.export(Configuration.OUTPUT));
    }
}
