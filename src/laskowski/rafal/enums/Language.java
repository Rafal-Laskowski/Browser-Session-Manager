package laskowski.rafal.enums;

public enum Language {
	ENGLISH, POLISH;

	public String getProperty() {
		switch (this) {
		case ENGLISH:
			return Language.getEnglishProperty();
		case POLISH:
			return Language.getPolishProperty();
		default:
			throw new RuntimeException(String.format("Language property [%s] is not implemented!", this.name()));
		}
	}

	public static String getEnglishProperty() {
		return "en-GB";
	}

	public static String getPolishProperty() {
		return "pl-PL";
	}

	public static Language parse(String text) {
		if (text.equalsIgnoreCase("english") || text.equalsIgnoreCase("en-gb")) {
			return Language.ENGLISH;
		} else if (text.equalsIgnoreCase("polish") || text.equalsIgnoreCase("pl-pl")) {
			return Language.POLISH;
		}
		throw new RuntimeException(String.format("Language [%s] is not supported!", text));
	}
}
