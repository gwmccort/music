package gwm.itunes.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import au.com.bytecode.opencsv.CSVWriter;

public class TracksXMLParserSAX {

	public static void main(String[] args) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			// TrackHandler handler = new TrackHandler();
			EnumTrackHandler handler = new EnumTrackHandler();
			// saxParser.parse(new File("tracks2.xml"), handler);
			// saxParser.parse(new File("tracks.xml"), handler);
//			 saxParser.parse(new File("itunesLib.xml"), handler);
			// saxParser.parse(new
			// File("C:/Users/Glen/Music/iTunesXmas/iTunes Library.xml"),
			// handler);
			saxParser.parse(new File(
					"C:/Users/Glen/Music/iTunes/iTunes Music Library.xml"),
					handler);
			
			// Get Tracks
			List<Track> tracks = handler.getTracks();

			// print employee information
//			for (Track t : tracks)
//				System.out.println(t);

			// write to csv
			CSVWriter writer = new CSVWriter(new FileWriter("output/itunes.csv"));
			writer.writeNext(Track.getColumns());
			for (Track t : tracks) {
				writer.writeNext(t.toArray());
			}
			writer.close();

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

}