//package dfinal.game.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dsvid88 on 5/29/15.
 */
public class UnBreakable extends ImMovable {

    public UnBreakable(int x, int y) {
        super(x, y);

        Dimension DIM = new Dimension(50, 10);

        ArrayList<Point> pntCs = new ArrayList<Point>();

        //right points
        pntCs.add(new Point(1, 5));
        pntCs.add(new Point(0, 5));
        pntCs.add(new Point(0, 0));
        pntCs.add(new Point(1, 0));

        assignPolarPoints(pntCs);

        setColor(Color.gray);

        //put falcon in the middle.
        setCenter(new Point(x, y));

        //this is the size of a brick
        setRadius(50);

    }

    public void draw(Graphics g) {

        Color colBrick;

        if (getFadeValue() == 255) {
            colBrick = Color.white;
        } else {

            colBrick = new Color(255, 255, 255);
        }

        drawBrickWithColor(g, colBrick);
    } //end for loop*/

    public void drawBrickWithColor(Graphics g, Color col) {
        super.draw(g);

        g.drawPolygon(getXcoords(), getYcoords(), dDegrees.length);

        g.setColor(Color.gray);

        g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);

    }

}



