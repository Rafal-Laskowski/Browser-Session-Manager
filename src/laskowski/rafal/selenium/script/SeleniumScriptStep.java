package laskowski.rafal.selenium.script;

import java.io.Serializable;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import laskowski.rafal.enums.Actions;

public class SeleniumScriptStep implements Serializable {
	private static final long serialVersionUID = 64033782414459192L;
	private By by;
	private Actions action;
	private String textToEnter;

	public void invoke(WebDriver driver) {
		WebElement element = null;

		if (by != null) {
			element = driver.findElement(by);
		}

		switch (action) {
		case CLICK:
			element.click();
			break;
		case GET:
			driver.get(textToEnter);
			break;
		case SEND_KEYS:
			element.sendKeys(textToEnter);
			break;
		}
	}

	public void setBy(By by) {
		this.by = by;
	}

	public void setAction(Actions action) {
		this.action = action;
	}

	public void setTextToEnter(String text) {
		this.textToEnter = text;
	}
	
	public Optional<By> getBy() {
		return Optional.ofNullable(by);
	}

	public Actions getAction() {
		return this.action;
	}
	
	public String getTextToEnter() {
		return this.textToEnter;
	}
}
