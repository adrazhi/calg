import java.io.FileInputStream;
import java.io.IOException;
import java.io.*;

/*
*  CONSTRUCTION: with: 
* 	(a) string as input
*
* ******************************OPERATIONS****************************
* void refreshBuffer 	 --> Buffer refresher
* void writeNext		 --> write next bits to the output and flush 
* 							 the buffer if the num of digits matches
* 							 the size, i.e 8 in this case
* void close			 --> close the output stream
* void finalize			 --> make sure the close() function has close
* 							 the output stream
*/

/**
 * OutStream class writes to output stream a bit at a time
 * Any exceptions generated are rethrown as Runtime Exception or IO Exception
 * objects so code does not have to catch or rethrow them. Unless the OutStream
 * explicitly requires another throwing another type of exception
 * 
 * @author adrazhi
 * COS 226 | Data Structures
 * University of Maine
 */
public class OutStream {
    private FileOutputStream output_stream;
    private int stream_digits;  //represent the set of buffer digits
    private int digits_number;  //represent the number of digits available in the buffer
    private static final int SIZE = 8;  //represents how many digits per byte

    /**
     * Send the output to file through the constructor of 
     * OutStream class. Takes file as input parameter
     */
    public OutStream(String file) {
        try {
            output_stream = new FileOutputStream(file);
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
        stream_digits = 0;
        digits_number = 0;
    }

    
    /**
     * Buffer refresher
     */
    private void refreshBuffer() {
        try {
            output_stream.write(stream_digits);
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
        stream_digits = 0;
        digits_number = 0;
    }
    
    
    /**
   	 * write next bits to the output and flush the buffer if 
   	 * the num of digits matches the size, i.e 8 in this case
   	 */
    public void writeNext(int bit) {
        if (bit < 0 || bit > 1)
            throw new IllegalArgumentException("Illegal bit: " + bit);
        stream_digits += bit << digits_number;
        digits_number++;
        if (digits_number == SIZE)
            refreshBuffer();
    }

    /**
   	 * close the output or throw a Runtime Exception if 
   	 * closing the output doesn't succeed
   	 */
    public void close() {
        if (digits_number > 0)
            refreshBuffer();
        try {
            output_stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }

    /**
	 * Confirm that the close() call has closed
	 * the output stream connection
	 */
    protected void finalize() {
        close();
    }
}