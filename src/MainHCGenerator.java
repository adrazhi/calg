import java.io.*;
import java.util.*;

/*
* ******************************OPERATIONS****************************
* void main		--> get input filename.txt and output the coded file
* 					based on user-inserted name. also responsible
* 					for counting the occurrencies frequency for each
* 					character on the input txt file
*/

/**
 * CodeGenerator class gets the file names from user
 * input filename, and asks for choosing a name for the 
 * output coded file that 
 * 
 * @author adrazhi
 * COS 226 | Data Structures
 * University of Maine
 */
public class MainHCGenerator {
	public static final int MAX_CHAR_NUM = 256; // max char value to be encoded

	public static void main(String[] args) throws IOException {
		System.out.println("Generate the Huffman Code of a given input file.");

		/*
		 * get the input filename from the user
		 * this is also the original(ideally a .txt) file
		 * that will be compressed/encoded
		 */
		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter the input filename, e.g. testFile.txt: ");
		String input_file = sc.nextLine();
		File f = new File(input_file);
		
		//check if filename matches with the file that is in the directory 
		if (f.exists()) 
			System.out.println("File was found. The size is: " + f.length()/1024 + " KB");

		/*
		 * Get the name for the coded file that will be written to the current 
		 * working directory
		 */
		System.out.print("Enter the name in which you want to save the coded file,"
				+ " e.g. coded_testFile: ");
		String coded_file = sc.nextLine();
		

		/*
		 * open the entered input file and count the frequency of each character
		 * i.e. how many times does each char appear on the file content
		 */
		FileInputStream input_fiStream = new FileInputStream(input_file);
		int[] char_freq_counter = new int[MAX_CHAR_NUM];
		for (;;) {
			int r = input_fiStream.read();
			if (r == -1) 	//i.e. check for EOF
				break;
			char_freq_counter[r]++;
		}

		/*
		 * build a tree based on the input file results and
		 * print the codes to the coded file and write it 
		 * to the directory based upon name inserted by user
		 */
		HTree new_tree = new HTree(char_freq_counter);
		PrintStream output_result = new PrintStream(new File(coded_file));
		new_tree.outputTree(output_result);
		
		//give feedback to user whether the performed operation succeeded or not
		System.out.println( coded_file + " was successfully created in your directory! \n");
		System.out.println("Would you like to run the file compressor? ");
		System.out.println("Yes -> Press 1");
		System.out.println("No  -> Press any key (will exit the program)");
		Scanner scann = new Scanner(System.in);
		int option = scann.nextInt();
		//automatically show compression and decompression as options
		//to select after finishing the coding operation
		if (option == 1) {
			HCompressor hc = new HCompressor();
			hc.main(args);
			//check if decompression option selected
		} else {
			System.out.println("Thank you for using. Bye!");
		}
		
	}
}
