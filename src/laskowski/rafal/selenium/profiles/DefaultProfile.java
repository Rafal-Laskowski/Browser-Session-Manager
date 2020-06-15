package laskowski.rafal.selenium.profiles;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import laskowski.rafal.constants.Directories;
import laskowski.rafal.enums.Language;
import laskowski.rafal.enums.WindowSizes;
import laskowski.rafal.selenium.WindowSize;
import laskowski.rafal.selenium.proxy.ProxySettings;
import laskowski.rafal.selenium.script.SeleniumScript;
import laskowski.rafal.utils.SerializeHelper;

public class DefaultProfile implements Profile {
	private static final long serialVersionUID = -4791079244419396826L;
	private Language language = Language.ENGLISH;
	private SeleniumScript seleniumScript = null;
	private WindowSize windowSize = new WindowSize(WindowSizes.DEFAULT);
	private String name = "Default Profile";
	private ProxySettings proxy;

	@Override
	public void setLanguage(Language language) {
		this.language = language;
	}

	@Override
	public void addSeleniumScript(SeleniumScript script) {
		seleniumScript = script;
	}

	@Override
	public void setWindowSize(WindowSize windowSize) {
		this.windowSize = windowSize;
	}
	
	@Override
	public void setProxy(ProxySettings proxy) {
		this.proxy = proxy;
	}

	@Override
	public Language getLanguage() {
		return language;
	}

	@Override
	public Optional<SeleniumScript> getSeleniumScript() {
		return Optional.ofNullable(seleniumScript);
	}

	@Override
	public WindowSize getWindowSize() {
		return windowSize;
	}

	@Override
	public Profile load(String fileName) {
		if (fileName != null) {
			return (Profile) new SerializeHelper(Directories.PROFILES, fileName).in();
		}
		throw new RuntimeException(String.format("Cannot find profile file [%s]", fileName));
	}

	@Override
	public void save() {
		new SerializeHelper(Directories.PROFILES, name).out(this);
	}

	@Override
	public void delete() {
		new SerializeHelper(Directories.PROFILES, name).delete();
	}

	public static List<Profile> loadAll() {
		List<Profile> profileList = new ArrayList<>();
		File profilesDir = new File(Directories.PROFILES);
		File[] profiles = profilesDir.listFiles();

		Arrays.asList(profiles).forEach(profileFile -> {
			DefaultProfile dp = new DefaultProfile();
			Profile profile = dp.load(profileFile.getName());
			profileList.add(profile);
		});

		return profileList;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public ProxySettings getProxy() {
		return proxy;
	}

	public static boolean exist() {
		File defaultProfileFile = new File(Directories.PROFILES, "DefaultProfile.ser");
		return defaultProfileFile.exists();
	}
}
