package test;

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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import au.com.bytecode.opencsv.CSVWriter;

public class iTunesFindDups {

	public static void main(String[] args) {

		// proxy setting for work
		System.setProperty("http.proxyHost", "usproxy");
		System.setProperty("http.proxyPort", "9090");

		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		Properties props = new Properties();
		try {
			//load a properties file
			props = new Properties() ;
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


			// add to multimap
			Multimap<String, Track> mm = ArrayListMultimap.create();
			for (Track t : tracks) {
				String name = t.getName();
				String album = t.getAlbum();
				mm.put(name+"|"+album, t);
			}

			for (String k: mm.keySet()) {
				int size = mm.get(k).size();
				if (size > 1) {
					System.out.println("key:" + k + " size:" + mm.get(k).size());
					for (Track t: mm.get(k)){
						System.out.println("\t" + t);
					}
				}
			}


		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

}