package airhockey;

import java.awt.Rectangle;
import java.io.Serializable;

/**
 * Puck handle the coordinates and speed of the puck.
 * @author joel
 *
 */

// TO-DO 
// Puck no longer a circle, remove unnecessary methods and attributes.
public class Puck implements Serializable {

	private int puckX;
	private int puckY;

	private int puckSpeedX;
	private int puckSpeedY;

	private final int puckPositiveMaxSpeedX = 5;
	private final int puckPositiveMaxSpeedY = 5;

	private final int puckNegativeMaxSpeedX = -5;
	private final int puckNegativeMaxSpeedY = -5;

	private int puckRadius;

	private double[] puckCenter = new double[2];
	private double[] puckCircleXList;
	private double[] puckCircleYList;

	private Rectangle surroundingRectangle;

	private int hitState;
	private static final long serialVersionUID = 2L;

	/**
	 * Initializes the speed of the puck with the given parameteres.
	 * @param xSpeed
	 * @param ySpeed
	 */
	public Puck(int xSpeed, int ySpeed, int radius, int x, int y) {
		this.puckSpeedX = xSpeed;
		this.puckSpeedY = ySpeed;
		this.puckRadius = radius;
		puckX = x;
		puckY = y;
		surroundingRectangle = new Rectangle(puckX,puckY,2*puckRadius,2*puckRadius); // Rectangle that surrounds the puck
	}
	/**
	 * sets the hit state. Hit state tells which mallet hit the puck.
	 */
	public void setHitState(int hitState){
		this.hitState = hitState;
	}
	/**
	 * gets the hit  state of the puck.
	 */
	public int getHitState(){
		return hitState;
	}

	/**
	 * Moves the puck to the given coordinates.
	 * @param x
	 * @param y
	 */
	public void movePuck(int x, int y) {
		this.puckX = x;
		this.puckY = y;
		surroundingRectangle.setLocation(x, y); // In addition to the visible puck movement, the rectangle has to be moved separately
	}

	/**
	 * returns true, if mallets x and y velocities are 0
	 */
	public boolean isStill(){
		if(puckSpeedX == 0 && puckSpeedY == 0){
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @return rectangle that surrounds the puck.
	 */
	public Rectangle getSurroundingRectangle(){ 
		return surroundingRectangle;
	}

	/**
	 * Inverts the puck's speed along the x-axis.
	 */
	public void invertPuckSpeedX() {
		puckSpeedX = puckSpeedX* - 1;
	}


	/**
	 * Inverts the puck's speed along the y-axis.
	 */
	public void invertPuckSpeedY() {
		puckSpeedY = puckSpeedY* - 1;
	}

	/**
	 * Returns the value of the puck's x-coordinate.
	 * @return
	 */
	public int getPuckX() {
		return puckX;
	}

	/**
	 * Sets the value of the puck's x-coordinate.
	 * @param x
	 */
	public void setPuckX(int x) {
		this.puckX = x;
	}

	/**
	 * Returns the value of the puck's y-coordinate.
	 * @return
	 */
	public int getPuckY() {
		return puckY;
	}

	/**
	 * Sets the value of the pucks's y-coordinate.
	 * @param y
	 */
	public void setPuckY(int y) {
		this.puckY = y;
	}

	/**
	 * Returns the spuck's speed along the x-axis.
	 * 
	 * @return
	 */
	public int getPuckSpeedX() {
		return puckSpeedX;
	}

	/**
	 * Sets the puck's speed along the x-axis.
	 * @param dx
	 */
	public void setPuckSpeedX(int dx) {

		this.puckSpeedX = dx;

	}

	/**
	 * Makes it so, that the puck's maximum speed can't be greater than the respective puckMaxSpeed in that direction.
	 */
	public void checkPuckSpeedXY() {
		if (puckSpeedX > puckPositiveMaxSpeedX) {
			puckSpeedX = puckPositiveMaxSpeedX;
		}
		if (puckSpeedY > puckPositiveMaxSpeedY) {
			puckSpeedY = puckPositiveMaxSpeedY;
		}
		if (puckSpeedX < puckNegativeMaxSpeedX) {
			puckSpeedX = puckNegativeMaxSpeedX;
		}
		if (puckSpeedY < puckNegativeMaxSpeedY) {
			puckSpeedY = puckNegativeMaxSpeedY;
		}
	}

	/**
	 * Returns the puck's speed along the y-axis.
	 * @return
	 */
	public int getPuckSpeedY() {
		return puckSpeedY;
	}

	/**
	 * Sets the puck's speed along the y-axis.
	 * @param dy
	 */
	public void setPuckSpeedY(int dy) {


		this.puckSpeedY = dy;

	}

	/**
	 * Returns the radius of the puck.
	 * @return
	 */
	public int getPuckRadius() {
		return puckRadius;
	}

	/**
	 * Returns the value of puck's center along the x-axis with the parameter "0" 
	 * and the value of y with "1".
	 * @param x
	 * @return
	 */
	public double getPuckCenter(int x) {
		puckCenter[0] = puckX + puckRadius;
		puckCenter[1] = puckY + puckRadius;
		return puckCenter[x];
	}

	/**
	 * Calculates 360 different coordinates(full circle) along the circle of the puck.
	 * These values are stored to their respective lists.
	 */
	public void calculatePuckCircleXYList() {
		puckCircleXList = new double[360];
		puckCircleYList = new double[360];
		for (int i = 0; i < 360; i++) {
			puckCircleXList[i] = puckCenter[0] + puckRadius * Math.cos(i * Math.PI / 180);
			puckCircleYList[i] = puckCenter[1] + puckRadius * Math.sin(i * Math.PI / 180);
		}
	}

	/**
	 * Returns a specified member of the puckCircleXList.
	 * @param x
	 * @return
	 */
	public double getPuckCircleX(int x) {
		return puckCircleXList[x];
	}

	/**
	 * Returns a specified member of the puckCircleYList.
	 * @param x
	 * @return
	 */
	public double getPuckCircleY(int x) {
		return puckCircleYList[x];
	}
}