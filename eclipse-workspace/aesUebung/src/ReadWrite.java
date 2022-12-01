import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ReadWrite {

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
		
		// read and write the file
		byte[] buffer = new byte[8192];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, length);
		}
		
		// close all the streams.
		inputStream.close();
		outputStream.close();
		System.out.println("done.");		
	}
}
