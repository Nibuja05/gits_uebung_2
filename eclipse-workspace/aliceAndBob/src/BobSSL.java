import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

/**
 * This class fetches a secret message from Alices server and displays it on
 * stdout.
 *
 * @author buchmann
 *
 */
public class BobSSL {

	// the host where alices server listens
	private static final String HOST = "127.0.0.1";

	// the port of Alices server
	private static final int PORT = 2345;

	public static void main(String[] args) throws Exception {
		
		try {
			// open a socket to host, port
			System.out.println("Bob tries to open a connection to " + HOST + ":" + PORT);
			
   			char storePassword[] = "123456".toCharArray();
   			
   			String trustStoreFile = "/home/uebung/BobsTrustStore.ks";
   			KeyStore trustStore = KeyStore.getInstance("JKS");
   			trustStore.load(new FileInputStream(trustStoreFile), storePassword);
   			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
   			tmf.init(trustStore);
   			
   			String keyStoreFile = "/home/uebung/BobsClientStore.ks";
   			KeyStore keyStore = KeyStore.getInstance("JKS");
   			keyStore.load(new FileInputStream(keyStoreFile), storePassword);
   			KeyManagerFactory kfm = KeyManagerFactory.getInstance("SunX509");
   			kfm.init(keyStore, storePassword);
   			
   			SSLContext sc = SSLContext.getInstance("TLS");
   			sc.init(kfm.getKeyManagers(), tmf.getTrustManagers(), null);
   			
   			SSLSocket s = (SSLSocket)sc.getSocketFactory().
   					createSocket(InetAddress.getByName(HOST), PORT);
			
//			Socket s = new Socket(InetAddress.getByName(HOST), PORT);
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
