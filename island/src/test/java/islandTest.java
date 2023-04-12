import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import adt.Edge;
import adt.Node;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a3.island.IslandGenerator;
import ca.mcmaster.cas.se2aa4.a3.island.elevationprofiles.HillsElevation;
import ca.mcmaster.cas.se2aa4.a3.island.elevationprofiles.MountainElevation;
import ca.mcmaster.cas.se2aa4.a3.island.extentionpoints.Aquifers;
import ca.mcmaster.cas.se2aa4.a3.island.extentionpoints.Lakes;
import ca.mcmaster.cas.se2aa4.a3.island.extentionpoints.Rivers;
import ca.mcmaster.cas.se2aa4.a3.island.shapes.CircleIsland;
import ca.mcmaster.cas.se2aa4.a3.island.soilabsorption.DrySoil;
import ca.mcmaster.cas.se2aa4.a3.island.starnetwork.Cities;
import ca.mcmaster.cas.se2aa4.a3.island.starnetwork.ConfigureCities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.io.IOException;
import java.util.ArrayList;


public class islandTest {
    private ArrayList<Structs.Polygon> temp;
    private ArrayList<String> type;
    private int numRivers;
    private ArrayList<Structs.Segment> tempSeg;
    private Rivers rivers;
    private ArrayList<Structs.Polygon> polygons;
    private ArrayList<Structs.Segment> segments;
    private ArrayList<Structs.Vertex> vertices;
    private Structs.Mesh aMesh;
    private ArrayList<Boolean> isAquifer;

    @BeforeEach
    void setUp() throws IOException {
        temp = new ArrayList<>();
        type = new ArrayList<>();
        numRivers = 3;
        tempSeg = new ArrayList<>();
        rivers = new Rivers(temp, type, numRivers, tempSeg);
        polygons = new ArrayList<>();
        segments = new ArrayList<>();
        vertices = new ArrayList<>();
        isAquifer = new ArrayList<>();
        try {
            aMesh = new MeshFactory().read("../ireg.mesh");
        } catch (IOException io) {
            System.out.println("couldnt find file");
        }
        for (int i = 0; i < aMesh.getPolygonsCount(); i++) {
            isAquifer.add(false);
        }
    }
//
//    @Test
//    void testGenerateCircleIsland() {
//        // Test that the two islands with the same parameters and seeds have the same type for each tile
//        CircleIsland circleIsland1 = new CircleIsland();
//        circleIsland1.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic", 5,123456789);
//        CircleIsland circleIsland2 = new CircleIsland();
//        circleIsland2.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic", 5,123456789);
//
//       // assertEquals(circleIsland1.getType(), circleIsland2.getType());
//       // assertEquals(circleIsland1.getTempSeg(), circleIsland2.getTempSeg());
//       // assertEquals(circleIsland1.getTempMeshProperties(), circleIsland2.getTempMeshProperties());
//
//        // Test that the two islands with the different parameters and seeds have a different type for the tiles
//        CircleIsland circleIsland3 = new CircleIsland();
//        circleIsland3.generateCircleIsland(aMesh, 700, 700, false, 5, 8, 5, "mountain", "wet", 1400, "Arctic", 5,995343222);
//
//      //  assertNotEquals(circleIsland1.getType(), circleIsland3.getType());
//      //  assertNotEquals(circleIsland1.getTempSeg(), circleIsland3.getTempSeg());
//      //  assertNotEquals(circleIsland1.getTempMeshProperties(), circleIsland3.getTempMeshProperties());
//    }
//
//    @Test
//    void testGenerateLakes() {
//        // Test that the generated rivers have non-zero length
//        CircleIsland circleIsland1 = new CircleIsland();
//        circleIsland1.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic",5, 123456789);
//        CircleIsland circleIsland2 = new CircleIsland();
//        circleIsland2.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic",5, 123456789);
//
//        Lakes lakes1 = new Lakes(circleIsland1.getTempMeshProperties(), circleIsland1.getType(), 5);
//        Lakes lakes2 = new Lakes(circleIsland2.getTempMeshProperties(), circleIsland2.getType(), 5);
//
//        lakes1.generateLakes(aMesh, 1234567);
//        lakes2.generateLakes(aMesh, 1234567);
//
//       // assertEquals(lakes1.getType(), lakes2.getType());
//      //  assertEquals(lakes1.getTempMeshProperties(), lakes2.getTempMeshProperties());
//
//        CircleIsland circleIsland3 = new CircleIsland();
//        circleIsland3.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic",5, 123456789);
//
//        Lakes lakes3 = new Lakes(circleIsland3.getTempMeshProperties(), circleIsland3.getType(), 5);
//        lakes3.generateLakes(aMesh, 111);
//
//       // assertNotEquals(lakes1.getType(), lakes3.getType());
//       // assertNotEquals(lakes1.getTempMeshProperties(), lakes3.getTempMeshProperties());
//    }
//
//    @Test
//    void testGenerateAquifers() {
//        // Test that the generated rivers have non-zero length
//        CircleIsland circleIsland1 = new CircleIsland();
//        circleIsland1.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic", 5,123456789);
//        CircleIsland circleIsland2 = new CircleIsland();
//        circleIsland2.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic", 5,123456789);
//
//        Aquifers aquifers1 = new Aquifers(circleIsland1.getTempMeshProperties(), circleIsland1.getType(), isAquifer, 4);
//        Aquifers aquifers2 = new Aquifers(circleIsland2.getTempMeshProperties(), circleIsland2.getType(), isAquifer, 4);
//
//
//        CircleIsland circleIsland3 = new CircleIsland();
//        circleIsland3.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic",5, 123456789);
//
//    }
//
//    @Test
//    void testGenerateRivers() {
//        // Test that the generated rivers have non-zero length
//        CircleIsland circleIsland1 = new CircleIsland();
//        circleIsland1.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic", 5,123456789);
//        CircleIsland circleIsland2 = new CircleIsland();
//        circleIsland2.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic",5, 123456789);
//
//        Rivers rivers1 = new Rivers(circleIsland1.getTempMeshProperties(), circleIsland1.getType(), 5, circleIsland1.getTempSeg());
//        Rivers rivers2 = new Rivers(circleIsland2.getTempMeshProperties(), circleIsland2.getType(), 5, circleIsland2.getTempSeg());
////
////        rivers1.generateRivers(aMesh, 700, 700, 123456789);
////        rivers2.generateRivers(aMesh, 700, 700, 123456789);
////
////        assertEquals(rivers1.getDischarge(), rivers2.getDischarge());
////        assertEquals(rivers1.getType(), rivers2.getType());
////        assertEquals(rivers1.getTempSeg(), rivers2.getTempSeg());
////        assertEquals(rivers1.getTempMeshProperties(), rivers2.getTempMeshProperties());
//
//        CircleIsland circleIsland3 = new CircleIsland();
//        circleIsland3.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic", 5,123456789);
//
//    }
//
//    @Test
//    void testHillsElevation() {
//        // Test that the generated rivers have non-zero length
//        CircleIsland circleIsland1 = new CircleIsland();
//        circleIsland1.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic", 5,123456789);
//        CircleIsland circleIsland2 = new CircleIsland();
//        circleIsland2.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic", 5,123456789);
//
//        HillsElevation he1 = new HillsElevation();
//        he1.computeElevations(aMesh, circleIsland1.getType(), 700, 700, 1400, 12345);
//        HillsElevation he2 = new HillsElevation();
//        he2.computeElevations(aMesh, circleIsland2.getType(), 700, 700, 1400, 12345);
//
//       // assertEquals(he1.getElevations(), he2.getElevations());
//
//        CircleIsland circleIsland3 = new CircleIsland();
//        circleIsland3.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic", 5,123456789);
//        HillsElevation he3 = new HillsElevation();
//        he3.computeElevations(aMesh, circleIsland1.getType(), 700, 700, 1400, 12);
//
//       // assertNotEquals(he1.getElevations(), he3.getElevations());
//
//    }
//
//    @Test
//    void testMountainElevation() {
//        // Test that the generated rivers have non-zero length
//        CircleIsland circleIsland1 = new CircleIsland();
//        circleIsland1.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic",5, 123456789);
//        CircleIsland circleIsland2 = new CircleIsland();
//        circleIsland2.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic", 5,123456789);
//
//        MountainElevation me1 = new MountainElevation();
//        me1.computeElevations(aMesh, circleIsland1.getType(), 700, 700, 1400, 12345);
//        MountainElevation me2 = new MountainElevation();
//        me2.computeElevations(aMesh, circleIsland2.getType(), 700, 700, 1400, 12345);
//
//      //  assertEquals(me1.getElevations(), me2.getElevations());
//
//        CircleIsland circleIsland3 = new CircleIsland();
//        circleIsland3.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic", 5,123456789);
//        MountainElevation me3 = new MountainElevation();
//        me3.computeElevations(aMesh, circleIsland1.getType(), 600, 600, 1200, 12345);
//
//       // assertNotEquals(me1.getElevations(), me3.getElevations());
//
//    }
    @Test
    void testConfigureCities(){
        CircleIsland circleIsland1 = new CircleIsland();
        circleIsland1.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic",5, 123456789);

        ConfigureCities configureCities1 = new ConfigureCities();
        configureCities1.initializeCities(circleIsland1.getType(),aMesh);
        ArrayList<Node> n1 = configureCities1.getNodes();
        ArrayList<Edge> e1 = configureCities1.getEdges();
        int counter = 0;

        // ENSURE THAT EACH NODE IS BEING ADDED TO AN EDGE CONNECTION
        for(Node n: n1){
            for(Edge e: e1){
                if(e.getSourceNode().equals(n)){
                    counter++;
                }
            }
        }
        assertEquals(counter,e1.size());

        //IF THERE ARE NO LAND TILES, THEN NO NODES/CITIES SHOULD BE CREATED

        ArrayList<String> newType = circleIsland1.getType();
        for(int i = 0; i < newType.size(); i++){
            newType.set(i,"ocean");
        }

        ConfigureCities configureCities2 = new ConfigureCities();
        configureCities2.initializeCities(newType,aMesh);
        assertEquals(configureCities2.getNodes().size(), 0);

    }
    @Test
    void testCities(){
        CircleIsland circleIsland1 = new CircleIsland();
        circleIsland1.generateCircleIsland(aMesh, 700, 700, false, 3, 2, 3, "hills", "dry", 1400, "Arctic",5, 123456789);

        ConfigureCities configureCities1 = new ConfigureCities();
        configureCities1.initializeCities(circleIsland1.getType(),aMesh);

        // CHECK THAT THERE IS A SHORTEST PATH BEING CALCULATED FOR EACH CITY BESIDES THE CAPITAL

        Cities cities1 = new Cities(circleIsland1.getTempVertex(),circleIsland1.getTempSeg(),5);
        cities1.generateCities(circleIsland1.getType(),aMesh,12344444);
        assertEquals(5-1,cities1.getPaths().size());

        // ENSURE THAT ALL NODES ARE BEING ADDED TO THE GRAPH
        assertEquals(configureCities1.getNodes().size(), cities1.getGraph().getNodes().size());
    }
}