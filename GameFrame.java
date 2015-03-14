package airhockey;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 * Handles the JFrame, calls for the Painter to fill it with JPanel, handles the inner game logic and
 * calls to the game objects(puck, mallet) and the running of the ActionListener.
 * @author joel
 *
 */
public class GameFrame extends JFrame implements ActionListener {
	// Don't ask, has something to do with JFrame/JPanel.
	private static final long serialVersionUID = 1L;

	private Puck puck;
	private Painter painter;
	private Mallet[] mallet = new Mallet[2];

	private CollisionDetector collisionDetector;
	private boolean isColliding = false;

	private int malletMaxPositive = 3;
	private int malletMaxNegative = -3;

	private Goal goalTop;
	private Goal goalBottom;

	private Player player1;
	private Player player2;

	private Airinterface1 airinterface; // now Airinterface methods are working and usable.
	private int sendId;

	private long timeElapsed;
	private Timer t;


	/**
	 * Sets the style of the JFrame window borders and calls for the initialize()-method.
	 * Now the constructor for GameFrame acts also as a setup procedure for our rmi client, which now is our game frame. 
	 * Methods from Gamecontroller should be added to GameFrame.
	 */
	public GameFrame(Airinterface1 airinterface, int sendId) {
		this.sendId = sendId;
		this.airinterface = airinterface;
		try {
			// Sets the window borders according to the style of the OS.
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		initialize();
	}

	/**
	 * Initializes the game objects and calls for the Painter to create a JPanel.
	 * Also contains the ActionListener and pretty much all of the game logic 
	 * or calls to the methods which alter the actual game.
	 */
	public void initialize() {

		// Initializes the puck object with the speed of 3 pixels per tick in both x- and y-axis and positions it in the middle of the game arena.
		puck = new Puck(0, 0, 30, 215, 320);

		// Creates 2 new Mallet objects into the list with the predetermined coordinates (top and bottom) and radius.
		// The second mallet(mallet[1]) is the one we will be controlling.

		mallet[0] = new Mallet(190, 20, 100, 20);
		mallet[0].setMalletTag(0);


		mallet[1] = new Mallet(190, 660, 100, 20);
		mallet[1].setMalletTag(1);

		goalTop = new Goal(210, 0, 60, 1, 1);
		goalBottom = new Goal(210, 699, 60, 1, 2);


		if (sendId == 1) {
			player1 = new Player("Player" + "" + sendId + "", sendId);
			player2 = new Player("Player 2", 2);
		}
		if (sendId == 2) {
			player2 = new Player("Player" + "" + sendId + "", sendId);
			player1 = new Player("Player 1", 1);
		}
		player1.setScore(0);
		player2.setScore(0);

		// collisionDetector is used to check if a collision happened between the mallet and the puck.
		collisionDetector = new CollisionDetector(puck, mallet);
		// according to my probably broken logic the puck should move to opposite direction in two different games that are connected to each other...

		puck.setPuckSpeedX(2);
		puck.setPuckSpeedY(1);


		// Gives the painter the puck, mallet, goals and player objects and 
		// runs the painters constructor which creates the JPanel.
		painter = new Painter(puck, mallet, goalTop, goalBottom, player1, player2);


		// We use the keyboard to control our mallet.
		KeyListener keyListener = new KeyListener() {

			/**
			 * Moves the mallet according to the presses.
			 */
			public void keyPressed(KeyEvent e) {
				// Reads the pressed key.
				if (sendId == 1) {
					int keyCode = e.getKeyCode();

					if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
						mallet[1].setMalletSpeedX(malletMaxNegative);
					}
					if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
						mallet[1].setMalletSpeedX(malletMaxPositive);
					}
					if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
						mallet[1].setMalletSpeedY(malletMaxNegative);
					}
					if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
						mallet[1].setMalletSpeedY(malletMaxPositive);
					}
				}
				if (sendId==2) {
					int keyCode = e.getKeyCode();

					if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
						mallet[0].setMalletSpeedX(malletMaxNegative);
					}
					if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
						mallet[0].setMalletSpeedX(malletMaxPositive);
					}
					if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
						mallet[0].setMalletSpeedY(malletMaxNegative);
					}
					if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
						mallet[0].setMalletSpeedY(malletMaxPositive);
					}
				}
			} // keyPressed

			/**
			 * Stops the mallet when the button is released.
			 */
			public void keyReleased(KeyEvent e) {
				if (sendId == 1) {
					int keyCode = e.getKeyCode();

					if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
						mallet[1].setMalletSpeedX(0);
					}
					if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
						mallet[1].setMalletSpeedX(0);
					}
					if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
						mallet[1].setMalletSpeedY(0);
					}
					if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
						mallet[1].setMalletSpeedY(0);
					}
				}
				if (sendId == 2) {
					int keyCode = e.getKeyCode();

					if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
						mallet[0].setMalletSpeedX(0);
					}
					if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
						mallet[0].setMalletSpeedX(0);
					}
					if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
						mallet[0].setMalletSpeedY(0);
					}
					if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
						mallet[0].setMalletSpeedY(0);
					}
				}
			} // keyReleased

			/**
			 * I have no idea.
			 */
			public void keyTyped(KeyEvent e) {}
		}; // KeyListener d

		// The Painter gets added to the JFrame so that it can start drawing the JPanel.
		add(painter);
		addKeyListener(keyListener); // adding the Key listener to the JPanel;
		// Sets the size of the window according to the JPanel dimensions.
		pack();

		setVisible(true);
		setFocusable(true);
		setTitle("" + timeElapsed + "");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		/**
		 * We create a new ActionListener to do stuff inside the JPanel.
		 * This contains all of the inner game logic.
		 */
		ActionListener aListener = new ActionListener() {

			// resetTimer keeps track of the ingame ticks and is used to reset the game if nothing happens for a while.
			int resetTimer = 0;

			/**
			 * actionPerfomed handles the game logic and calls for the Painter to repaint everything once it's done
			 * moving the puck/mallets/etc.
			 */
			public void actionPerformed(ActionEvent e) {

				resetTimer++;
				try {
					if (airinterface.winner(sendId) == 1) {
						t.stop();

						JOptionPane.showMessageDialog(null, "YOU ARE WINNER!");
						JOptionPane.showMessageDialog(null, "The game will now shut down.");

						Thread.sleep(200);
						setVisible(false);
						System.exit(0);
					}
					if (airinterface.winner(sendId) == -1) {
						t.stop();

						JOptionPane.showMessageDialog(null,"YOU LOST!");
						JOptionPane.showMessageDialog(null, "The game will now shut down.");

						Thread.sleep(200);
						setVisible(false);
						System.exit(0);
					}
				} catch (RemoteException g) {
					g.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				// puckMovement handles the movement of the puck.
				puckMovement();
				// Restricts the mallet's movement inside the playing field.
				malletInTheArea();

				if (sendId == 1) {
					try {
						airinterface.sendPuckPos(puck.getPuckX(),puck.getPuckY());
					}catch(RemoteException e3){
						e3.printStackTrace();
					}
				}




				try {
					// sendId tells the server which variables to use for sending the coordinates
					if (sendId == 1) {
						airinterface.sendMalletPos(mallet[1].getMalletX(), mallet[1].getMalletY(), sendId);

					}
					if (sendId == 2) {
						airinterface.sendMalletPos(mallet[0].getMalletX(), mallet[0].getMalletY(), sendId);

					}

				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				// bouncing
				isColliding = collisionDetector.doesItCollide();  
				if (isColliding == true) {

					int x =	puck.getHitState(); 


					bounce(x);


					puck.checkPuckSpeedXY();
					puckMovement();
					isColliding = false;
					resetTimer = 0;
				}


				if (goalTop.detectGoal(puck)) {
					if (sendId == 1) {// upper goal
						try {

							airinterface.sendScore(1, 2); // sends player2 score to the server.

						} catch(RemoteException e1) {
							e1.printStackTrace();
						}
						newRound(1);
						resetTimer = 0;
					}
				}

				if (goalBottom.detectGoal(puck)) { // lower goal
					if (sendId == 1) {
						try {
							airinterface.sendScore(1, 1); // sends player1 score to the server.

						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
						newRound(2);
						resetTimer = 0;
					}
				}

				// If nothing happens for 10 seconds(2000 x 5ms), the game gets reset in favor of the bottom player.
				if (resetTimer > 2000) {
					newRound(1);
				}

				mallet[1].moveMallet(mallet[1].getMalletX() + mallet[1].getMalletSpeedX(), mallet[1].getMalletY() + mallet[1].getMalletSpeedY());
				mallet[0].moveMallet(mallet[0].getMalletX() + mallet[0].getMalletSpeedX(), mallet[0].getMalletY() + mallet[0].getMalletSpeedY());
				// Prints the speed of our mallet(keypresses) to the title of the window. Used for testing purposes.
				if (sendId == 2){
					try {
						puck.setPuckX(airinterface.getPuckX());
						puck.setPuckY(airinterface.getPuckY());
					} catch (RemoteException f) {
						f.printStackTrace();
					}
				}
				try {
					// sendId tells the server which variables to use for getting the coordinates
					if (sendId == 1) {

						mallet[0].setMalletX(airinterface.getMallet2X());
						mallet[0].setMalletY(airinterface.getMallet2Y());
					}
					if (sendId == 2) {

						mallet[1].setMalletX(airinterface.getMallet1X());
						mallet[1].setMalletY(airinterface.getMallet1Y());
					}

				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				try {
					player2.setScore(airinterface.getScore(2)); // updates  scores from the server to local game
					player1.setScore(airinterface.getScore(1));
				} catch (RemoteException ef) {
					ef.printStackTrace();
				}
				painter.paintAll();



			} // actionPerformed
		}; // aListener

		// Timer defines in milliseconds how often the ActionListener is run.
		t = new Timer(10, aListener);
		t.start();

	} // initialize()



	/**
	 * Handles the movement of the puck. 
	 * If the puck comes into a contact with a wall/floor/ceiling, it will bounce off from it.
	 */
	public void puckMovement() {
		// Puck hits the  left wall.
		if (puck.getPuckX() < 0) {
			puck.invertPuckSpeedX();

		}
		// Puck hits the right wall..
		if (puck.getPuckX() > 460) {
			puck.invertPuckSpeedX();
		}
		// Puck hits the ceiling.
		if (puck.getPuckY() < 0) {
			puck.invertPuckSpeedY();
		}
		// Puck hits the floor.
		if (puck.getPuckY() > 660) {
			puck.invertPuckSpeedY();
		}
		// if the lower mallet hit the puck, we send these puck coordinates to the server for the other game to pick

		// Puck is moved from the original coordinates to the new ones (old coordinates + the speed). If the puck hitstate == sendId, we use the local puck speed and local puck coordinates.

		puck.movePuck(puck.getPuckX() + puck.getPuckSpeedX(), puck.getPuckY() + puck.getPuckSpeedY());

	} // puckMovement()

	/**
	 * Bounces the puck according to its relative position from the mallet.
	 * Uses methods from CollisionDetector to calculate the puck's position relative to the mallet. This method also sends  the puck coordinates and 
	 * speed to the server, as the method bounce is the turning point of the coordidate and speed stream from one game to another
	 */
	public void bounce(int y) {
		int x = y;
		// If the mallet is moving  and the puck is not.
		if (mallet[x].isStill() == false && puck.isStill()) {

			// at first, the puck is above the mallet.
			if (collisionDetector.getRelationY(x) == "above") {
				puck.setPuckSpeedY(puck.getPuckSpeedY() + mallet[x].getMalletSpeedY()); 
			}
			// puck might also stop moving below the mallet.
			else if (collisionDetector.getRelationY(x) == "under") {
				puck.setPuckSpeedY(puck.getPuckSpeedY() + mallet[x].getMalletSpeedY()); 
			}
			else if (collisionDetector.getRelationX(x) == " left") {
				puck.setPuckSpeedX(puck.getPuckSpeedX() + mallet[x].getMalletSpeedX()); 
			}
			else if (collisionDetector.getRelationX(x) == "right") {
				puck.setPuckSpeedX(puck.getPuckSpeedX() + mallet[x].getMalletSpeedX()); 
			}
			puck.movePuck(puck.getPuckX() + puck.getPuckSpeedX(), puck.getPuckY() + puck.getPuckSpeedY());


		}
		// If mallet is not moving and puck is. 
		if (mallet[x].isStill() && !puck.isStill()) {
			if (collisionDetector.getRelationY(x) == "above") {
				puck.invertPuckSpeedY();
			}
			else if (collisionDetector.getRelationX(x) == "right") {
				puck.invertPuckSpeedX();
			}
			else if (collisionDetector.getRelationY(x) == "under") {
				puck.invertPuckSpeedY();
			}
			else if (collisionDetector.getRelationX(x) == "left") {
				puck.invertPuckSpeedX();
			}
			puck.movePuck(puck.getPuckX() + puck.getPuckSpeedX(), puck.getPuckY() + puck.getPuckSpeedY());


		}
		// if both are moving
		if (!mallet[x].isStill() && !puck.isStill()) {
			if (collisionDetector.getRelationY(x) == "above") {
				puck.invertPuckSpeedY();
				puck.setPuckSpeedY(puck.getPuckSpeedY() + mallet[x].getMalletSpeedY()); 
			}
			if (collisionDetector.getRelationY(x) == "under") {
				puck.invertPuckSpeedY();
				puck.setPuckSpeedY(puck.getPuckSpeedY() + mallet[x].getMalletSpeedY()); 
			}
			if (collisionDetector.getRelationX(x) == "right") {
				puck.invertPuckSpeedX();
				puck.setPuckSpeedX(puck.getPuckSpeedX() + mallet[x].getMalletSpeedX()); 
			}
			if (collisionDetector.getRelationX(x) == "left") {
				puck.invertPuckSpeedX();
				puck.setPuckSpeedX(puck.getPuckSpeedX() + mallet[x].getMalletSpeedX()); 
			}
			puck.movePuck(puck.getPuckX() + puck.getPuckSpeedX(), puck.getPuckY() + puck.getPuckSpeedY());

		}
	} // bounce()

	/**
	 * Restricts the mallet's movement inside the playing field and the players side of the field.
	 */
	public void malletInTheArea() {
		if (mallet[1].getMalletX() < 0) {
			mallet[1].setMalletSpeedX(0);
			mallet[1].setMalletX(0);
		}
		if (mallet[1].getMalletX() + mallet[1].getMalletWidth() > 500) {
			mallet[1].setMalletSpeedX(0);
			mallet[1].setMalletX(500-mallet[1].getMalletWidth());
		}
		if (mallet[1].getMalletY() < 350) {
			mallet[1].setMalletSpeedY(0);
			mallet[1].setMalletY(350);
		}
		if (mallet[1].getMalletY() + mallet[1].getMalletHeight() > 700) {
			mallet[1].setMalletSpeedY(0);
			mallet[1].setMalletY(700-mallet[1].getMalletHeight());
		}


		if (mallet[0].getMalletX() < 0) {
			mallet[0].setMalletSpeedX(0);
			mallet[0].setMalletX(0);
		}
		if (mallet[0].getMalletX() + mallet[1].getMalletWidth() > 500) {
			mallet[0].setMalletSpeedX(0);
			mallet[0].setMalletX(500-mallet[1].getMalletWidth());
		}
		if (mallet[0].getMalletY() + 20 > 350) {
			mallet[0].setMalletSpeedY(0);
			mallet[0].setMalletY(330);
		}
		if (mallet[0].getMalletY() < 0) {
			mallet[0].setMalletSpeedY(0);
			mallet[0].setMalletY(0);
		}
	}

	/**
	 * Resets the playing field, all mallets return to their original coordinates and the puck 
	 * gets placed to the center of the field, the parameter "winner" is used to determine who gets to hit the puck first.
	 * @param winner
	 */
	public void newRound(int winner) {
		if (winner == 1) {
			puck.setPuckX(215);
			puck.setPuckY(320); // in the final game this value should be 290
			puck.setPuckSpeedX(-2);
			puck.setPuckSpeedY(-1);
			mallet[1].setMalletX(190);
			mallet[1].setMalletY(660);
			mallet[0].setMalletX(190);
			mallet[0].setMalletY(20);
		}

		if (winner == 2) {
			puck.setPuckX(215);
			puck.setPuckY(320); // in the final game 350
			puck.setPuckSpeedX(2);
			puck.setPuckSpeedY(1);
			mallet[1].setMalletX(190);
			mallet[1].setMalletY(660);
			mallet[0].setMalletX(190);
			mallet[0].setMalletY(20);
		} // newRound
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}
}// GameFrame