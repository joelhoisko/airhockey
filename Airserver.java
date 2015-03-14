package airhockey;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Reads the policy-files to allow others to connect to you and creates a Gamecontroller object
 * and binds it for the rmiregistry for the players to start using it.
 * @author joel
 *
 */
public class Airserver {

	public static void main(String[] args) throws RemoteException {
		String s = "";
		//int port = 1105;

		try {
			InetAddress i = InetAddress.getLocalHost();
			s = i.getHostName();
			
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		//Get your ip from the Internet, start the rmiregistry from command line first before running the server	
		System.setProperty("-Djava.rmi.server.hostname", s);
		System.setProperty("java.security.policy", "file:./airhockey.policy");
		System.setProperty("java.net.SocketPermission", "file:./Connection.policy");

		// Registry registry = null;
		// Registry registry is never used?

		try {
			Gamecontroller controller = new Gamecontroller();

			System.out.println("Let's start the bind!");
			Naming.rebind("controller", controller);

			System.out.println("Bind complete.");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		System.out.println("End of Airserver-file.");
	}
}