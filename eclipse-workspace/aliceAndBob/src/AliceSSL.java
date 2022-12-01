import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.TrustManagerFactory;

/**
 * This is the server class. This class responds to any incoming request on a
 * defined port with some secret words of wisdom.
 *
 * @author buchmann
 *
 */
public class AliceSSL {

    // The port alices server is listening to
    private static final int PORT = 2345;

    public static void main(String[] args) throws Exception {

    	try {
   			// open a server socket
//   			ServerSocket ss = new ServerSocket(PORT); 
   			
   			char storePassword[] = "123456".toCharArray();
   			
   			String trustStoreFile = "/home/uebung/AliceTrustStore.ks";
   			KeyStore trustStore = KeyStore.getInstance("JKS");
   			trustStore.load(new FileInputStream(trustStoreFile), storePassword);
   			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
   			tmf.init(trustStore);
   			
   			String keyStoreFile = "/home/uebung/AliceKeyStore.ks";
   			KeyStore keyStore = KeyStore.getInstance("JKS");
   			keyStore.load(new FileInputStream(keyStoreFile), storePassword);
   			KeyManagerFactory kfm = KeyManagerFactory.getInstance("SunX509");
   			kfm.init(keyStore, storePassword);
   			
   			SSLContext sc = SSLContext.getInstance("TLS");
   			sc.init(kfm.getKeyManagers(), tmf.getTrustManagers(), null);
   			
   			SSLServerSocket ss = (SSLServerSocket) sc.getServerSocketFactory().createServerSocket(PORT);
   			ss.setEnabledProtocols(new String[] {"TLSv1", "TLSv1.1","TLSv1.2"});
   			ss.setNeedClientAuth(true);

    		// wait for incoming requests
    		while (true) {
    			System.out.println("Alice is waiting...");
    			Socket s = ss.accept(); // block until a request is incoming
    			
    			System.out.println("Alice receives a call from "+s.getInetAddress().getHostName() +":"+s.getPort());
    			String response = AlicesSecrets.getSecret(); // fetch a secret message from the store
    			
    			OutputStream out = s.getOutputStream();
    			
//    			SSLOutputStream sslOut = SSLOutputStream()
    			
    			
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
