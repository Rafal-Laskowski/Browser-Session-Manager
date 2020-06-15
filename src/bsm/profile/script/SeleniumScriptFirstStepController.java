package bsm.profile.script;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import laskowski.rafal.selenium.script.SeleniumScriptStep;

public class SeleniumScriptFirstStepController implements Initializable {
	
	@FXML
	private TitledPane firstStepTitledPane;
	
	@FXML
	private TextField urlTextField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public TitledPane getTitledPane() {
		return firstStepTitledPane;
	}
	
	public String getURL() {
		return urlTextField.getText();
	}

	public void applySeleniumScriptStep(SeleniumScriptStep scriptStep) {
		urlTextField.setText(scriptStep.getTextToEnter());
	}
}
