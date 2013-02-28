package gwm.util;

import gwm.itunes.model.Track;
import gwm.itunes.xml.TrackHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import au.com.bytecode.opencsv.CSVWriter;

public class iTunesXmlParser {

	public static void main(String[] args) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		Properties props = new Properties();
		try {
			//load a properties file
			Properties properties = new Properties() ;
			URL url =  ClassLoader.getSystemResource("music.properties");
			props.load(new FileInputStream(new File(url.getFile())));
			String itunesFile = props.getProperty("itunes.input");
    		String itunesOutput = props.getProperty("itunes.output");
 
			SAXParser saxParser = saxParserFactory.newSAXParser();
			TrackHandler handler = new TrackHandler();
			saxParser.parse(new File(
					itunesFile),
					handler);
			
			// Get Tracks
			List<Track> tracks = handler.getTracks();

			// write to csv
			CSVWriter writer = new CSVWriter(new FileWriter(itunesOutput));
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