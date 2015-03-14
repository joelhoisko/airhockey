package airhockey;

import java.rmi.Naming;

/**
 * Connects to the server, reads the policy-files, reserves the players a position in the game
 * and starts the game by creating a new GameFrame() after both the players have connected. 
 * Is the main()-method for the client.
 * @author joel
 *
 */
public class Airhockeygame {

	public static void main(String[] args) {

		int playerID = 0;

		System.setProperty("java.security.policy","file:./airhockey.policy");
		System.setProperty("java.net.SocketPermission", "file:./Connection.policy");
		System.out.println("Policy-files have been read.");

		//get the hostname from the internet too, it must be the hostname of the server-side machine.
		String serveraddress = "rmi://"+"192.168.43.9"+"/controller"; 
		System.out.println("Serveraddress created.");

		try {
			Airinterface1 airinterface = (Airinterface1)Naming.lookup(serveraddress);
			System.out.println("Lookup succesful!");

			try {
				playerID = airinterface.reservePosition();

			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("Position in a game reserved!");

			boolean a = true;

			while (a) {
				System.out.println("Waiting for game to start.");
				try {
					if (airinterface.initGame()) { 
						// Doesn't the while-loop break here, meaning we immediately break from the if-try-while thing?
						// reply: the Server has an array with two indexes. clients reserve a position in an air hockey game 
						// by placing number 1 in either of the indexes of the server array.
						// the initGame() method brakes the loop only if two clients have 
						// reserved a position for the game from the server.
						// Moved it to the bottom of the if, looks nicer that way if nothing else. @joel
						long l = System.currentTimeMillis();
						while (System.currentTimeMillis()-l<2000){}

						new GameFrame(airinterface, playerID);
						a = false;
						airinterface.emptyReservations();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}