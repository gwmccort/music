package gwm.itunes.model;

import java.io.File;

public class Track {
	private int id;
	private String name;
	private String artist;
	private String albumArtist;
	private String album;
	private String genre;
	private File location;
	private boolean isPodcast = false;

	private int columns = 6;

	public static String[] getColumns() {
		String[] r = { "id", "name", "artist", "album artist", "album",
				"location" };
		return r;
	}

	public String[] toArray() {
		String[] r = new String[6];
		int idx = 0;
		r[idx++] = Integer.toString(id);
		r[idx++] = name;
		r[idx++] = artist;
		r[idx++] = albumArtist;
		r[idx++] = album;
		r[idx++] = (location != null) ? location.getPath() : "";
		return r;
	}

	@Override
	public String toString() {
		return "Track[id:" + id + ", name:" + name + ", artist:" + artist
				+ ",  albumArtist:" + albumArtist + ",  album:" + album
				+ ",  location:" + location.getPath() + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbumArtist() {
		return albumArtist;
	}

	public void setAlbumArtist(String albumArtist) {
		this.albumArtist = albumArtist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public File getLocation() {
		return location;
	}

	public void setLocation(File location) {
		this.location = location;
	}

	public boolean isPodcast() {
		return isPodcast;
	}

	public void setPodcast(boolean isPodcast) {
		this.isPodcast = isPodcast;
	}

}
