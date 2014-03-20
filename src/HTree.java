import java.io.PrintStream;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;


/* HTree class stores a huffman tree
*  CONSTRUCTION: with: 
* 	(a) c -> num of occurencies of each char
*   (b) in -> by taking user input stream
*
* ******************************OPERATIONS*****************************
* void outputTree 		--> Print out the current tree to output_stream 
* 						    which is given as a parameter in the 
* 							outpuTree method (std).
* void decodeToOutput	--> the decodeToOutput function will have a 
* 							concern reading the bits from the in_str 
* 						    given as an input parameter and writing the
* 							orresponding characters to the out_str which 
* 							is an instance of java's std PrintStream.
* void constructTree	--> This function manages to use a Queue to pro-
* 							perly construct a Huffman Tree, which will 
* 							be basically used as the core data structure 
* 							of this project
* void setBin			--> Set the node to binary.
* void printTreeRepresentation --> print the tree representation by 
* 								   appending the ascii representation 
* 								   value together with the binary 
* 								   representation value. Append a "\n"
* 								   character for legibility
* 
*/

/**
 * HTree class stores a tree based on Huffman data structure
 * 
 * @author adrazhi
 * COS 226 | Data Structures
 * University of Maine
 */
public class HTree {
	
	private Queue<HNode> q;			//queue - hold node elements prior to processing
	private String output = ""; 	//store output result
	private HNode root_node = null; //represents the root of the tree
	
	/**
	 * HTree constructor based on the number of occurrencies of each 
	 * character with its representing ascii value and using the array of
	 * frequencies
	 * 
	 * @param c: how many times does a character occur ?
	 */
	public HTree(int[] c) {
		q = new PriorityQueue<HNode>();
		for (int i=0; i<c.length; i++) {
			if (c[i] > 0) {
				HNode nd = new HNode(null, null, null, c[i], i);
				//offer method doesn't throw an exception
				q.offer(nd);
			}
		}
		//offer a new node to the queue without left, right and parent
		q.offer(new HNode(null, null, null, 1, 256));
		//construct a tree based off the Queue q as input parameter 
		//on the constructTree function which is defined below
		constructTree(q);
		//retrieve and remove the top element from the Queue
		HNode nd = q.remove();
		setBin(nd);
		//print the tree representation given the current node
		printTreeRepresentation(nd);
	}

	/**
	 * HTree constructor - build a huffman tree after taking user input
	 * from the scanner.
	 * 
	 * @param in: standard tree description
	 */
	public HTree(Scanner in) {
		root_node = new HNode(null, null, null, 0, 0);
		HNode top = root_node;
		while(in.hasNextLine()) {
			int l = Integer.parseInt(in.nextLine());
			String s = in.nextLine();
			for (int i=0; i<s.length(); i++) {
				if (s.charAt(i) == '0') {
					if (root_node.left_node == null)
						root_node.left_node = new HNode(null, null, root_node, 0, 0);
					root_node = root_node.left_node;
				} else if (s.charAt(i) == '1') {
					if (root_node.right_node == null)
						root_node.right_node = new HNode(null, null, root_node, 0, 0);
					root_node = root_node.right_node;
				}
			} 
			
			//check if both children of the root are null
			if (root_node.left_node == null && root_node.right_node == null) {
				root_node.ascii_rep = l;
				root_node.binary_rep = s;
			}
			
			root_node = top;
		}
	}
	

	/**
	 * Print out the current tree to output_stream which is given 
	 * as a parameter in the outpuTree method (std).
	 * @param out_str
	 */
	public void outputTree(PrintStream out_str) {
		out_str.print(output);
		out_str.close();
		output = "";
	}
	
	/**
	 * First, the decodeToOutput function will have a concern 
	 * reading the bits from the in_str given as an input parameter and 
	 * writing the corresponding characters to the out_str which is a 
	 * instance of java's std PrintStream. The reading has a stopping
	 * conding when it encounters the EOF(End of File). 
	 * 
	 * @param in_str
	 * @param out_str
	 * @param max_chars
	 */
	public void decodeToOutput(InStream in_str, PrintStream out_str, int max_chars) {
		int in = in_str.readNext();
		HNode nd = root_node;
		while (in != -1) {
			if (in == 1) {
				//check if right_node is null or not
				if (nd.right_node != null)
					nd = nd.right_node;
			} else {
				//check if left node is null or not
				if (nd.left_node != null)
					nd = nd.left_node;
			}
			
			//check to see if both node branches are null
			if (nd.left_node == null && nd.right_node == null) {
				//check if the ascii chars of node are equal to max num of characters
				if (nd.ascii_rep == max_chars) {
					//close the output and set the root to null
					out_str.close();
					root_node = null;
				}
				out_str.print((char) nd.ascii_rep);
				while (nd.parent_node !=  null) {
					nd = nd.parent_node;
				}
			}
			
			//read the next bit element
			in = in_str.readNext();
		}
	}
	
	
	/**
	 * This function manages to use a Queue to properly
	 * construct a Huffman Tree, which will be basically used
	 * as the core data structure of this project
	 * 
	 * @param q
	 */
	public void constructTree(Queue<HNode> q) {
		if (q.size() > 1) {
			//retrieve and remove the head of the Queue
			HNode l = q.remove();
			HNode r = q.remove();
			HNode nd = new HNode(l, r, null, l.freq + r.freq, 0);
			
			//set the right's and the left's nodes parent
			l.parent_node = nd;
			r.parent_node = nd;
			q.offer(nd);
			//construct the tree given the q as input parameter
			constructTree(q);
		}
		
	}
	
	
	/**
	 * Set the node to binary. Check if each left or right node
	 * of a given node is null, and concatenate "0" to represent 
	 * a binary value or "1" to represent the other binary value
	 * 
	 * @param nd
	 */
	public void setBin(HNode nd) {
		//check if the node to the left is empty
		if (nd.left_node != null) {
			HNode ndL = nd.left_node;
			ndL.binValue(nd.binary_rep + "0");
			setBin(ndL);
		}
		
		//check if the node to the right is empty
		if (nd.right_node != null) {
			HNode ndR = nd.right_node;
			ndR.binValue(nd.binary_rep + "1");
			setBin(ndR);
		}
	}
	
	
	/**
	 * print the tree representation by appending
	 * the ascii representation value together with the 
	 * binary representation value. Append a newline character
	 * for readability 
	 * 
	 * @param nd
	 */
	public void printTreeRepresentation(HNode nd) {
		//check to see if both left and right node of a given node
		//are null and recursively call the printTreeRepresentation
		//function on each of the children of the given node
		if (nd.left_node != null && nd.right_node != null) {
			printTreeRepresentation(nd.left_node);
			printTreeRepresentation(nd.right_node);
		} else {
			//add the ascii code representation and binary representation
			//to the output result
			output += nd.ascii_rep + "\n";
			output += nd.binary_rep + "\n";
		}
	}
	
}
