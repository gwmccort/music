package gwm.musicfile;

import gwm.musicfile.io.TabSeparatedFileReader;

import java.io.IOException;
import java.util.Arrays;

public class ITunesExportReader {
	TabSeparatedFileReader reader;

	// [Name 0, Artist 1, Composer 2, Album 3, Grouping 4, Genre 5, Size 6, Time
	// 7, Disc Number 8,
	// Disc Count 9, Track Number 10, Track Count 11, Year 12, Date Modified 13,
	// Date Added 14,
	// Bit Rate 15, Sample Rate 16, Volume Adjustment 17, Kind 18, Equalizer 19,
	// Comments 20,
	// Plays 21, Last Played 22, Skips 23, Last Skipped 24, My Rating 25]

	// fields:[Name0, Artist1, Composer2, Album3, Grouping4, Genre5, Size6,
	// Time7, Disc Number8, Disc Count9, Track Number10, Track Count11, Year12,
	// Date Modified13, Date Added14, Bit Rate15, Sample Rate16, Volume
	// Adjustment17, Kind18, Equalizer19, Comments20, Plays21, Last Played22,
	// Skips23, Last Skipped24, My Rating25, Location26]

	public ITunesExportReader(String filePath) throws IOException {
		reader = new TabSeparatedFileReader(filePath);
	}

	public Mp3File readNext() throws IOException {
		String[] fields = reader.readNext();
		if (fields != null)
			return new Mp3File(fields[26], fields[0], fields[1], "albumArtist",
					fields[3]);
		else
			return null;

	}

	public static void main(String[] args) throws Exception {
		ITunesExportReader r = new ITunesExportReader("input/Jam.txt");
		r.readNext();
		Mp3File f;
		while ((f = r.readNext()) != null) {
			System.out.println(f + ":" + f.isLocationValid());
		}
	}

	/**
	 * @param args
	 */
	public static void mainOld(String[] args) throws Exception {
		// TabSeparatedFileReader reader = new TabSeparatedFileReader(
		// "c:/users/glen/documents/music.txt");
		TabSeparatedFileReader reader = new TabSeparatedFileReader(
				"input/Jam.txt");
		String[] fields;

		// read header line
		fields = reader.readNext();
		System.out.println("fields:" + Arrays.toString(fields));

		while ((fields = reader.readNext()) != null) {
			// System.out.println("nextLine:" + Arrays.toString(nextLine));
			// nextLine[] is an array of values from the line
			// System.out.println(fields[0] + "\t" + fields[1] + "\t" +
			// fields[3] + "\t" + fields[26] );
		}

		reader.close();

	}

}
