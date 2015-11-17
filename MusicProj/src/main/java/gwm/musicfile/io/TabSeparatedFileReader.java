package gwm.musicfile.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * read tab separated files which were exported from iTunes playlist
 * @author Glen
 *
 */
public class TabSeparatedFileReader {
	
	BufferedReader reader;
	
	public TabSeparatedFileReader(String filePath) throws IOException {
		reader = new BufferedReader(new FileReader(filePath));
	}

	
	public String[] readNext() throws IOException {
		String[] results = null;
		String line = reader.readLine();
		if (line==null) return results;
		results = line.split("\t");
		return results;
	}
	
	public void close() throws IOException{
		reader.close();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
//		String input = "input/Music.txt";
		String input = "input/Jam.txt";
		TabSeparatedFileReader reader = new TabSeparatedFileReader(input);
		String[] fields;
		while ((fields=reader.readNext()) != null){
			System.out.println(Arrays.toString(fields));
		}
		

	}

}
