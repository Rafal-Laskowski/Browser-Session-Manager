package bsm.profile;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import bsm.generics.Dialog;
import bsm.profile.script.SeleniumScriptFirstStepInitializer;
import bsm.profile.script.SeleniumScriptStepController;
import bsm.profile.script.SeleniumScriptStepInitializer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import laskowski.rafal.enums.Actions;
import laskowski.rafal.enums.Language;
import laskowski.rafal.enums.WindowSizes;
import laskowski.rafal.selenium.WindowSize;
import laskowski.rafal.selenium.profiles.DefaultProfile;
import laskowski.rafal.selenium.profiles.Profile;
import laskowski.rafal.selenium.proxy.ProxySettings;
import laskowski.rafal.selenium.script.SeleniumScript;
import laskowski.rafal.selenium.script.SeleniumScriptStep;

public class ProfileController implements Initializable, Observer {

	@FXML
	public ToggleGroup customWindowSizeGroup;

	@FXML
	private RadioButton defaultSize;

	@FXML
	private RadioButton maximized;

	@FXML
	private RadioButton sized;

	@FXML
	public ToggleGroup languageGroup;

	@FXML
	private RadioButton englishRadio;

	@FXML
	private RadioButton polishRadio;

	@FXML
	private TitledPane customWindowSizePane;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField widthTextField;

	@FXML
	private TextField heightTextField;

	@FXML
	private Accordion seleniumSteps;
	
	@FXML
	private TextField proxyUrl;
	
	@FXML
	private TextField proxyPort;

	private List<SeleniumScriptStepInitializer> seleniumScriptSteps = new ArrayList<>();

	private SeleniumScriptFirstStepInitializer ssfsInit;

	private int stepNumber = 2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		customWindowSizePane.setVisible(false);
		ssfsInit = (SeleniumScriptFirstStepInitializer) new SeleniumScriptFirstStepInitializer().createInstance();
		seleniumSteps.getPanes().add(ssfsInit.getTitledPane());
	}

	@Override
	public void update(Observable o, Object arg) {
		TitledPane toRemove = (TitledPane) arg;
		seleniumSteps.getPanes().remove(toRemove);

		stepNumber = 1;

		ObservableList<TitledPane> existingTitledPanes = seleniumSteps.getPanes();
		existingTitledPanes.forEach(titledPane -> {
			titledPane.setText(String.format("Step #%d", stepNumber));
			stepNumber++;

			seleniumScriptSteps.removeIf(sssInit -> sssInit.getTitledPane().equals(titledPane));
		});
	}

	public void applyProfile(Profile profile) {
		nameTextField.setText(profile.getName());

		Language lang = profile.getLanguage();
		if (lang.equals(Language.ENGLISH)) {
			englishRadio.setSelected(true);
		} else if (lang.equals(Language.POLISH)) {
			polishRadio.setSelected(true);
		}

		WindowSize windowSize = profile.getWindowSize();
		WindowSizes windowSizeType = windowSize.getType();
		if (windowSizeType.equals(WindowSizes.DEFAULT)) {
			defaultSize.setSelected(true);
		} else if (windowSizeType.equals(WindowSizes.MAXIMIZED)) {
			maximized.setSelected(true);
		} else if (windowSizeType.equals(WindowSizes.SIZED)) {
			sized.setSelected(true);
			customWindowSizePane.setVisible(true);
			Dimension size = windowSize.getSize().get();
			widthTextField.setText(String.valueOf(size.getWidth()));
			heightTextField.setText(String.valueOf(size.getHeight()));
		}

		Optional<SeleniumScript> optSeleniumScript = profile.getSeleniumScript();
		if (optSeleniumScript.isPresent()) {
			SeleniumScript script = optSeleniumScript.get();
			List<SeleniumScriptStep> scriptSteps = script.getSteps();
			addFirstStep(scriptSteps.get(0));

			for (int i = 1; i < scriptSteps.size(); i++) {
				addStep(scriptSteps.get(i));
			}
		}
		
		ProxySettings proxy = profile.getProxy();
		if (proxy != null) {
			proxyUrl.setText(proxy.getUrl());
			proxyPort.setText(proxy.getPort());
		}
	}

	@FXML
	private void handleCustomWindowSizePane(ActionEvent event) {
		RadioButton selectedRadioButton = (RadioButton) customWindowSizeGroup.getSelectedToggle();
		String toogleGroupValue = selectedRadioButton.getText();

		if (toogleGroupValue.equals(defaultSize.getText())) {
			customWindowSizePane.setVisible(false);
		} else customWindowSizePane.setVisible(!toogleGroupValue.equals(maximized.getText()));
	}

	@FXML
	private void addStep(ActionEvent event) {
		addStep((SeleniumScriptStep) null);
	}

	private void addStep(SeleniumScriptStep scriptStep) {
		SeleniumScriptStepInitializer sssInit = (SeleniumScriptStepInitializer) new SeleniumScriptStepInitializer()
				.createInstance();
		sssInit.setStepNumber(stepNumber);
		sssInit.addObserver(this);

		// apply steps
		if (scriptStep != null) {
			sssInit.getController().applySeleniumScriptStep(scriptStep);
		}
		seleniumSteps.getPanes().add(sssInit.getTitledPane());
		stepNumber++;

		seleniumScriptSteps.add(sssInit);
	}

	private void addFirstStep(SeleniumScriptStep scriptStep) {
		ssfsInit.getController().applySeleniumScriptStep(scriptStep);
	}

	@FXML
	public Profile saveProfile(ActionEvent action) {
		DefaultProfile profile = new DefaultProfile();

		profile.setName(nameTextField.getText());
		profile.setLanguage(Language.parse(((RadioButton) languageGroup.getSelectedToggle()).getText()));
		WindowSize windowSize = WindowSizes.parse(((RadioButton) customWindowSizeGroup.getSelectedToggle()).getText());
		if (windowSize.getType().equals(WindowSizes.SIZED)) {
			windowSize.setSize(new Dimension(Integer.parseInt(widthTextField.getText()),
					Integer.parseInt(heightTextField.getText())));
		}
		profile.setWindowSize(windowSize);
		SeleniumScript script = getSeleniumScript();
		profile.addSeleniumScript(script);
		
		String url = proxyUrl.getText();
		String port = proxyPort.getText();
		if (url.length() > 0 && port.length() > 0) {
			profile.setProxy(new ProxySettings().setUrl(proxyUrl.getText()).setPort(proxyPort.getText()));
		}
		
		profile.save();

		Dialog.close(action);

		return profile;
	}

	private SeleniumScript getSeleniumScript() {
		SeleniumScript seleniumScript = new SeleniumScript();

		String url = ssfsInit.getController().getURL();

		if (url != null && url.length() > 0) {
			SeleniumScriptStep openUrlStep = new SeleniumScriptStep();
			openUrlStep.setAction(Actions.GET);
			openUrlStep.setTextToEnter(url);

			seleniumScript.addStep(openUrlStep);

			seleniumScriptSteps.forEach(sssInit -> {
				SeleniumScriptStepController ctrl = sssInit.getController();
				By by = ctrl.getBy();
				Actions action = ctrl.getAction();
				String text = ctrl.getTextToEnter();

				SeleniumScriptStep sss = new SeleniumScriptStep();
				sss.setBy(by);
				sss.setAction(action);
				sss.setTextToEnter(text);

				seleniumScript.addStep(sss);
			});

			return seleniumScript;
		} else {
			return null;
		}
	}

	@FXML
	public void cancel(ActionEvent event) {
		Dialog.close(event);
	}
}
