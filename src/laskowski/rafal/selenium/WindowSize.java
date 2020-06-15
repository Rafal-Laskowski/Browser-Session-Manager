package laskowski.rafal.selenium;

import java.io.Serializable;
import java.util.Optional;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import laskowski.rafal.enums.WindowSizes;

public class WindowSize implements Serializable {
	private static final long serialVersionUID = 5831221153739878660L;
	private int width, height;
	private WindowSizes windowSize;

	public WindowSize(WindowSizes windowSize) {
		this.windowSize = windowSize;
	}

	public void setSize(Dimension dimension) {
		width = dimension.width;
		height = dimension.height;
	}

	public Optional<Dimension> getSize() {
		Dimension dimension = null;
		if (height > 0 && width > 0) {
			dimension = new Dimension(width, height);
		}

		return Optional.ofNullable(dimension);
	}

	public void configureWebDriver(WebDriver driver) {
		switch (windowSize) {
		case DEFAULT:
			break;
		case MAXIMIZED:
			driver.manage().window().maximize();
			break;
		case SIZED:
			Dimension dimension = new Dimension(width, height);
			driver.manage().window().setSize(dimension);
			break;
		default:
			throw new RuntimeException(String.format("Window size enum [%s] is not implemented in WindowSize class!"));
		}
	}

	public WindowSizes getType() {
		return windowSize;
	}
}
