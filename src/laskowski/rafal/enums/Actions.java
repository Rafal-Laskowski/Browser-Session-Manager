package laskowski.rafal.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Actions {
	GET, CLICK, SEND_KEYS;

	public static Actions parse(String text) {
		switch (text) {
		case "GET":
		case "get":
			return Actions.GET;

		case "click":
		case "CLICK":
			return Actions.CLICK;

		case "send_keys":
		case "SEND_KEYS":
		case "send keys":
		case "SEND KEYS":
			return Actions.SEND_KEYS;
		}
		throw new IllegalArgumentException(String.format("Action %s is not supported!", text));
	}
}
