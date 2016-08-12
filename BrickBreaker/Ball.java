//package dfinal.game.model;

//import dfinal.sounds.Sound;

import java.awt.*;

/**
 * Created by dsvid88 on 5/19/15.
 */
public class Ball extends movableObject {

    //size of the ball
    private final int RAD = 50;

    public Ball(int nSize) {

        //call superclass constructor
        super();

        //spaning the ball
        setCenter(new Point(300, 300));

        //set DeltaX
        int nDX = 10;
        setDeltaX(nDX);

        //set DeltaY
        int nDY = -10;

        setDeltaY(nDY);

        setRadius(RAD / (nSize * 2));

    }

    public int getSize() {

        int nReturn = 0;

        switch (getRadius()) {
            case 100:
                nReturn = 0;
                break;
            case 50:
                nReturn = 1;
                break;
            case 25:
                nReturn = 2;
                break;
        }
        return nReturn;
    }

    //overridden
    public void move() {
        Point pnt = getCenter();

        double dX = pnt.x + getDeltaX();
        double dY = pnt.y + getDeltaY();

        if (pnt.x > 690) {
            Sound.playSound("CarDoor.wav");
            setCenter(new Point(690, pnt.y));
            this.setDeltaX(-15.0);

        } else if (pnt.x < 5) {
            Sound.playSound("CarDoor.wav");
            setCenter(new Point(6, pnt.y));
            this.setDeltaX(15);

        }

        //taking care of y coordinates
        if (pnt.y < 5) {
            Sound.playSound("CarDoor.wav");
            setCenter(new Point(pnt.x, 6));
            this.setDeltaY(15.0);

        } else if (pnt.y >= 575) {
            //if the ball is going down, this fixes it
            Sound.playSound("CarDoor.wav");
            setCenter(new Point(pnt.x, 574));
            this.setDeltaY(-15.0);

        } else {
            //	System.out.println("Everything is working fine ");
            setCenter(new Point((int) dX, (int) dY));
        }
    }

    public void draw(Graphics g) {
        Point pnt = getCenter();

        g.setColor(new Color(255, 213, 0));
        g.drawOval((int) pnt.getX(), (int) pnt.getY(), 5, 5);
        g.fillOval((int) pnt.getX(), (int) pnt.getY(), 5, 5);
    }

}