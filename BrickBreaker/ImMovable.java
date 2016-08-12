//package dfinal.game.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dsvid88 on 5/24/15.
 */
public class ImMovable extends movableObject {

    public ImMovable(int x, int y) {
        super();

        Dimension DIM = new Dimension(50, 10);

        ArrayList<Point> pntCs = new ArrayList<Point>();

        //right points
        pntCs.add(new Point(1, 5));
        pntCs.add(new Point(0, 5));
        pntCs.add(new Point(0, 0));
        pntCs.add(new Point(1, 0));

        assignPolarPoints(pntCs);

        setColor(Color.white);

        //put falcon in the middle.
        setCenter(new Point(x, y));

        //this is the size of a brick
        setRadius(50);

    }

    public void draw(Graphics g) {

        //does the fading at the beginning or after hyperspace
        Color colImmovable;

        if (getFadeValue() == 255) {
            colImmovable = Color.white;
        } else {

            colImmovable = new Color(255, 255, 255);
        }

        drawBricksWithColor(g, colImmovable);
    } //end for loop*/

    public void drawBricksWithColor(Graphics g, Color col) {
        super.draw(g);

        g.drawPolygon(getXcoords(), getYcoords(), dDegrees.length);

        g.setColor(new Color(200, 200, 100));
        g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
    }


}
