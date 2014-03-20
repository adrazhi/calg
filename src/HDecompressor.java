import java.io.*;
import java.util.*;

/*
* HDecompressor Class
* ******************************OPERATIONS****************************
* void main		--> get the name of the compressed file that will be 
* 					used for decompression, get the name of the coded
* 					file that matches with the previously compressed
* 					file and ask the user for the name of the output
* 					decompressed file
*/

/**
 * HDecompressor Class
 * Get the name of the compressed file that will be used for decompression, 
 * get the name of the coded file that did match with the file what was 
 * originally compressed, ask the user to choose a name for the output of the
 * decompressed file which will be written to the current working directory
 * 
 * @author adrazhi
 * COS 226 | Data Structures
 * University of Maine
 */
public class HDecompressor {
	
    public static final int MAX_CHAR_NUM = 256;  //char max value for Decompression

    public static void main(String[] args) throws IOException {
    	System.out.println("Welcome to the file Decompressor!");

		Scanner s = new Scanner(System.in);
		
		//get the name of the compressed file that will be used for decompression
		System.out.print("Please enter the name of the compressed file you wish to "
				+ "decompress, e.g.,  myFile_compressed.txt: ");
		String input_filename = s.nextLine();
		
		//get the name of the coded file that did correspond to the compressed 
		//filename. This was used in the compression process
		System.out.print("Please enter the name of the coded file: ");
		String coded_filename = s.nextLine();
		
		//name the output decompressed file that will be written to the current 
		//working directory
		System.out.print("Please enter a name for the decompressed file "
				+ "decompressed file: e.g. myFile_decompressed.txt: ");
		String output_filename = s.nextLine();
		
		//construct the Huffman tree by using the coded file (given by the user)
		Scanner in = new Scanner(new File(coded_filename));
		HTree new_tree = new HTree(in);
		
		//open the compressed file and decompress using the coded file to match 
		//the results
		InStream inpInStream = new InStream(input_filename);
		PrintStream out_stream = new PrintStream(new File(output_filename));
		
		//read the bits and write to sdt out using decodeToOutput function
		new_tree.decodeToOutput(inpInStream, out_stream, MAX_CHAR_NUM);
		File fl = new File(output_filename);
		float fl_size = fl.length()/1024;	//get the file size in KB
		
		System.out.println(output_filename + " was successfully created. Size = "
									       + fl_size + " KB \n");
		System.out.println("Thank you for using!");
		System.exit(0);
		out_stream.close();
    }
 }
