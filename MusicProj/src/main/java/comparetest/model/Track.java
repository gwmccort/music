package comparetest.model;

public class Track implements Comparable<Track> {
	String name;
	String artist;
	String album;

	public Track(String name, String artist, String album) {
		super();
		this.name = name;
		this.artist = artist;
		this.album = album;
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

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	 @Override
	 public int compareTo(Track t) {

	 // int i = p1.firstName.compareTo(p2.firstName);
	 // if (i != 0) return i;
	 //
	 // i = p2.lastName.compareTo(p2.lastName);
	 // if (i != 0) return i;
	 //
	 // return p1.age - p2.age;


	 int r;
	 r = name.compareTo(t.getName());
	 if (r !=0) return r;

	 r = artist.compareTo(t.artist);
	 if (r !=0) return r;

	 return album.compareTo(t.album);
	 }

	@Override
	public String toString() {
		return "Track [name=" + name + ", artist=" + artist + ", album="
				+ album + "]";
	}

	/*
	 * override hashCode & equals example from:
	 * http://stackoverflow.com/questions
	 * /8180430/how-to-override-equals-method-in-java
	 */

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 53 * hash + (this.artist != null ? this.artist.hashCode() : 0);
		hash = 53 * hash + (this.album != null ? this.album.hashCode() : 0);

		// int example
		// hash = 53 * hash + this.age;

		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		// check if two obj's are same class
		// if (getClass() != obj.getClass()) {
		// System.out.println("classes not equal");
		// return false;
		// }

		// check if two obj's are subclasses of Track
		if (getClass().isAssignableFrom(Track.class)
				&& obj.getClass().isAssignableFrom(Track.class)) {
			System.out.println("classes not equal");
			return false;
		}

		final Track t = (Track) obj;
		if ((name == null) ? (t.name != null) : !name.equals(t.name)) {
			return false;
		}
		if ((artist == null) ? (t.artist != null) : !artist.equals(t.artist)) {
			return false;
		}
		if ((album == null) ? (t.album != null) : !album.equals(t.album)) {
			return false;
		}

		// int example
		// if (this.age != other.age) {
		// return false;
		// }
		return true;
	}

}
