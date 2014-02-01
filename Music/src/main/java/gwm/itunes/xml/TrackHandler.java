package gwm.itunes.xml;

import gwm.itunes.model.Track;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX event handler to parse iTunes xml file
 *
 * @author gwmccort
 *
 */
public class TrackHandler extends DefaultHandler {

	// List to hold Tracks object
	private List<Track> tracks = new ArrayList<Track>();
	private Track track = null;

	// getter method for employee list
	public List<Track> getTracks() {
		return tracks;
	}

	private boolean isInTracks = false;
	private boolean isInTrack = false;
	private boolean isInPlaylists = false;

	private Keys currentKey = Keys.NONE;
	private Tags currentTag = Tags.NONE;

	private StringBuilder charData;

	/*
	 * Process start elements, in particular dict & string.
	 *
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		// System.out.println("startElement() qName:" + qName);

		Tags tag = Tags.fromString(qName);
		// System.out.println("startElement() qName:" + qName +" tag:" + tag);
		currentTag = tag;

		try {
			switch (tag) {
				case DICT:
					if (isInTracks) {
						isInTrack = true;
						track = new Track();
					}
					break;
				case STRING:
					charData = new StringBuilder(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	/*
	 * Process the end element tag.
	 *
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		Tags tag = Tags.fromString(qName);

		switch (tag) {
			case DICT:
				if (isInTracks && !isInTrack) {
					isInTracks = false;
					// System.out.println("endElement not in tracks");
				} else if (isInTrack) {
					isInTrack = false;
					tracks.add(track);
					// System.out.println("endElement end Track:" + track);
				}
				break;
			case STRING:
				switch (currentKey) {
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
					case GENRE:
						track.setGenre(charData.toString());
						break;
					case LOCATION:
						try {
							URL url = new URL(charData.toString());
							File f = new File(URLDecoder.decode(url.getFile(),
									"UTF-8"));
							track.setLocation(f);
						} catch (MalformedURLException
								| UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						break;
				}
				break;
			case TRUE:
				switch (currentKey) {
				case PODCAST:
					track.setPodcast(true);
					break;
				}
				break;
		}
		currentTag = Tags.NONE;
	}

	@Override
	public void characters(char ch[], int start, int length)
			throws SAXException {

		// System.out.println("characters:" + new String(ch, start, length)
		// + " isKey:" + isKey + " isString:" + isString + " isInt:"
		// + isInteger + " currentKey:" + currentKey);

		switch (currentTag) {

		// set current key
			case KEY:
				String keyName = new String(ch, start, length);
				Keys key = Keys.fromString(keyName);
				currentKey = (key != null) ? key : Keys.NONE;

				// determine if in tracks or playlists
				switch (keyName) {
					case "Tracks":
						isInTracks = true;
						break;
					case "Playlists":
						isInPlaylists = true;
						break;
				}
				break;

			case STRING:
				charData.append(new String(ch, start, length));
				break;
			case INTEGER:
				// convert track id int to string
				if (currentKey == Keys.TRACK_ID && isInTrack) {
					int tid = Integer.parseInt(new String(ch, start, length));
					track.setId(tid);
				}
				break;
		}
	}
}
