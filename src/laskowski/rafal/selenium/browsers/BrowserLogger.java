package laskowski.rafal.selenium.browsers;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;

public class BrowserLogger {
	
	public static List<String> getLogs(WebDriver driver) {
		Logs logs = driver.manage().logs();
		LogEntries browserLogs = logs.get(LogType.BROWSER);
		LogEntries clientLogs = logs.get(LogType.CLIENT);
		LogEntries driverLogs = logs.get(LogType.DRIVER);
		
		List<String> allLogs = new ArrayList<>();
		for (LogEntry logEntry : browserLogs) {
			allLogs.add(logEntry.getMessage());
		}
		
		for (LogEntry logEntry : clientLogs) {
			allLogs.add(logEntry.getMessage());
		}
		
		for (LogEntry logEntry : driverLogs) {
			allLogs.add(logEntry.getMessage());
		}
		
		return allLogs;
	}
}
