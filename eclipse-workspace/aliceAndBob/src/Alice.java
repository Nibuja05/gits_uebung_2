import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the server class. This class responds to any incoming request on a
 * defined port with some secret words of wisdom.
 *
 * @author buchmann
 *
 */
public class Alice {

    // The port alices server is listening to
    private static final int PORT = 2345;

    public static void main(String[] args) throws Exception {

    	try {
   			// open a server socket
   			ServerSocket ss = new ServerSocket(PORT); 

    		// wait for incoming requests
    		while (true) {
    			System.out.println("Alice is waiting...");
    			Socket s = ss.accept(); // block until a request is incoming
    			
    			System.out.println("Alice receives a call from "+s.getInetAddress().getHostName() +":"+s.getPort());
    			String response = AlicesSecrets.getSecret(); // fetch a secret message from the store
    			
    			OutputStream out = s.getOutputStream();
    			out.write(response.getBytes()); // write a secret message to the receiver
    			out.flush();
    			s.close(); // close the connection
    			
    			System.out.println("Alice closes the connection");
    		}
    	} catch (Exception e) {
    		// catch errors, so some errorhandling
    		System.out.println("Something bad happened with Alices server:");
    		e.printStackTrace();
    	}
    }
}
