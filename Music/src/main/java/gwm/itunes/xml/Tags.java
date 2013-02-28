package gwm.itunes.xml;

/**
 * Enums for iTunes xml tags
 * 
 * @author Glen
 *
 */
public enum Tags {
	NONE(""), PLIST("plist"), DICT("dict"), KEY("key"), STRING("string"), INTEGER(
			"integer"), DATE("date"), DATA("data"), TRUE("true"), FALSE("false"), ARRAY("array");
	private String text;

	Tags(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	public static Tags fromString(String text) {
		if (text != null) {
			for (Tags t : Tags.values()) {
				if (text.equalsIgnoreCase(t.text)) {
					return t;
				}
			}
		}
		return null;
	}
}
