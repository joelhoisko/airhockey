package airhockey;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * I changed the mallets to rectangles and modified some methods.
 * - Sauli
 */
/**
 * I've added a new experimental method, countdown. I think it would be nice that at the beginning and after every goal there could be a countdown from 4 to 0. 
 */
/**
 * Painter handles the painting of the objects inside JPanel and repaints the panel when asked.
 * @author joel
 *
 */

public class Painter extends JPanel {
	// Don't ask. Has something to do with JFrame/JPanel.
	private static final long serialVersionUID = 1L;

	private Puck puck;
	private Mallet[] mallet;
	
	private Goal goal1;
	private Goal goal2;
	
	private Player p1;
	private Player p2;

	/**
	 * Initializes the size of the JPanel, sets its color and
	 * asks for the puck and mallet objects so that we can use their values(eg. coordinates) and methods(getters) to draw them.
	 * @param puck
	 * @param mallet
	 */
	public Painter(Puck puck, Mallet[] mallet, Goal goal1, Goal goal2, Player p1, Player p2) { 
		this.puck = puck;
		this.mallet = mallet;
		this.goal1 = goal1;
		this.goal2 = goal2;
		this.p1 = p1;
		this.p2 = p2;
		// Sets the size of the JPanel.
		setPreferredSize(new Dimension(500,700));
		// And color.
		//Color fieldColor = new Color(220, 230, 250) Slightly bluer lavender.
		//setBackground(fieldColor);
		setBackground(Color.WHITE);
	}

	/**
	 * Paints the JPanel, which includes the puck(black color), the stage lines, goals and 2 mallets(red and blue)
	 * and some other details.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		// Sets the location(x, y) and the size(x-length, y-length) of the puck.
		g.fillRect(puck.getPuckX(), puck.getPuckY(), puck.getPuckRadius() * 2, puck.getPuckRadius() * 2);

		// I've added some graphical details to the arena.	
		g.drawOval(100,200,300,300);
		g.drawLine(0, 350, 500, 350);
		
		g.drawString("Cola", 235, 250);
		g.drawString("Pepsi", 235, 450);
		g.drawString(Integer.toString(p1.getScore()), 240, 215);
		g.drawString(Integer.toString(p2.getScore()), 240, 490);
		
		// draws both the goals.
		g.setColor(Color.RED);
		g.fillRect(goal1.getXpos(), goal1.getYpos(), goal1.getWidth(), goal1.getHeight() + 5);
		g.setColor(Color.BLUE);
		g.fillRect(goal2.getXpos(), goal2.getYpos() - 5, goal2.getWidth(), goal2.getHeight() + 5);

		// Paints both the mallets from the mallet[]-list. One red, one blue.
		for (int i = 0; i < mallet.length; i++) {
			g.setColor(Color.RED);
			if (i == 1) {
				g.setColor(Color.BLUE);
			}
			// Sets the location(x, y) of the mallet[i] and the size, just like the puck.
			g.fillRect(mallet[i].getMalletX(), mallet[i].getMalletY(), mallet[i].getMalletWidth(), mallet[i].getMalletHeight());
		}
	}

	/**
	 * Calls JPanels own method, repaint(), nothing else
	 */
	public void paintAll() {
		repaint();
	}
}