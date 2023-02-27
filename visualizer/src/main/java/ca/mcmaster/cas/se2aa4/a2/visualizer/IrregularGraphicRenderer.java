package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.List;
import java.awt.geom.Line2D;

public class IrregularGraphicRenderer {
    private static final int VERTEX_THICKNESS = 4;
    public void render(Mesh aMesh, Graphics2D canvas, boolean debugMode) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);

        for (Vertex v : aMesh.getVerticesList()) {
            double centre_x = v.getX() - (VERTEX_THICKNESS / 2.0d);
            double centre_y = v.getY() - (VERTEX_THICKNESS / 2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, VERTEX_THICKNESS, VERTEX_THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
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
