/*
*  HNode class
*  CONSTRUCTION: with: 
* 	(a) left, right, parent, frequency, ascii representation
*
* ******************************OPERATIONS****************************
* void binValue 	 --> Update the binary symbol representation value
* int compareTo		 --> Comparison method that compares between nodes
* String toString	 --> Used to print node info + good debug practice
*/

/**
 * HNode class represents a Huffman tree node and 
 * is used to store a node in the Huffman tree
 * 
 * @author adrazhi
 * COS 226 | Data Structures
 * University of Maine
 */
public class HNode implements Comparable<HNode> {
	
	public int freq; 		  //represent the letter frequency
	public int ascii_rep; 	  //represents the ascii letters
	public HNode left_node;   //represent the left node
	public HNode right_node;  //represent the right node
	public HNode parent_node; //represent the parent node
	public String binary_rep = ""; //represents the binary code
	
	/**
	 * HNode constructor takes the following parameters as input: 
	 * @param left_node
	 * @param right_node
	 * @param parent_node
	 * @param freq
	 * @param ascii_rep
	 */
	public HNode (HNode left_node, HNode right_node, HNode parent_node, int freq, int ascii_rep) {
		this.left_node = left_node;
		this.right_node = right_node;
		this.parent_node = parent_node;
		this.ascii_rep = ascii_rep;
		this.freq = freq;
	}
	
	/**
	 * Update the binary symbol representation value
	 * @param binary_rep
	 */
	public void binValue(String binary_rep) {
		this.binary_rep = binary_rep;
	}
	
	/**
	 * Comparison method that compares between nodes
	 */
	public int compareTo(HNode node) throws ClassCastException {
		if (!(node instanceof HNode))
			throw new ClassCastException("Invalid node set!");
		int r = freq - node.freq;
		return r;
	}
	
	/**
	 * toString method used to pring the node info
	 * also good debugging practice
	 */
	public String toString() {
		return "Ascii representation: " + ascii_rep + ", frequency: " + freq 
				+ ", binary representation: " + binary_rep;
	}
	
}

