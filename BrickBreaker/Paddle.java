//package dfinal.game.model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dsvid88 on 5/19/15.
 * <p>
 * //This extends movable Object
 */

public class Paddle extends movableObject {

	// ==============================================================
	// FIELDS
	// ==============================================================
	private boolean paddleMoveLeft = false;
	private boolean paddleMoveRight = false;
	// ==============================================================
	// CONSTRUCTOR
	// ==============================================================

	public Paddle() {
		super();

		ArrayList<Point> pntCs = new ArrayList<Point>();

		//right points
		pntCs.add(new Point(1, 5));
		pntCs.add(new Point(0, 5));
		pntCs.add(new Point(0, 0));
		pntCs.add(new Point(1, 0));

		assignPolarPoints(pntCs);

		setColor(Color.white);

		//put paddle in the middle.
		setCenter(new Point(500, 575));

		//this is the size of the paddle
		setRadius(70);

	}

	// ==============================================================
	// METHODS
	// ==============================================================

	public void move() {

		Point pnt = getCenter();
		double dX = pnt.x + getDeltaX();

		//this just keeps the paddle inside the bounds of the frame
		if (pnt.x > getDim().width) {
			setCenter(new Point(650, pnt.y));

		} else if (pnt.x < 0) {
			setCenter(new Point(0, pnt.y));
		} else {
			setCenter(new Point((int) dX, pnt.y));
		}

		if (paddleMoveLeft) {
			setDeltaX(getDeltaX() - 10);
		}

		if (paddleMoveRight) {
			setDeltaX(getDeltaX() + 10);
		}

		if (!paddleMoveLeft && !paddleMoveRight) {
			setDeltaX(0);
		}

	} //end move


	public void moveLeft() {
		paddleMoveLeft = true;
	}

	public void moveRight() {
		paddleMoveRight = true;
	}

	public void stopLeft() {
		paddleMoveLeft = false;
	}

	public void stopRight() {
		paddleMoveRight = false;
	}


	public void draw(Graphics g) {

		Color colPaddle = new Color(255, 255, 255);
		drawPaddleWithColor(g, colPaddle);
	} //end for loop*/


	public void drawPaddleWithColor(Graphics g, Color col) {
		super.draw(g);
		g.setColor(col);

		g.drawPolygon(getXcoords(), getYcoords(), dDegrees.length);
		g.setColor(new Color(255, 255, 255));
		g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
	}

} //end class
