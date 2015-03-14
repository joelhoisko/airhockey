package airhockey;

import java.awt.Rectangle;
import java.io.Serializable;

/**
 * Goal holds all the information of a goal object. It is also able to detect if a goal has been made.
 * @author Markus
 *
 */
public class Goal implements Serializable {
	private int xpos;
	private int ypos;
	private int width;
	private int height;
	private int goalId;
	private Rectangle rectangle;
	private static final long serialVersionUID = 3L;
	
	public Goal(int x, int y, int w, int h, int id) {
		xpos = x;
		ypos = y;
		width = w;
		height = h;
		goalId = id;
		rectangle = new Rectangle(xpos, ypos, width, height);
	}
	/**
	 * Detects a goal. For detection the method uses rectangles. Basically it detects if puck's and goal's 
	 * rectangles intersect.
	 * @param puck
	 * @return true if a goal has been made
	 */
	public boolean detectGoal(Puck puck) {
		if (puck.getSurroundingRectangle().intersects(rectangle)) {
			return true;
		}
		return false;
	}

	/// Setters and getters///

	/**
	 * Returns the xpos of the goal.
	 * @return
	 */
	public int getXpos() {
		return xpos;
	}
	/**
	 * Sets the xpos of the goal.
	 * @param xpos
	 */
	public void setXpos(int xpos) {
		this.xpos = xpos;
	}
	/**
	 * Returns the ypos of the goal.
	 * @return
	 */
	public int getYpos() {
		return ypos;
	}
	/**
	 * Sets the ypos of the goal.
	 * @param ypos
	 */
	public void setYpos(int ypos) {
		this.ypos = ypos;
	}
	/**
	 * Returns the width of the goal.
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Sets the width of the goal.
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * Returns the height of the goal.
	 * @return
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Sets the height of the goal.
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * Returns the goalID.
	 * @return
	 */
	public int getGoalId() {
		return goalId;
	}
	/**
	 * Sets the goalID.
	 * @param goalId
	 */
	public void setGoalId(int goalId) {
		this.goalId = goalId;
	}
}