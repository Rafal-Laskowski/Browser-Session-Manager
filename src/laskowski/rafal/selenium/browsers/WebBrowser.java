package laskowski.rafal.selenium.browsers;

import laskowski.rafal.enums.BrowserType;
import laskowski.rafal.enums.Language;
import laskowski.rafal.selenium.profiles.Profile;
import laskowski.rafal.selenium.script.SeleniumScript;
import laskowski.rafal.utils.Logger;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Optional;

public class WebBrowser extends Observable {
	private Profile profile;
	private WebDriver driver;
	private ActiveSessionListener sessionListener;
	private BrowserType browserType;

	static {
		System.setProperty("webdriver.chrome.driver", "binaries/chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "binaries/geckodriver.exe");
		System.setProperty("webdriver.ie.driver", "binaries/IEDriverServer.exe");
	}

	public WebBrowser(Profile profile) {
		this.profile = Objects.requireNonNull(profile);
	}

	public void start(BrowserType type) {
		this.browserType = type;
		Language language = profile.getLanguage();

		Proxy proxy = null;
		if (profile.getProxy() != null) {
			proxy = new Proxy();
			proxy.setHttpProxy(profile.getProxy().toString());
		}
		
		switch (type) {
		case CHROME:
			driver = startChrome(language, proxy);
			registerConsoleLogListener();
			break;
		case FF:
			driver = startFirefox(language, proxy);
			break;
		case IE:
			driver = startIE();
			break;
		}

		profile.getWindowSize().configureWebDriver(driver);
		sessionListener = new ActiveSessionListener(this);
		sessionListener.start();
	}

	public void stop() {
		try {
			driver.quit();
		} catch (NoSuchSessionException ignore) {
		}
		sessionListener.terminate();
		setChanged();
		notifyObservers(new SessionChanged());
	}

	public void executeScript() {
		Optional<SeleniumScript> optSeleniumScript = profile.getSeleniumScript();
		if (optSeleniumScript.isPresent()) {
			optSeleniumScript.get().invoke(driver);
		}
	}

	public Profile getProfile() {
		return profile;
	}

	public boolean isActive() {
		return this.isActive(driver);
	}

	public BrowserType getBrowserType() {
		return browserType;
	}

	public WebDriver toWebDriver() {
		return driver;
	}

	private boolean isActive(WebDriver driver) {
		if (driver != null) {
			try {
				driver.getCurrentUrl();
				return true;
			} catch (WebDriverException e) {
				Logger.out(e.getMessage());
				return false;
			}
		}
		return false;
	}

	private WebDriver startChrome(Language language, Proxy proxy) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments(String.format("--lang=%s", language.getProperty()));

		if (proxy != null) {
			options.addArguments(String.format("--proxy-server=%s", proxy.getHttpProxy()));
		}

		return new ChromeDriver(options);
	}

	private WebDriver startFirefox(Language language, Proxy proxy) {
		FirefoxOptions options = new FirefoxOptions();
		options.addPreference("intl.accept_languages", language.getProperty());

		if (proxy != null) {
			options.addPreference("network.proxy.type", proxy.getProxyType().ordinal());
			Logger.out(proxy.getSocksProxy());
			options.addPreference("network.proxy.socks", proxy.getSocksProxy());
			options.addPreference("network.proxy.socks_port", 1080);
		}

		return new FirefoxDriver(options);
	}

	private WebDriver startIE() {
		InternetExplorerOptions options = new InternetExplorerOptions();
		options.ignoreZoomSettings();

		return new InternetExplorerDriver(options);
	}

	private void registerConsoleLogListener() {
		Thread listener = new Thread(() -> {
			while (isActive()) {
				List<String> logs = BrowserLogger.getLogs(driver);

				if (logs.size() > 0) {
					setChanged();
					notifyObservers(new ConsoleLogs(logs));
				}

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		listener.start();
	}

}
