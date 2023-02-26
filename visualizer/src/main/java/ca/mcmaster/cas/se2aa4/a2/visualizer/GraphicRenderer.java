package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

import org.apache.commons.cli.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.List;
import java.awt.geom.Line2D;

public class GraphicRenderer {

    private static final int VERTEX_THICKNESS = 4;
    private static final int SEGMENT_THICKNESS = 3;



    public void render(Mesh aMesh, Graphics2D canvas, boolean debugMode) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);


        if (debugMode==false) {
            for (Vertex v : aMesh.getVerticesList()) {
                double centre_x = v.getX() - (VERTEX_THICKNESS / 2.0d);
                double centre_y = v.getY() - (VERTEX_THICKNESS / 2.0d);
                Color old = canvas.getColor();
                canvas.setColor(extractColor(v.getPropertiesList()));
                Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, VERTEX_THICKNESS, VERTEX_THICKNESS);
                canvas.fill(point);
                canvas.setColor(old);
            }
            for (Segment s : aMesh.getSegmentsList()) {
                Color old = canvas.getColor();
                canvas.setColor(extractColor(s.getPropertiesList()));
                Line2D line = new Line2D.Double(aMesh.getVerticesList().get(s.getV1Idx()).getX(), aMesh.getVerticesList().get(s.getV1Idx()).getY(), aMesh.getVerticesList().get(s.getV2Idx()).getX(), aMesh.getVerticesList().get(s.getV2Idx()).getY());
                canvas.setStroke(new BasicStroke(SEGMENT_THICKNESS));
                canvas.draw(line);
                canvas.setColor(old);
            }
            Double yTemp = aMesh.getVerticesList().get(aMesh.getPolygonsList().get(0).getCentroidIdx()).getY();
            for (Structs.Polygon p : aMesh.getPolygonsList()) {
                Color old = canvas.getColor();
                canvas.setColor(extractColor(p.getPropertiesList()));
                Path2D path = new Path2D.Double();

                if (aMesh.getVerticesList().get(p.getCentroidIdx()).getY() == yTemp) {
                    path.moveTo(aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(0)).getV1Idx()).getX(), aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(0)).getV1Idx()).getY());
                    path.lineTo(aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(0)).getV2Idx()).getX(), aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(0)).getV2Idx()).getY());
                    path.lineTo(aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(2)).getV1Idx()).getX(), aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(2)).getV1Idx()).getY());
                    path.lineTo(aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(2)).getV2Idx()).getX(), aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(2)).getV2Idx()).getY());

                } else {
                    path.moveTo(aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(0)).getV2Idx()).getX(), aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(0)).getV2Idx()).getY());
                    path.lineTo(aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(0)).getV1Idx()).getX(), aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(0)).getV1Idx()).getY());
                    path.lineTo(aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(2)).getV1Idx()).getX(), aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(2)).getV1Idx()).getY());
                    path.lineTo(aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(2)).getV2Idx()).getX(), aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(2)).getV2Idx()).getY());
                }
                path.closePath();
                canvas.fill(path);
                canvas.setColor(old);
            }
        }else{

            for (int i =0, j = 0 ; i<aMesh.getVerticesList().size(); i++) {
                Vertex v = aMesh.getVertices(i);
                double centre_x = v.getX() - (VERTEX_THICKNESS / 2.0d);
                double centre_y = v.getY() - (VERTEX_THICKNESS / 2.0d);
                Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, VERTEX_THICKNESS, VERTEX_THICKNESS);
                canvas.fill(point);

                if(v == aMesh.getVertices(i)){
                    canvas.setColor(Color.RED);
                }
                else{
                    canvas.setColor(Color.BLACK);
                }
            }
            for (Segment s : aMesh.getSegmentsList()) {
                canvas.setColor(Color.BLACK);
                Line2D line = new Line2D.Double(aMesh.getVerticesList().get(s.getV1Idx()).getX(), aMesh.getVerticesList().get(s.getV1Idx()).getY(), aMesh.getVerticesList().get(s.getV2Idx()).getX(), aMesh.getVerticesList().get(s.getV2Idx()).getY());
                canvas.setStroke(new BasicStroke(SEGMENT_THICKNESS));
                canvas.draw(line);
                canvas.setColor(Color.BLACK);

            }

            Double yTemp = aMesh.getVerticesList().get(aMesh.getPolygonsList().get(0).getCentroidIdx()).getY();
            for (Structs.Polygon p : aMesh.getPolygonsList()) {
                Color old = canvas.getColor();
                canvas.setColor(extractColor(p.getPropertiesList()));
                Path2D path = new Path2D.Double();

                if (aMesh.getVerticesList().get(p.getCentroidIdx()).getY() == yTemp) {
                    path.moveTo(aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(0)).getV1Idx()).getX(), aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(0)).getV1Idx()).getY());
                    path.lineTo(aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(0)).getV2Idx()).getX(), aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(0)).getV2Idx()).getY());
                    path.lineTo(aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(2)).getV1Idx()).getX(), aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(2)).getV1Idx()).getY());
                    path.lineTo(aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(2)).getV2Idx()).getX(), aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(2)).getV2Idx()).getY());

                } else {
                    path.moveTo(aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(0)).getV2Idx()).getX(), aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(0)).getV2Idx()).getY());
                    path.lineTo(aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(0)).getV1Idx()).getX(), aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(0)).getV1Idx()).getY());
                    path.lineTo(aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(2)).getV1Idx()).getX(), aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(2)).getV1Idx()).getY());
                    path.lineTo(aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(2)).getV2Idx()).getX(), aMesh.getVerticesList().get(aMesh.getSegmentsList().get(p.getSegmentIdxs(2)).getV2Idx()).getY());
                }
                path.closePath();
                canvas.fill(path);
                canvas.setColor(old);
            }
        }
    }

    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgba_color")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        int alpha = Integer.parseInt(raw[3]);
        return new Color(red, green, blue, alpha);
    }
}