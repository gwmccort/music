package gwm;

import java.io.FileReader;
import java.util.Arrays;

import au.com.bytecode.opencsv.CSVReader;

public class ITunesExportHome {

	// [Name 0, Artist 1, Composer 2, Album 3, Grouping 4, Genre 5, Size 6, Time 7, Disc Number 8,
	// Disc Count 9, Track Number 10, Track Count 11, Year 12, Date Modified 13, Date Added 14,
	// Bit Rate 15, Sample Rate 16, Volume Adjustment 17, Kind 18, Equalizer 19, Comments 20,
	// Plays 21, Last Played 22, Skips 23, Last Skipped 24, My Rating 25]

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		CSVReader reader = new CSVReader(new FileReader(
				"c:/users/glen/documents/music.txt"), '\t');
		String[] nextLine;
		
		// read header line
		nextLine = reader.readNext();
//		System.out.println("nextLine:" + Arrays.toString(nextLine));

		 while ((nextLine = reader.readNext()) != null) {
		 // System.out.println("nextLine:" + Arrays.toString(nextLine));
		 // nextLine[] is an array of values from the line
		 System.out.println(nextLine[0] + "\t" + nextLine[1] + "\t" + nextLine[3] + "\t" + nextLine[5] );
		 }
		reader.close();

	}

}
