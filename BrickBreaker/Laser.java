//package dfinal.game.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dsvid88 on 6/01/15.
 */
public class Laser extends movableObject {

    public Laser(Paddle fal) {

        super();

        //defined the points on a cartesean grid
        ArrayList<Point> pntCs = new ArrayList<Point>();

        pntCs.add(new Point(0, 3)); //top point

        pntCs.add(new Point(1, -1));
        pntCs.add(new Point(0, -2));
        pntCs.add(new Point(-1, -1));

        assignPolarPoints(pntCs);

        //a bullet expires after 20 frames
        setExpire(20);
        setRadius(6);

        setDeltaY(-10);

        setColor(Color.red);

        setCenter(new Point((int) fal.getCenter().getX() + 35, (int) fal.getCenter().getY() - 10));

        //set the bullet orientation to the falcon (ship) orientation
        setOrientation(fal.getOrientation() + 270);

    }

    public void move() {

        Point pnt = getCenter();

        double dX = pnt.x + getDeltaX();
        double dY = pnt.y + getDeltaY();

        //taking care of y coordinates
        if (pnt.y < 5) {
            this.expire();
        } else {
            setCenter(new Point((int) dX, (int) dY));
        }

    }

    //override the expire method - once an object expires, then remove it from the arrayList.
    public void expire() {
        if (getExpire() == 0)
            ControlCenter.movFriends.remove(this);
        else
            setExpire(getExpire() - 1);
    }

}