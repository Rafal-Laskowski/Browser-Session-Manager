package laskowski.rafal.selenium.profiles;

import java.util.Optional;

import laskowski.rafal.enums.Language;
import laskowski.rafal.io.Deletable;
import laskowski.rafal.io.Loadable;
import laskowski.rafal.io.Saveable;
import laskowski.rafal.selenium.WindowSize;
import laskowski.rafal.selenium.proxy.ProxySettings;
import laskowski.rafal.selenium.script.SeleniumScript;

public interface Profile extends Loadable<Profile>, Saveable, Deletable {

	void setName(String name);

	void setLanguage(Language language);

	void addSeleniumScript(SeleniumScript script);

	void setWindowSize(WindowSize windowSize);
	
	void setProxy(ProxySettings proxy);

	Language getLanguage();

	Optional<SeleniumScript> getSeleniumScript();

	WindowSize getWindowSize();

	String getName();
	
	ProxySettings getProxy();
}
