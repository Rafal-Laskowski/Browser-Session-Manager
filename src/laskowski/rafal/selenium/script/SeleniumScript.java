package laskowski.rafal.selenium.script;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

public class SeleniumScript implements Serializable {
	private static final long serialVersionUID = 2756754374877081957L;
	private List<SeleniumScriptStep> seleniumSteps = new ArrayList<>();

	public void invoke(WebDriver driver) {
		seleniumSteps.forEach(seleniumScriptStep -> seleniumScriptStep.invoke(driver));
	}

	public void addStep(SeleniumScriptStep seleniumScriptStep) {
		seleniumSteps.add(seleniumScriptStep);
	}

	public List<SeleniumScriptStep> getSteps() {
		return seleniumSteps;
	}
}
