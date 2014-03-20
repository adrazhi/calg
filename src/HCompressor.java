import java.io.*;
import java.util.*;

/*
*  HCompressor Class
* ******************************OPERATIONS****************************
* void main		--> get the input filename that will be compressed, 
* 					get the name of the coded file, and ask the user
* 					for the name of the output compressed file that 
* 					will be written
* void putStr	--> helper function
*/

/**
 * HCompressor Class
 * 
 * @author adrazhi
 * COS 226 | Data Structures
 * University of Maine
 */
public class HCompressor {
	
	public static final int MAX_CHAR_NUMBER = 256;	//char max value for compression
	
	public static void main(String[] args) throws IOException {
		System.out.println("Welcome to the file compressor!");
		Scanner s = new Scanner(System.in);
		
		//get the name of the file that will be compressed
		System.out.print("Please enter the input filename to be compressed: "
				+ "myFile.txt: ");
		String input_filename = s.nextLine();
		
		//get the name of the coded file that corresponds with the input filename
		System.out.print("Please enter name of the code file that corresponds "
				+ "to the input file: ex: myFile_coded: ");
		String coded_filename = s.nextLine();
		
		//name the output compressed file that will be written to the current 
		//working directory
		System.out.print("Please choose a name for the compressed "
				+ "file: e.g. myFile_compressed.txt: ");
		String output_filename = s.nextLine();
		
		//open the coded file that was given by user 
		String[] cd = new String[MAX_CHAR_NUMBER + 1];
		Scanner in_code = new Scanner(new File(coded_filename));
		while (in_code.hasNextLine()) {
			int x = Integer.parseInt(in_code.nextLine());
			cd[x] = in_code.nextLine();
		}
		
		//open the source file and perform the compression
		FileInputStream in = new FileInputStream(input_filename);
		OutStream out = new OutStream(output_filename);
		for (;;) {
			int r = in.read();
			if (r == -1)	//check for EOF
				break;
			putStr(cd[r], out);
		}
		putStr(cd[MAX_CHAR_NUMBER], out);
		out.close();	//recall close to confirm stream is closed
		
		File fl = new File(output_filename);
		float fl_size = fl.length()/1024;	//get the file size in KB
		
		System.out.println("\n" + input_filename +" was successfully compressed! Size = " 
										  + fl_size + " KB \n");
		
		//Offer the file compression option to the user
		System.out.println("Do you want to decompress the created compressed file ?");
		System.out.println("Yes -> Press 1");
		System.out.println("No  -> Press any key (will exit the program)");
		
		//get new option
		Scanner scann = new Scanner(System.in);
		int opt = scann.nextInt();
		if (opt == 1) {
			HDecompressor.main(args);
		} else {
			System.out.println("Thank you for using. Bye!");
			System.exit(0);
		}
		
	}

	//write string to output and use the function to index by the 
	//maximum number of characters in the main function
	private static void putStr(String str, OutStream out_str) {
		for (int i=0; i<str.length(); i++)
			out_str.writeNext(str.charAt(i) - '0');
	}
}
