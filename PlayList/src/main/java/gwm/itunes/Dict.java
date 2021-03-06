package gwm.itunes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import au.com.bytecode.opencsv.CSVWriter;

public class Dict {

	HashMap<String, Object> m = new HashMap<String, Object>();

	public void add(String key, Object value) {
		m.put(key, value);
	}
	public Object get(String key) {
		return m.get(key);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (Map.Entry<String, Object> entry : m.entrySet()) {
			sb.append(entry.getKey());
			sb.append(':');
			sb.append(entry.getValue());
			sb.append(", ");
		}
		sb.append(']');
		return sb.toString();
	}

	public String toCSV() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Object> entry : m.entrySet()) {
			Track t = new Track((Dict)entry.getValue());
			sb.append(t.toCSV());
			sb.append("\n");
		}
		return sb.toString();
	}

	public void toCSV(CSVWriter writer) {
		for (Map.Entry<String, Object> entry : m.entrySet()) {
			Track t = new Track((Dict)entry.getValue());
			t.toCSV(writer);
		}
	}


	/**
	 * Test to create a csv file from an iTunes library xml file.
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		 CSVWriter writer = new CSVWriter(new FileWriter("yourfile.csv"));

		// File file = new File("dict.xml");
		File file = new File("input/itunesLib.xml");
//		File file = new File("input/iTunes Music Library.xml");

		// use StAX api to read xml
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLEventReader reader = inputFactory
				.createXMLEventReader(new FileInputStream(file));

		try {
			while (reader.hasNext()) {
				XMLEvent e = reader.nextEvent();
				if (e.isCharacters() && e.asCharacters().isWhiteSpace()) {
					continue;
				} else if (e.isStartElement()) {
					StartElement se = e.asStartElement();
//					System.out.println("se:" + se.getName());
					if ("dict".equals(se.getName().getLocalPart())) {
						Dict d = processDict(reader);
//						System.out.println("******************");

						//System.out.println("dict:" + d);

						// just print out tracks
						Dict tracks = (Dict) d.get("Tracks");
						tracks.toCSV(writer);
					}
				}
			}

		} finally {
			reader.close();
			writer.close();
		}
		System.out.println("main() finished!");
	}

	static Dict processDict(XMLEventReader reader) throws XMLStreamException {
		//System.out.println("**** dict called");
		Dict results = new Dict();

		// skip dict
		// System.out.println("skiped:" + reader.nextEvent().getEventType());

		// reader.nextEvent();

		while (reader.hasNext()) {
			XMLEvent e = reader.nextEvent();

			if (e.isStartElement()
					&& "key".equals(e.asStartElement().getName().getLocalPart())) {

				// read key
				String key = reader.getElementText();
//				System.out.println("found key:" + key);
				// System.out.println("start:" + e.asStartElement().getName()
				// + ":" + key);

				// skip whitespace
				while (e.isCharacters() && e.asCharacters().isWhiteSpace()){
//					System.out.println("skiping whitespace...");
					e = reader.nextEvent();
				}

				// read value
				e = reader.nextEvent();


				// skip whitespace
				while (e.isCharacters() && e.asCharacters().isWhiteSpace()){
//					System.out.println("skiping whitespace...");
					e = reader.nextEvent();
				}

				if (e.isStartElement() && "dict".equals(e.asStartElement().getName().getLocalPart())){
					results.add(key, processDict(reader));
//					System.out.println("after processDict !!!!!!!!!!!!!!!!!!!");
				}
				else if (e.isStartElement() && "array".equals(e.asStartElement().getName().getLocalPart())){
//					System.out.println("array processing TBD:");
					while (reader.hasNext()) {
						e = reader.nextEvent();
					}
				}
				else if (e.isStartElement()){
					String type = e.asStartElement().getName().getLocalPart();
//					lSystem.out.println("type:" + type);
					String valueString = reader.getElementText();
					// System.out.println("\ttype:" + type + ":" + valueString);
					if ("integer".equals(type))
						results.add(key, new Long(valueString));
					else if ("true".equals(type))
						results.add(key, new Boolean(true));
					else if ("false".equals(type))
						results.add(key, new Boolean(false));
					else if ("string".equals(type))
						results.add(key, valueString);
					else if ("dict".equals(type))
						results.add(key, processDict(reader));
				} else {
					System.out.println("not a start element!!!!!! :" + e.getEventType());
				}
			} else if (e.isStartElement()) {
				System.out.println("se2" + e.asStartElement().getName());
			} else if (e.isCharacters() && e.asCharacters().isWhiteSpace()) {
//				System.out.println("skiping white space");
				continue;
			} else if (e.isEndElement()) {
//				System.out.println("found end tag:"
//						+ e.asEndElement().getName().getLocalPart());
				return results;
			}
		}
//		System.out.println("end of events");
		return results;
	}


}
