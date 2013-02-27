package gwm.itunes.xml;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CopyOfEnumTrackHandler extends DefaultHandler {

	// List to hold Tracks object
	private List<Track> tracks = new ArrayList<Track>();
	private Track track = null;

	// getter method for employee list
	public List<Track> getTracks() {
		return tracks;
	}

	private boolean isInTracks = false;
	private boolean isInTrack = false;

	private Keys currentKey = Keys.NONE;
	private Tags currentTag = Tags.NONE;
	
	private StringBuilder charData;

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

//		 System.out.println("startElement() qName:" + qName + " localName:" +
//		 localName);
		
		
		Tags tag = Tags.fromString(qName);
		System.out.println("tag:" + tag);
		currentTag = tag;
		switch(tag){
			case DICT:
				currentTag = Tags.DICT;
				if (!isInTracks) {
					isInTracks = true;
					// //System.out.println("dict start tracks");
				} else { // if (isInTracks) {
					isInTrack = true;
					track = new Track();
					//System.out.println("--------- dict start track ------------");
				}
				break;
//			case KEY:
//				currentTag = Tags.KEY;
//				break;
//			case INTEGER:
//				currentTag = Tags.INTEGER;
//				break;
			case STRING:
				currentTag = tag;
				charData = new StringBuilder(200);
			default:
				currentTag = tag;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		Tags tag = Tags.fromString(qName);
		switch(tag){
			case DICT:
				if (isInTrack) {
					isInTrack = false;
					tracks.add(track);
					//System.out.println("endElement end Track:" + track);
				}
				break;
			case STRING:
				switch (currentKey){
				case NAME:
					track.setName(charData.toString());
					break;
				case ARTIST:
					track.setArtist(charData.toString());
					break;
				case ALBUM_ARTIST:
					track.setAlbumArtist(charData.toString());
					break;
				case ALBUM:
					track.setAlbum(charData.toString());
					break;
				}
		}
		currentTag = Tags.NONE;
	}

	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException {

//		System.out.println("characters:" + new String(ch, start, length)
//				+ " isKey:" + isKey + " isString:" + isString + " isInt:"
//				+ isInteger + " currentKey:" + currentKey);
		
		
		switch(currentTag){
		case KEY:
			String keyName = new String(ch, start, length);
			Keys key =  Keys.fromString(keyName);
			currentKey = (key!=null)?key:Keys.NONE;
			break;
		case STRING:
			charData.append(new String(ch, start, length));
			break;
		case INTEGER:
			if (currentKey == Keys.TRACK_ID) {
				int tid = Integer.parseInt(new String(ch, start, length));
				//System.out.println("track id:" + tid);
				track.setId(tid);
			}
			break;
		}
	}
}