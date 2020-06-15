package laskowski.rafal.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum BYs {
	ID, CSS, XPATH;
	
	public static List<String> toList() {
		return Arrays.stream(BYs.values()).map(Enum::toString).collect(Collectors.toList());
	}
}
