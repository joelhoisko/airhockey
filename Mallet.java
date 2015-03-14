package airhockey;

import java.awt.Rectangle;
import java.io.Serializable;

/**
 * Changed the malletRadius attribute to malletWidth and malletHeight attributes. And I changed
 * the constructor for the mallets to include these attributes.
 * - Sauli
 */
/**
 * Mallet handles the coordinates and speed of a single mallet and the change of these values.
 * @author joel
 *
 */
public class Mallet implements Serializable {

	private static final long serialVersionUID = 1L;

	private int malletX;
	private int malletY;

	private int malletSpeedX;
	private int malletSpeedY;

	private int malletWidth;
	private int malletHeight;
	private Rectangle bounds;

	private int malletTag;

	/**
	 * Initialize the position of and height/width the mallet according to the given parameters.
	 */
	public Mallet(int x, int y, int width, int height) {
		malletX = x; // Default for the first mallet is (240, 0) and for the second it's (240, 620);
		malletY = y;
		malletWidth = width;
		malletHeight = height;
		bounds = new Rectangle(malletX, malletY, malletWidth, malletHeight); // rectangle that surrounds the mallet
	}

	/**
	 * Sets an identification tag for a mallet.
	 */
	public void setMalletTag(int malletTag) {
		this.malletTag = malletTag;
	}

	/**
	 * Gets the id Tag
	 */
	public int getMalletTag() {
		return malletTag;
	}

	/**
	 * Sets the mallets and its rectangles coordinates.  
	 * @param x
	 * @param y
	 */
	// Calculating the speed of the mallet is now irrelevant as the speed in the area of key listener is constant. 
	public void moveMallet(int x, int y) {
		malletX = x;
		malletY = y;
		bounds.setLocation(malletX, malletY); // surrounding rectangle has to be moved separatedly.
	}

	/**
	 * Returns the mallet's coordinate along the x-axis.
	 * @return
	 */
	public int getMalletX() {
		return malletX;
	}

	/**
	 * Sets the mallets x-coordinate.
	 * @param x
	 */
	public void setMalletX(int x) {
		malletX = x;
	}

	/**
	 * Returns the mallet's coordinate along the y-axis.
	 * @return
	 */
	public int getMalletY() {
		return malletY;
	}

	/**
	 * Sets the mallets y-coordinate.
	 * @param y
	 */
	public void setMalletY(int y) {
		malletY = y;
	}

	/**
	 * Returns the mallets speed along the x-axis.
	 * @return
	 */
	public int getMalletSpeedX() {
		return malletSpeedX;
	}

	/**
	 * Sets the mallets speed along the x-axis.
	 * Might be deprecated, as the moveMallet() already calculates the speed.
	 * @param malletSpeedX
	 */
	public void setMalletSpeedX(int malletSpeedX) {
		this.malletSpeedX = malletSpeedX;
	}

	/**
	 * Returns the mallets speed along the y-axis.
	 * @return
	 */
	public int getMalletSpeedY() {
		return malletSpeedY;
	}

	/**
	 * Sets the mallets speed along the y-axis.
	 * Might be deprecated as the moveMallet() already calculates the speed.
	 * @param malletSpeedY
	 */
	public void setMalletSpeedY(int malletSpeedY) {
		this.malletSpeedY = malletSpeedY;
	}

	public int getMalletWidth() {
		return malletWidth;
	}

	public int getMalletHeight() {
		return malletHeight;
	}
	/**
	 * Returns a rectangle same size as the mallet
	 * @return Rectangle that surrounds the mallet
	 */

	public Rectangle getBounds(){ 
		return bounds;
	}

	/**
	 * returns true if mallet is still
	 */
	public boolean isStill(){
		if (malletSpeedX == 0 && malletSpeedY == 0) {
			return true;
		}
		return false;
	}
}