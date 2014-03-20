import java.io.FileInputStream;
import java.io.IOException;
import java.io.*;

/*
*  CONSTRUCTION: with: 
* 	(a) string as input
*
* ******************************OPERATIONS****************************
* void refreshBuffer 	 --> Refreshes the buffer
* int readNext			 --> reads the bits from input when encounters
* 							 -1 that means it is EOF (End of File)
* void closeInput	 	 --> close the input stream
* void confirmClosing	 --> recall closeInput to confirm closing
*/

/**
 * InStream class reads input from an underlying input stream a bit at a time.
 * Any exceptions generated are rethrown as RuntimeException or IO Exception
 * objects so code does not have to catch or rethrow them. Unless the 
 * InStream explicitly requires another type of exception
 * 
 * @author adrazhi
 * COS 226 | Data Structures
 * University of Maine
 */
public class InStream {
	
	private FileInputStream input_stream;	//represent the input stream from java.io
	private int stream_digits;				//represent the set of buffer digits
	private int digits_number; 				//represent the number of digits used
	private static final int SIZE = 8;  	//represents how many digits per byte

	/**
	 * InStream constructor
	 * Should create a stream of bits after takes the filename
	 * from the starndard input from the user
	 * 
	 * @param input file
	 */
    public InStream(String file) {
        try {
            input_stream = new FileInputStream(file);
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
        refreshBuffer();
    }

    /**
	 * Buffer refresher
	 */
    private void refreshBuffer() {
        try {
            stream_digits = input_stream.read();
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
        digits_number = 0;
    }
    
    /**
	 * reads the bits from input
	 * when encounters -1 that means it is EOF
	 * @return result
	 */
    public int readNext() {
        if (stream_digits == -1)
            return -1;
        int result = stream_digits % 2;
        stream_digits /= 2;
        digits_number++;
        if (digits_number == SIZE)
            refreshBuffer();
        return result;
    }

    

    /**
	 * close the input or throw a Runtime Exception if 
	 * closing the input doesn't succeed
	 */
    public void close() {
        try {
            input_stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }

    /**
	 * Confirm that the close() call has closed
	 * the input stream connection
	 */
    protected void finalize() {
        close();
    }
}
