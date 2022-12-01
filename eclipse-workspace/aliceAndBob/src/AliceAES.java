import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

/**
 * This is the server class. This class responds to any incoming request on a
 * defined port with some secret words of wisdom.
 *
 * @author buchmann
 *
 */
public class AliceAES {

    // The port alices server is listening to
    private static final int PORT = 2345;

    public static void main(String[] args) throws Exception {

    	try {
   			// open a server socket
   			ServerSocket ss = new ServerSocket(PORT);
   			
   			Cipher cipher = AliceAES.getCipher();

    		// wait for incoming requests
    		while (true) {
    			System.out.println("Alice is waiting...");
    			Socket s = ss.accept(); // block until a request is incoming
    			
    			System.out.println("Alice receives a call from "+s.getInetAddress().getHostName() +":"+s.getPort());
    			String response = AlicesSecrets.getSecret(); // fetch a secret message from the store
    			
    			OutputStream out = s.getOutputStream();	
 
    			CipherOutputStream cipherOutputStream = new CipherOutputStream(out, cipher);
    			
    			cipherOutputStream.write(response.getBytes()); // write a secret message to the receiver
    			cipherOutputStream.flush();
    			s.close(); // close the connection
    			
    			System.out.println("Alice closes the connection");
    		}
    	} catch (Exception e) {
    		// catch errors, so some errorhandling
    		System.out.println("Something bad happened with Alices server:");
    		e.printStackTrace();
    	}
    }
    
    public static Cipher getCipher() {
    	try {
    		// Set a key
    		String keyStr = "thisismysecretkey";
    		byte[] key = (keyStr).getBytes("UTF-8");
    		// Obtain a cryptographic hash of 16 bytes from the key
    		MessageDigest sha = MessageDigest.getInstance("MD5");
    		key = sha.digest(key);
    		key = Arrays.copyOf(key, 16);
    		SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
    		// Get an instance of the AES Cipher
    		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
    		System.out.println(secretKeySpec);
    		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
    		// Use this cipher to encrypt an output stream
    		// ...
    		
    		return cipher;
    	} catch (Exception e) {
    		return null;
    	}
    }
}
