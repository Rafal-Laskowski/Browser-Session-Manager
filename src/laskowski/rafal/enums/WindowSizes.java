package laskowski.rafal.enums;

import laskowski.rafal.selenium.WindowSize;

public enum WindowSizes {
	DEFAULT, MAXIMIZED, SIZED;

	public static WindowSize parse(String string) {
		WindowSizes size = null;
		if (string.equalsIgnoreCase("Default")) {
			size = WindowSizes.DEFAULT;
		} else if (string.equalsIgnoreCase("Maximized")) {
			size = WindowSizes.MAXIMIZED;
		} else if (string.equalsIgnoreCase("Custom size")) {
			size = WindowSizes.SIZED;
		}
		
		if (size != null) {
			return new WindowSize(size);
		}
		
		throw new RuntimeException(String.format("Window Size type [%s] is not supported!", string));
	}
}
