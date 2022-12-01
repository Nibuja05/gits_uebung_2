import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * This class fetches a secret message from Alices server and displays it on
 * stdout.
 *
 * @author buchmann
 *
 */
public class Bob {

	// the host where alices server listens
	private static final String HOST = "127.0.0.1";

	// the port of Alices server
	private static final int PORT = 2345;

	public static void main(String[] args) throws Exception {
		
		try {
			// open a socket to host, port
			System.out.println("Bob tries to open a connection to " + HOST + ":" + PORT);
			Socket s = new Socket(InetAddress.getByName(HOST), PORT);
			InputStream in = s.getInputStream();

			// read bytes until the server closes the connection
			String msg = "Bob receives from Alice:\n";
			byte[] buffer = new byte[8192];
			while ((in.read(buffer)) != -1) {
				msg = msg + new String(buffer);
			}

			// print the message and close the connection to the server
			System.out.println(msg);
			s.close();
			System.out.println("done.");
		} catch (IOException e) {
			// catch errors and do some error handling
			System.out.println("Something bad happened with Bobs client:");
			e.printStackTrace();
		}

	}

}
