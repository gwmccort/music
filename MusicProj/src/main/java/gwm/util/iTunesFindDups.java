package gwm.util;

import gwm.itunes.model.Track;
import gwm.itunes.xml.TrackHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import au.com.bytecode.opencsv.CSVWriter;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * Create a csv file of duplicate iTunes file.
 * 
 * @author gwmccort
 *
 */
public class iTunesFindDups {

	public static void main(String[] args) {

		// proxy setting for work
//		System.setProperty("http.proxyHost", "usproxy");
//		System.setProperty("http.proxyPort", "9090");

		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		Properties props = new Properties();
		try {
			//load a properties file
			props = new Properties() ;
			URL url =  ClassLoader.getSystemResource("music.properties");
			props.load(new FileInputStream(new File(url.getFile())));
			String itunesFile = props.getProperty("itunes.input");
    		String itunesOutput = props.getProperty("itunes.duplicateOutput");

			SAXParser saxParser = saxParserFactory.newSAXParser();
			TrackHandler handler = new TrackHandler();
			saxParser.parse(new File(
					itunesFile),
					handler);

			// Get Tracks
			List<Track> tracks = handler.getTracks();


			// add to multimap
			ArrayListMultimap<String, Track> mm = ArrayListMultimap.create();
			for (Track t : tracks) {
				String name = t.getName();
				String album = t.getAlbum();
				mm.put(name+"|"+album, t);
			}

			CSVWriter writer = new CSVWriter(new FileWriter(itunesOutput));
			writeDuplicateCSV(mm, writer);
			writer.close();


		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeDuplicateCSV(ArrayListMultimap<String, Track> trackMap, CSVWriter writer) {
		for (String k: trackMap.keySet()) {
			int size = trackMap.get(k).size();
			if (size > 2) {
				System.err.println("key:" + k + " has more than 2 entries");
			}
			else {
				if (size == 2){
//				List<String> entry = new ArrayList(20);
//				entry.add(k);
//				entry.add(Integer.toString(mm.get(k).size()));

				List<Track> x = trackMap.get(k);
				Track t1 = x.get(0);
				Track t2 = x.get(1);

				if (!(t1.isPodcast() || t2.isPodcast())) {

					if (!t1.getLocation().getPath().equals(t2.getLocation().getPath())) {
						for (Track t: trackMap.get(k)){
							List<String> entry = new ArrayList(20);
							entry.add(k);
							entry.add(Integer.toString(trackMap.get(k).size()));
							//entry.addAll(Arrays.asList(t.toArray())); //before adding toList
							entry.addAll(t.toList());
							writer.writeNext(entry.toArray(new String[entry.size()]));
						}
						//					System.out.println("\t" + t1.getLocation());
						//					System.out.println("\t" + t2.getLocation());
					}
//					else System.out.println("\tlocations same");
				}
//				// add trace to csv entry
//				for (Track t: mm.get(k)){
//					entry.addAll(t.toList());
//				}
//
////				writer.writeNext((String[])entry.toArray());
//				writer.writeNext(entry.toArray(new String[entry.size()]));
				}
			}
		}
	}

}