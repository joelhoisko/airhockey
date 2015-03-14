package airhockey;

import java.awt.Rectangle;

/**
 * I commented out every collision related stuff because they need to be rewritten.
 * I also wondered if we should change the puck to be a square since checking the collision would be
 * so much easier. I've been wondering this collision stuff for a while and can't figure it out. There's some
 * kind of a rectangle- and circle-classes in swing/awt/whatever that have some sorts of methods like intersects()
 * that would be helpful.. but i dunno..
 * - Sauli
 */
/**
 * Detects collisions of the mallet and the puck.
 * @author joel
 *
 */
public class CollisionDetector {
	private Puck puck;
	private Mallet[] mallet;

	/**
	 * Takes use of the puck and mallet objects to check their values.
	 * @param puck
	 * @param mallet
	 */
	public CollisionDetector(Puck puck, Mallet[] mallet) {
		this.puck = puck;
		this.mallet = mallet;
	}
	//this constructor is for test use.
	public CollisionDetector(){}

	/**
	 * If the puck and mallet collided, return true, otherwise false.
	 * They collide if the surrounding rectangles of the mallet and puck intersect.
	 * Meaning that they touched.
	 * @return
	 */
	public boolean doesItCollide() {
		Rectangle malletRectangle = mallet[1].getBounds();
		Rectangle mallet2Rectangle = mallet[0].getBounds();
		Rectangle puckRectangle = puck.getSurroundingRectangle();
		if (malletRectangle.intersects(puckRectangle)) {
			puck.setHitState(mallet[1].getMalletTag());
			return true;
		}
		if(mallet2Rectangle.intersects(puckRectangle)){
			puck.setHitState(mallet[0].getMalletTag());
			return true;
		}
		return false;
	}

	/**
	 * this method finds whether the puck is above or below the mallet.
	 * @returns true if puck is above mallet. else the return value is false.
	 * 
	 */
	public String getRelationY(int y){
		int x = y;
		String positionY;
		if(mallet[x].getMalletY() > puck.getPuckY() + 30){
			positionY = "above";
			return positionY;
		}
		if(mallet[x].getMalletY() + 20 < puck.getPuckY()+30){
			positionY = "under";
			return positionY;
		}
		positionY = "between";
		return positionY;

	}
	/**
	 * Determines the side on which the puck is located in relation to the mallet.
	 * @returns true, if puck is on the right side of the mallet 
	 */
	public String getRelationX(int y){
		int x =y;
		String positionX;
		if(mallet[x].getMalletX() + 100 < puck.getPuckX() + 25){
			positionX = "right";
			return positionX;
		}
		if(mallet[x].getMalletX() > puck.getPuckX() + 40){
			positionX = "left";
			return positionX;
		}
		positionX = "center";
		return positionX;
	}
} // CollisionDetector