package laskowski.rafal.utils;

public class Logger {
	
	public static void out(Object message) {
		System.out.println(message);
	}
	
	public static void out(String message, Object... parms) {
		System.out.println(String.format(message, parms));
	}

}
