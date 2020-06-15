package laskowski.rafal.selenium;

import org.openqa.selenium.By;

import laskowski.rafal.enums.BYs;

public class ByParser {
	
	public static String getSelector(By by) {
		String selectorPart = by.toString().split("[ ]")[1];
		return selectorPart.substring(0, selectorPart.length());
	}
	
	public static BYs getType(By by) {
		String strBy = by.toString();
		if (strBy.contains("By.cssSelector")) {
			return BYs.CSS;
		} else if (strBy.contains("By.xpath")) {
			return BYs.XPATH;
		} else if (strBy.contains("By.id")) {
			return BYs.ID;
		} else {
			throw new IllegalArgumentException(String.format("Cannot distinguish type of BY from [%s]", strBy));
		}
	}
}
