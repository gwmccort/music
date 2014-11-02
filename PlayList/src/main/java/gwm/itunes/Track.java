package gwm.itunes;

import au.com.bytecode.opencsv.CSVWriter;

public class Track {
	Long id;
	String artist;
	String album;
	String name;

	public Track(Long id, String artist, String album, String name) {
		super();
		this.id = id;
		this.artist = artist;
		this.album = album;
		this.name = name;
	}

	public Track(Dict dict) {
		super();
		// System.out.println(dict);
		this.id = (Long) dict.get("Track ID");
		this.artist = (String) dict.get("Artist");
		this.album = (String) dict.get("Album");
		this.name = (String) dict.get("Name");
	}

	@Override
	public String toString() {
		return "Track [id=" + id + ", artist=" + artist + ", album=" + album
				+ ", name=" + name + "]";
	}

	public String[] toArray(){
		String[] sa = new String[4];
		sa[0] = id.toString();
		sa[1] = artist;
		sa[2] = album;
		sa[3] = name;
		return sa;
	}

	public String toCSV() {
		return "" + id + "," + artist + "," + album
				+ "," + name;
	}

	public void toCSV(CSVWriter writer) {
		writer.writeNext(toArray());
	}

}
