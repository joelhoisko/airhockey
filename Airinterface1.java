package airhockey;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Works as an interface for Gamecontroller, contains only methods which the Gamecontroller then proceeds to use.
 * @author joel
 *
 */
/**
 * I added mirror methods also for the puck. Then there is  a send and a get  method for puck hit state. The hit state for the puck basically is  a number equal to the 
 * player sendId. Can possibly be used for synchronizing puck movement in multiple games.
 * Lastly I implemented a setters and a getters for pucks  x and y speed. 
 * @author Markus
 *
 */
public interface Airinterface1 extends Remote {

	/**
	 * Checks if the puck is in a goal and gives the respective player a point.
	 */
	public void setScore(Goal g1, Goal g2,Player p1, Player p2, Puck puck) throws RemoteException;

	/**
	 * Changes the puck's X- and Y-coordinates to match the given arguments.
	 */
	public void sendPuckPos(int x,int y) throws RemoteException;

	/**
	 * Changes the position of a mallet to the given coordinates. The correct mallet is identified by the id-parameter.
	 */
	public void sendMalletPos(int x, int y,int id) throws RemoteException;

	public int getMallet1X() throws RemoteException;
	public int getMallet1Y() throws RemoteException;

	public int getMallet2X() throws RemoteException;
	public int getMallet2Y() throws RemoteException;


	public int getPuckX() throws RemoteException;
	public int getPuckY() throws RemoteException;

	public int reservePosition() throws RemoteException;

	/**
	 * Checks if there are 2 players connected to the game, returns true if true.
	 */
	public boolean initGame()throws RemoteException;

	/**
	 * Resets the game[]-list values to 0.
	 */
	public void emptyReservations() throws RemoteException;

	public void sendScore(int score, int id) throws RemoteException;

	/**
	 * Returns the score of a player(id).
	 */
	public int getScore(int id)throws RemoteException;

	/**
	 * Returns the other players score.
	 */
	public int getRivalScore(int id)throws RemoteException;
	/**
	 * Sends the hit state of the puck to the server. Basically this variable can be used to determine whether to receive or 
	 * send puck coordinates.
	 * @param state
	 * @throws RemoteException
	 */
	public void sendPuckHitState(int state)throws RemoteException;
	/**
	 * Hit state of the puck is returned
	 * @return hit state of the puck
	 * @throws RemoteException
	 */
	public int getPuckHitState()throws RemoteException;
	/**
	 * Sends the  X speed of the puck to the server
	 * @param speed
	 * @throws RemoteException
	 */
	public void sendPuckSpeedX(int speed)throws RemoteException;
	/**
	 * Sends the Y speed of the Puck to the server;
	 * @param speed
	 * @throws RemoteException
	 */
	public void sendPuckSpeedY(int speed)throws RemoteException;
	/**
	 * Returns the X speed of the puck 
	 * @return X speed
	 * @throws RemoteException
	 */
	public int getPuckSpeedX()throws RemoteException;
	/**
	 * Returns the Y speed of the puck.
	 * @return Y speed 
	 * @throws RemoteException
	 */
	public int getPuckSpeedY()throws RemoteException;
	/**
	 * Checks if a player has 5 points. if yes then the player wins.
	 * @returns -1 if loser, 1 if winner, 0 if nothing happens.
	 */
	public int winner(int id)throws RemoteException;
	/**
	 * Shuts the server down
	 */
	public void shutDownServer()throws RemoteException;
}
