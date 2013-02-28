package gwm.itunes.xml;

import gwm.itunes.model.Track;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class OldTrackHandler extends DefaultHandler {

	// List to hold Tracks object
	private List<Track> tracks = new ArrayList<Track>();
	private Track track = null;

	// getter method for employee list
	public List<Track> getTracks() {
		return tracks;
	}

	boolean isInTracks = false;
	boolean isInTrack = false;

	boolean isKey = false;
	boolean isInteger = false;
	boolean isString = false;

	boolean isId = false;
	boolean isName = false;

	boolean bAge = false;
	boolean bName = false;
	boolean bGender = false;
	boolean bRole = false;

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		// System.out.println("startElement() localName:" + localName);
		if (qName.equalsIgnoreCase("dict")) {
			if (!isInTracks) {
				isInTracks = true;
				System.out.println("dict start:" + qName);
			} else { // if (isInTracks) {
				isInTrack = true;
				track = new Track();
				System.out.println("startElement in track");
			}
		} else if (qName.equalsIgnoreCase("key")) {
			System.out.println("Start key");
			isKey = true;
		} else if (qName.equalsIgnoreCase("integer")) {
			System.out.println("Start int");
			isInteger = true;
		} else if (qName.equalsIgnoreCase("string")) {
			System.out.println("Start string");
			isString = true;
		}

		// if (qName.equalsIgnoreCase("Employee")) {
		// // create a new Employee and put it in Map
		// String id = attributes.getValue("id");
		// // initialize Employee object and set id attribute
		// track = new Track();
		// track.setId(Integer.parseInt(id));
		// // initialize list
		// if (tracks == null)
		// tracks = new ArrayList<>();
		// } else if (qName.equalsIgnoreCase("name")) {
		// // set boolean values for fields, will be used in setting Employee
		// // variables
		// bName = true;
		// } else if (qName.equalsIgnoreCase("age")) {
		// bAge = true;
		// } else if (qName.equalsIgnoreCase("gender")) {
		// bGender = true;
		// } else if (qName.equalsIgnoreCase("role")) {
		// bRole = true;
		// }
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (qName.equalsIgnoreCase("dict")) {
			if (isInTrack) {
				isInTrack = false;
				tracks.add(track);
				System.out.println("endElement end Track:");
			} else if (isInTracks) {
				isInTracks = false;
				System.out.println("endElement end Tracks");
			}
		} else if (qName.equalsIgnoreCase("integer")) {
			System.out.println("end integer");
			isInteger = false;
		}

		if (qName.equalsIgnoreCase("Employee")) {
			// add Employee object to list
			tracks.add(track);
		}
	}

	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException {

		if (isKey) {
			String key = new String(ch, start, length);
			// System.out.println("key:" + key);
			isKey = false;
			if (key.equals("Track ID")) {
				isId = true;
			} else if (key.equals("Name")) {
				isName = true;
			}
		} else if (isString) {
			String s = new String(ch, start, length);
			System.out.println("string:" + s);
			if (isName) {
				isName = false;
				String name = new String(ch, start, length);
				System.out.println("name:" + name);
				track.setName(name);
			}
		} else if (isInteger) {
			if (isId) {
				isId = false;
				int tid = Integer.parseInt(new String(ch, start, length));
				System.out.println("track id:" + tid);
				track.setId(tid);
			}
		}

		// if (bAge) {
		// // age element, set Employee age
		// track.setAge(Integer.parseInt(new String(ch, start, length)));
		// bAge = false;
		// } else if (bName) {
		// track.setName(new String(ch, start, length));
		// bName = false;
		// } else if (bRole) {
		// track.setRole(new String(ch, start, length));
		// bRole = false;
		// } else if (bGender) {
		// track.setGender(new String(ch, start, length));
		// bGender = false;
		// }

	}
}