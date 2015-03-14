package airhockey;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
// this class will be used by our RMI client.

/**
 * The player will call methods from this class to get the needed information to draw the opponent,
 * check the scores and the server uses this class to check that both the players are 
 * connected to the game before starting it.
 * @author joel
 *
 */
public class Gamecontroller extends UnicastRemoteObject implements Airinterface1 {

	private static final long serialVersionUID = 1L;
	private int puckX;
	private int puckY;

	private int mallet1X;
	private int mallet1Y;

	private int mallet2X;
	private int mallet2Y;

	private int[] game = new int[2];

	private int scorePlayer1;
	private int scorePlayer2;

	private int puckHitstate;

	private int puckXspeed;
	private int puckYspeed;

	public Gamecontroller() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Checks if the puck is in a goal and gives the respective player a point.
	 */
	@Override
	public void setScore(Goal g1, Goal g2, Player p1, Player p2, Puck puck) throws RemoteException {
		if (g1.detectGoal(puck)) {
			p2.setScore(p2.getScore()+1);
		}
		if (g2.detectGoal(puck)) {
			p1.setScore(p1.getScore()+1);
		}
	}

	/**
	 * Changes the puck's X- and Y-coordinates to match the given arguments.
	 */
	@Override
	public synchronized void sendPuckPos(int x, int y) throws RemoteException {
		puckX = x;
		puckY = y;
		notifyAll();

	}

	/**
	 * Changes the position of a mallet to the given coordinates. The correct mallet is identified by the id-parameter.
	 */
	public void sendMalletPos(int x, int y, int id) throws RemoteException {
		if (id == 1) {
			mallet1X = x;
			mallet1Y = y;
		}
		if (id == 2) {
			mallet2X = x;
			mallet2Y = y;
		}
	}

	@Override
	public int getMallet1X() throws RemoteException {
		// TODO Auto-generated method stub
		return mallet1X;
	}

	@Override
	public int getMallet1Y() throws RemoteException {
		// TODO Auto-generated method stub
		return mallet1Y;
	}

	@Override
	public int getMallet2X() throws RemoteException {
		// TODO Auto-generated method stub
		return mallet2X;
	}

	@Override
	public int getMallet2Y() throws RemoteException {
		// TODO Auto-generated method stub
		return mallet2Y;
	}

	@Override
	public synchronized int getPuckX() throws RemoteException {
		// TODO Auto-generated method stub
		return puckX;
	}

	@Override
	public synchronized int getPuckY() throws RemoteException {
		// TODO Auto-generated method stub
		return puckY;
	}

	@Override
	public  int reservePosition() throws RemoteException {
		if (game[0] == 0) {
			game[0] = 1;
			return 1; // this is returned to tell the client to use Mallet1X and Mallet1Y for sending
			// mallet coordinates and to read coordinates from Mallet2X and Mallet2Y.
		}
		else {
			game[1] = 1;
			return 2; // Same as before but reversed.
		}
	}

	/**
	 * Checks if there are 2 players connected to the game, returns true if true.
	 */
	@Override
	public boolean initGame() throws RemoteException {
		if (game[0] == 1 && game[1] == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Resets the game[]-list values to 0.
	 */
	@Override
	public void emptyReservations() throws RemoteException {
		// TODO Auto-generated method stub
		game[0] = 0;
		game[1] = 0;
	}

	@Override
	public void sendScore(int score, int id) throws RemoteException {
		if(id == 1){
			scorePlayer1 = scorePlayer1 + score;
		}
		if(id == 2){
			scorePlayer2 = scorePlayer2 + score;
		}
	}

	/**
	 * Returns the score of a player(id).
	 */
	@Override
	public int getScore(int id) throws RemoteException {
		int score = 0;
		if (id == 1) {
			score = scorePlayer1;
		}
		if (id == 2) {
			score = scorePlayer2;
		}
		return score;
	}

	/**
	 * Returns the other players score.
	 */
	@Override
	public int getRivalScore(int id) throws RemoteException {
		int rivalscore =0;
		if (id == 1){rivalscore = scorePlayer2;}
		if (id == 2){rivalscore = scorePlayer1;}
		return rivalscore;
	}

	@Override
	public void sendPuckHitState(int state) throws RemoteException {
		puckHitstate = state;

	}

	@Override
	public int getPuckHitState() throws RemoteException {

		return puckHitstate;
	}

	@Override
	public void sendPuckSpeedX(int speed) throws RemoteException {
		// TODO Auto-generated method stub
		puckXspeed = speed;
	}

	@Override
	public void sendPuckSpeedY(int speed) throws RemoteException {
		// TODO Auto-generated method stub
		puckYspeed = speed;

	}

	@Override
	public int getPuckSpeedX() throws RemoteException {
		// TODO Auto-generated method stub
		return puckXspeed;
	}

	@Override
	public int getPuckSpeedY() throws RemoteException {
		// TODO Auto-generated method stub
		return puckYspeed ;
	}

	@Override
	public int winner(int id) throws RemoteException {
		int information = 0;
		if (id == 1){
			if(scorePlayer1 == 3){
				information = -1;
				return information;
			}
			if(scorePlayer2 == 3){
				information = 1;
				return information;
			}
		}
		if (id == 2) {
			if (scorePlayer2 == 3) {
				information = -1;
				return information;
			}
			if (scorePlayer1 == 3) {
				information = 1;
				return information;
			}
		}
		return information;
	}

	@Override
	public void shutDownServer() throws RemoteException {
		// TODO Auto-generated method stub
		System.exit(0);

	}
}