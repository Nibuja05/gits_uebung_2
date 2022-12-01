import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class ReadWriteAES {

	public static void main(String[] args) throws Exception {

		/////////////////////////////////////////////////
		// Variables
		String infile = "/home/uebung/image.ppm";
		String outfile = "/home/uebung/image-copy.ppm";

		/////////////////////////////////////////////////
		//read/write file
		System.out.println("Read " + infile + " write " + outfile);		
		
		// Open file
		FileInputStream inputStream = new FileInputStream(infile);		
		FileOutputStream outputStream = new FileOutputStream(outfile);
		
		// Set a key
		String keyStr = "thisismysecretkey";
		byte[] key = (keyStr).getBytes("UTF-8");
		// Obtain a cryptographic hash of 16 bytes from the key
		MessageDigest sha = MessageDigest.getInstance("MD5");
		key = sha.digest(key);
		key = Arrays.copyOf(key, 16);
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
		// Get an instance of the AES Cipher
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		// Use this cipher to encrypt an output stream
		// ...
		CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
		
		// read and write the file
		byte[] buffer = new byte[8192];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
			cipherOutputStream.write(buffer, 0, length);
		}
		
		// close all the streams.
		inputStream.close();
		outputStream.close();
//		cipherOutputStream.close();
		System.out.println("done.");		
	}

}
