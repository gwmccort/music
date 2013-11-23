package gwm.itunes.xml;

/**
 * Enums for iTunes xml dict keys
 * 
 * @author Glen
 *
 */
public enum Keys {
	NONE(""), TRACK_ID("Track ID"), NAME("Name"), ARTIST("Artist"), ALBUM_ARTIST(
			"Album Artist"), ALBUM("Album"), LOCATION("Location");

	private String text;

	Keys(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	public static Keys fromString(String text) {
		if (text != null) {
			for (Keys k : Keys.values()) {
				if (text.equalsIgnoreCase(k.text)) {
					return k;
				}
			}
		}
		return null;
	}
}