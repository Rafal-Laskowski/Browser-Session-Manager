package bsm.profile.script;

import java.net.URL;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

import org.openqa.selenium.By;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import laskowski.rafal.enums.Actions;
import laskowski.rafal.enums.BYs;
import laskowski.rafal.selenium.ByParser;
import laskowski.rafal.selenium.script.SeleniumScriptStep;

public class SeleniumScriptStepController extends Observable implements Initializable {

	@FXML
	private AnchorPane seleniumScriptPane;

	@FXML
	private TitledPane scriptStepTitledPane;

	@FXML
	private ComboBox<String> byComboBox;

	@FXML
	private ComboBox<String> actionComboBox;

	@FXML
	private TextField textToEnterTextField;

	@FXML
	private TextField selectorTextField;

	@FXML
	private Label textToEnterLbl;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textToEnterTextField.setVisible(false);
		textToEnterLbl.setVisible(false);

		byComboBox.getItems().removeAll(byComboBox.getItems());
		byComboBox.getItems().addAll(BYs.toList());
		byComboBox.getSelectionModel().select(0);

		actionComboBox.getItems().removeAll(actionComboBox.getItems());
		actionComboBox.getItems().addAll(Actions.CLICK.toString(), Actions.SEND_KEYS.toString());
		actionComboBox.getSelectionModel().select(0);
	}

	public void applySeleniumScriptStep(SeleniumScriptStep scriptStep) {
		Optional<By> optBy = scriptStep.getBy();

		if (optBy.isPresent()) {
			By by = optBy.get();
			BYs bys = ByParser.getType(by);

			if (bys.equals(BYs.CSS)) {
				byComboBox.getSelectionModel().select(BYs.CSS.toString());
			} else if (bys.equals(BYs.XPATH)) {
				byComboBox.getSelectionModel().select(BYs.XPATH.toString());
			} else if (bys.equals(BYs.ID)) {
				byComboBox.getSelectionModel().select(BYs.ID.toString());
			}

			selectorTextField.setText(ByParser.getSelector(by));
		}

		Actions actions = scriptStep.getAction();
		actionComboBox.getSelectionModel().select(actions.name());

		if (actions.equals(Actions.SEND_KEYS)) {
			textToEnterTextField.setVisible(true);
			textToEnterLbl.setVisible(true);
		}

		String textToEnter = scriptStep.getTextToEnter();
		textToEnterTextField.setText(textToEnter);
	}

	public TitledPane getTitledPane() {
		return scriptStepTitledPane;
	}

	public void setStepNumber(int stepNumber) {
		scriptStepTitledPane.setText(String.format("%s #%d", "Step", stepNumber));
	}

	@FXML
	private void delete() {
		setChanged();
		notifyObservers(scriptStepTitledPane);
	}

	public By getBy() {
		String selector = selectorTextField.getText();
		String selectorType = byComboBox.getSelectionModel().getSelectedItem();
		switch (byComboBox.getSelectionModel().getSelectedItem()) {
		case "ID":
		case "id":
			return By.id(selector);
		case "CSS":
		case "css":
			return By.cssSelector(selector);
		case "XPATH":
		case "xpath":
			return By.xpath(selector);
		}
		throw new IllegalArgumentException(String.format("Unsupported selector type: %s", selectorType));
	}

	public Actions getAction() {
		return Actions.parse(actionComboBox.getSelectionModel().getSelectedItem());
	}

	public String getTextToEnter() {
		return textToEnterTextField.getText();
	}

	@FXML
	private void showOrHideSendKeysTextField(ActionEvent event) {
		if (actionComboBox.getValue().equals(Actions.SEND_KEYS.toString())) {
			textToEnterTextField.setVisible(true);
			textToEnterLbl.setVisible(true);
		} else {
			textToEnterTextField.setVisible(false);
			textToEnterLbl.setVisible(false);
		}
	}
}
